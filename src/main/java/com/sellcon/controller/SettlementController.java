package com.sellcon.controller;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sellcon.dto.*;
import com.sellcon.service.*;

@Controller
public class SettlementController {

	@Autowired
	private SettlementService settlementService;
	
	@RequestMapping("/settlement")
	public String getSettlementList(Model model,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "15") int size,
			@RequestParam(value = "keyword", defaultValue="") String keyword) {

		String styn = "N";
		
		PageRequest pageable = PageRequest.of(page - 1, size, Sort.by("o.regdate").descending());
	    Page<SettlementDTO> settlementList = settlementService.getSettlementListBySort(pageable, styn, keyword);

		model.addAttribute("settlementList", settlementList);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", settlementList.getTotalPages());
		model.addAttribute("size", size);

		return "settlement";
	}
	
	@RequestMapping("/settlementlist")
	public String settlementSortByStyn(Model model,
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "15") int size,
	        @RequestParam(value="styn", defaultValue = "") String styn,
	        @RequestParam(value = "keyword", defaultValue="") String keyword) {

	    PageRequest pageable;
	    if ("Y".equals(styn)) {
	        pageable = PageRequest.of(page - 1, size, Sort.by("settledate").descending());
	    } else {
	        pageable = PageRequest.of(page - 1, size, Sort.by("o.regdate").descending());
	    }

	    Page<SettlementDTO> settlementList = settlementService.getSettlementListBySort(pageable, styn, keyword);

	    model.addAttribute("settlementList", settlementList);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", settlementList.getTotalPages());
	    model.addAttribute("size", size);
	    model.addAttribute("styn", styn);
	    model.addAttribute("keyword", keyword);

	    return "settlementlist";
	}
	
	// 정산 처리
	@PostMapping("/updateSettlement")
	public String updateSettlement(@RequestParam("stseq") List<Long> stseqList,
									@RequestParam("settle_amount") List<Integer> settleAmountList,
									@RequestParam("rate") List<BigDecimal> rateList) {

		Date settledate = new Date();

		for (int i = 0; i < stseqList.size(); i++) {
			Long stseq = stseqList.get(i);
			Integer settle_amount = settleAmountList.get(i);
			BigDecimal rate = rateList.get(i);

			settlementService.updateSettlement(stseq, rate, settle_amount, settledate);
		}
		return "redirect:/settlement";
	}

	
	// 반려 처리
	@PostMapping("/returnSettlement")
	public String returnSettlement(@RequestParam("stseq") Long stseq) {

		settlementService.settlementReturn(stseq);

		return "redirect:/settlement";
	}

	// 반려 취소
	@PostMapping("/returnReverSesettlement")
	public String returnReverseSettlement(@RequestParam("stseq") Long stseq) {

		settlementService.settlementReturnReverse(stseq);

		return "redirect:/settlement";
	}
}