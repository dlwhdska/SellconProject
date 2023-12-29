package com.sellcon.service;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sellcon.domain.*;
import com.sellcon.dto.*;

public interface OrdersService {

	// 주문 상품 목록 리스트
	List<OrdersDTO> getOrdersList(List<Long> cseqList);
	
	List<OrdersDTO> getOrder(Long sseq, String member_id);
	
	void insertOrders(Orders orders);
	
	void insertOrderDetails(List<Order_Detail> orderDetails);
	
	Page<Map<String, Object>> getMyOrderSummary(String member_id, Pageable pageable);
	
	List<Order_Detail> getMyOrderDetail(Long oseq);
	
	default OrdersDTO entityToDTO(Object[] obj) {
		
		OrdersDTO ordersDTO = OrdersDTO.builder()
							.cseq(Long.parseLong(obj[0].toString()))
							.sseq(Long.parseLong(obj[1].toString()))
							.member_id(obj[2].toString())
							.product_name(obj[3].toString())
							.image(obj[4].toString())
							.price(Integer.parseInt(obj[5].toString()))
							.selling_price(Integer.parseInt(obj[6].toString()))
							.valid((Date)obj[7])
							.sell_id(obj[8].toString())
							.build();
		
		return ordersDTO;
		
	}
	
	default OrdersDTO orderDirectly(Long cseq, String member_id, Object[] obj) {
	    OrdersDTO ordersDTO = OrdersDTO.builder()
	            .cseq(cseq)
	            .sseq(Long.parseLong(obj[0].toString()))
	            .member_id(member_id)
	            .product_name(obj[1].toString())
	            .image(obj[2].toString())
	            .price(Integer.parseInt(obj[3].toString()))
	            .selling_price(Integer.parseInt(obj[4].toString()))
	            .valid((Date) obj[5])
	            .sell_id(obj[6].toString())
	            .build();

	    return ordersDTO;
	}

}
