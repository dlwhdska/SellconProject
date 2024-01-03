package com.sellcon.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sellcon.domain.*;
import com.sellcon.dto.*;
import com.sellcon.service.*;

@Controller
public class MypageController {

	@Autowired
	OrdersService ordersService;
	@Autowired
	SettlementService settlementService;
	@Autowired
	ProductService productService;

	
	@GetMapping("/mypage")
	public String mypageView(Model model, HttpSession session,
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="pageY", defaultValue = "0") int pageY) {
	    Member member = (Member) session.getAttribute("member");
	    String checkp = "Y";
	    if (member != null) {
	    	
	    	String userId = member.getId();
	    	
	        Map<String, Long> counts = productService.salesApplication(userId);
	        
	        int pageSize = 5;
	        Pageable pageable = PageRequest.of(page, pageSize);
	        Pageable pageableY = PageRequest.of(pageY, pageSize);
	        
	        Page<Selling_Product> sproductList = productService.salesApplicationList(pageable, userId);
	        Page<Selling_Product> sproductYList = productService.salesApplicationListY(pageableY, userId, checkp);
	        
	        System.out.println(sproductYList);
	        
	        int currentPage = sproductList.getNumber();
	        int totalPages = sproductList.getTotalPages();
	        
	        int currentPageY = sproductYList.getNumber();
	        int totalPagesY = sproductYList.getTotalPages();

	        
	        model.addAttribute("countY", counts.get("countY"));
	        model.addAttribute("countNull", counts.get("countNull"));
	        
	        model.addAttribute("sproductList", sproductList);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("totalPages", totalPages);
			
			model.addAttribute("currentPageY", currentPageY);
			model.addAttribute("totalPagesY", totalPagesY);
			model.addAttribute("sproductYList", sproductYList);	
			
	        model.addAttribute("member", member);
	        session.setAttribute("member", member);
	        
	        int totalSettleAmount = settlementService.getMySettleAmount(member.getId());
			int notYetSettleAmount = settlementService.getMyEstimateSettleAmount(member.getId());
			int completedSettlements = settlementService.completedSettlements(member.getId());
			int unsettlemnts = settlementService.unsettlements(member.getId());

			model.addAttribute("totalSettleAmount", totalSettleAmount);
			model.addAttribute("notYetSettleAmount", notYetSettleAmount);
			model.addAttribute("completedSettlements", completedSettlements);
			model.addAttribute("unsettlemnts", unsettlemnts);
	        
	        return "mypage"; 
	    } else {
	        return "redirect:/login"; 
	    }
	}

	// 주문 요약 정보
	@RequestMapping("/myOrder")
	public String getMyOrders(HttpSession session, Model model,
							@RequestParam(defaultValue = "1") int page,
							@RequestParam(defaultValue = "5") int size) {
		Member member = (Member) session.getAttribute("member");

		PageRequest pageable = PageRequest.of(page - 1, size);
		Page<Map<String, Object>> myOrder = ordersService.getMyOrderSummary(member.getId(), pageable);

		model.addAttribute("myOrder", myOrder.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalItems", myOrder.getTotalElements());
		model.addAttribute("totalPages", myOrder.getTotalPages());
		model.addAttribute("size", size);

		return "myorder";
	}

	// 주문 상세 보기
	@GetMapping("/myOrderDetail")
	public String getMyOrderDetail(Model model, @RequestParam("oseq") Long oseq) {

		List<Order_Detail> myOrderDetail = ordersService.getMyOrderDetail(oseq);
		model.addAttribute("myOrderDetail", myOrderDetail);

		return "myorder_detail";
	}

	// 정산 목록 요약
	@RequestMapping("/mySettlementList")
	public String getMySettlementList(HttpSession session, Model model,
									@RequestParam("styn") String styn) {

		Member member = (Member) session.getAttribute("member");

		List<MySettlementDTO> mySettlementList = settlementService.getMySettlementList(member.getId(), styn);

		model.addAttribute("mySettlementList", mySettlementList);
		model.addAttribute("styn", styn);

		return "mysettlementlist";
	}

	// 정산 디테일
	@GetMapping("/mySettlementDetail")
	public String getMySettlementDetail(Model model, @RequestParam("oseq") Long oseq) {

		List<MySettlementDTO> mySettlementDetail = settlementService.getSettlementDetail(oseq);
		model.addAttribute("mySettlementDetail", mySettlementDetail);

		return "mysettlementl_detail";
	}

}
