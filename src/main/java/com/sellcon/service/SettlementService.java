package com.sellcon.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sellcon.domain.Settlement;
import com.sellcon.dto.SettlementDTO;

public interface SettlementService {

	Page<SettlementDTO> getSettlementList(Pageable pageable, String styn, String sell_id);
	
	Page<SettlementDTO> getSettlementListBySort(Pageable pageable, String styn, String keyword);
	
	void insertSettlement(List<Settlement> settlements);

	void updateSettlement(Long stseq, BigDecimal rate, int settle_amount, Date settledate);
	
	void settlementReturn(Long stseq);
	
	void settlementReturnReverse(Long stseq);
	
	int getMySettleAmount(String sell_id);
	
	int getMyEstimateSettleAmount(String sell_id);
	
	int completedSettlements(String sell_id);
	
	int unsettlements(String sell_id);
	
	default SettlementDTO entityToDTO(Object[] obj) {
		
		if(obj[4] == null) {
			obj[4] = "0";
		}
		if(obj[5] == null) {
			obj[5] = "0";
		}
		if(obj[9] == null) {
			obj[9] = new Date("9999/01/01");
		}
		
		SettlementDTO settlementDTO = SettlementDTO.builder()
									.stseq(Long.parseLong(obj[0].toString()))
									.oseq(Long.parseLong(obj[1].toString()))
									.productNames(obj[2].toString())
									.totalPrice(BigDecimal.valueOf(Double.parseDouble(obj[3].toString())))
									.rate(BigDecimal.valueOf(Double.parseDouble(obj[4].toString())))
									.settle_amount(Integer.parseInt(obj[5].toString()))
									.sell_id(obj[6].toString())
									.styn(obj[7].toString())
									.orderdate((Date)obj[8])
									.settledate((Date)obj[9])
									.build();
		
		return settlementDTO;
	}
	
}