package com.sellcon.service;

import java.math.BigDecimal;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Settlement;
import com.sellcon.dto.MySettlementDTO;
import com.sellcon.dto.SettlementDTO;
import com.sellcon.repository.SettlementRepository;

@Service
public class SettlementServiceImpl implements SettlementService {

	@Autowired
	private SettlementRepository settlementRepo;

	public Page<SettlementDTO> getSettlementList(Pageable pageable, String styn, String sell_id) {
		Page<Object[]> settlementPage = settlementRepo.getSettlementList(pageable, styn, sell_id);

		return settlementPage.map(this::entityToDTO);
	}

	public Page<SettlementDTO> getSettlementListBySort(Pageable pageable, String styn, String keyword) {
		Page<Object[]> settlementPage = settlementRepo.getSettlementListByStyn(pageable, styn, keyword);

		return settlementPage.map(this::entityToDTO);
	}

	@Override
	public void insertSettlement(List<Settlement> settlements) {

		settlementRepo.saveAll(settlements);
	}

	@Transactional
	public void updateSettlement(Long stseq, BigDecimal rate, int settle_amount, Date settledate) {

		settlementRepo.updateByStseq(stseq, rate, settle_amount, settledate);
	}

	@Transactional
	public void settlementReturn(Long stseq) {

		settlementRepo.updateByStseqAndReturn(stseq);
	}

	@Transactional
	public void settlementReturnReverse(Long stseq) {

		settlementRepo.updateByStseqAndReturnReverse(stseq);
	}

	public int getMySettleAmount(String sell_id) {

		Integer settleAmount = settlementRepo.getTotalSettleAmountBySellId(sell_id);
		
		if (settleAmount == null) {
	        return 0;
	    }
		
		return settleAmount;
	}

	public int getMyEstimateSettleAmount(String sell_id) {

		Integer notYetSettleAmount = settlementRepo.getTotalSellingPriceBySellId(sell_id);

		if (notYetSettleAmount == null) {
	        notYetSettleAmount = 0;
	    }
		
		// 3% 기준으로 미정산 정산금 계산처리
		return (int) Math.round(notYetSettleAmount * 0.03);
	}
	
	public int completedSettlements(String sell_id) {
		Integer completedSettlements = settlementRepo.getTotalSettlementStynY(sell_id);
		
		return completedSettlements;
	}
	
	public int unsettlements(String sell_id) {
		Integer unsettlements = settlementRepo.getTotalSettlementStynNR(sell_id);
		
		return unsettlements;
	}
	
	public List<MySettlementDTO> getMySettlementList(String sell_id, String styn) {
	    List<Object[]> mySettlementList = settlementRepo.findMySettlementList(sell_id, styn);

	    Set<Long> oseqSet = new HashSet<>();
	    List<MySettlementDTO> resultList = new ArrayList<>();

	    for (Object[] obj : mySettlementList) {
	        Long oseq = Long.parseLong(obj[0].toString());

	        if (!oseqSet.contains(oseq)) {
	            MySettlementDTO settlementDTO = mySettlementList(obj);
	            resultList.add(settlementDTO);
	            oseqSet.add(oseq);
	        }
	    }
 	    return resultList;
	}
	
	public List<MySettlementDTO> getSettlementDetail(Long oseq) {
	    List<Object[]> settlementDetail = settlementRepo.findSettlementDetails(oseq);
	    
	    List<MySettlementDTO> settlementDTOList = new ArrayList<>();

	    for (Object[] detail : settlementDetail) {
	        MySettlementDTO mySettlementDTO = mySettlementList(detail);
	        settlementDTOList.add(mySettlementDTO);
	    }

	    return settlementDTOList;
	}

}
