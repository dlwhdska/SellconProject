package com.sellcon.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sellcon.domain.*;
import com.sellcon.dto.*;
import com.sellcon.service.*;

@Controller
public class OrdersController {
	
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CartService cartService;
	
	@PostMapping("/orders")
	public String getOrdersList(Model model,
								@RequestParam("cseq") List<Long> cseqList) {
		
		// cseq 목록
		for(int i=0; i<cseqList.size(); i++) {
			System.out.print(cseqList.get(i) + ",");
		}
		List<OrdersDTO> orderList = ordersService.getOrdersList(cseqList);
		model.addAttribute("ordersItems", orderList);
		
		return "orders";
	}
	
	// 바로 주문하기
	@PostMapping("/orderdirectly")
	public String getOrdersList(HttpSession session, Model model,
								@RequestParam("sseq") Long sseq) {
		Member member = (Member) session.getAttribute("member");
	    
	    if (member == null || member.getId() == null) {
	        return "redirect:/login";
	    }
	    
		List<OrdersDTO> orderList = ordersService.getOrder(sseq, member.getId());
		
		model.addAttribute("ordersItems", orderList);
		
		return "orders";
	}
	

}
