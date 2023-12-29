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
	@Autowired
	private SettlementService settlementService;

	@PostMapping("/orders")
	public String getOrdersList(Model model, @RequestParam("cseq") List<Long> cseqList) {

		// cseq 목록
		for (int i = 0; i < cseqList.size(); i++) {
			System.out.print(cseqList.get(i) + ",");
		}
		List<OrdersDTO> orderList = ordersService.getOrdersList(cseqList);
		model.addAttribute("ordersItems", orderList);

		return "orders";
	}

	// 바로 주문하기
	@PostMapping("/orderdirectly")
	public String getOrdersList(HttpSession session, Model model, @RequestParam("sseq") Long sseq) {
		Member member = (Member) session.getAttribute("member");

		if (member == null || member.getId() == null) {
			return "redirect:/login";
		}

		List<OrdersDTO> orderList = ordersService.getOrder(sseq, member.getId());

		model.addAttribute("ordersItems", orderList);

		return "orders";
	}

	@PostMapping("/addorders")
	public String insertOrders(HttpSession session, Model model, @RequestParam("sseq") List<Long> sseqList) {
		// 세션에서 회원 정보 가져오기
		Member member = (Member) session.getAttribute("member");

		// 주문 생성
		Orders orders = Orders.builder()
						.member(member)
						.build();

		// 주문 생성 서비스 호출
		ordersService.insertOrders(orders);

		// 받아온 sseqList 출력
		System.out.println("sseqList: " + sseqList);

		// Order_Detail과 Settlement을 담을 리스트 생성
		List<Order_Detail> orderDetails = new ArrayList<>();
		List<Settlement> settlements = new ArrayList<>();

		// 중복 체크를 위한 Set
		Set<String> sellerIdsProcessed = new HashSet<>();

		// 각 상품의 sseq를 활용하여 order_detail 생성 및 리스트에 추가
		for (Long sseq : sseqList) {
			// sseq로 Selling_Product 정보 가져오기
			Selling_Product sellingProduct = productService.getSellingProductBySseq(sseq);

			// 주문완료한 상품 Cart result = 2로 업데이트
			List<Long> cartUpdate = new ArrayList<>();
			cartUpdate.add(sseq);
			cartService.updateCart(cartUpdate);

			// Order_Detail 생성 후 리스트에 추가
			Order_Detail orderDetail = Order_Detail.builder()
										.order(orders)
										.selling_product(sellingProduct)
										.build();
			orderDetails.add(orderDetail);

			// 해당 sell_id에 맞게 Settlement 생성
			String sellId = sellingProduct.getMember().getId();
			if (!sellerIdsProcessed.contains(sellId)) {
				Settlement settlement = Settlement.builder()
										.orders(orders)
										.sell_id(sellId)
										.build();
				settlements.add(settlement);
				sellerIdsProcessed.add(sellId);
			}
		}

		// Order_Detail과 Settlement을 일괄 삽입
		ordersService.insertOrderDetails(orderDetails);
		settlementService.insertSettlement(settlements);

		Long oseq = orders.getOseq();

		return "redirect:/main";
	}

}
