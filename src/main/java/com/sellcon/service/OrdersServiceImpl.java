package com.sellcon.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Order_Detail;
import com.sellcon.domain.Orders;
import com.sellcon.dto.OrdersDTO;
import com.sellcon.repository.OrdersDetailRepository;
import com.sellcon.repository.OrdersRepository;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepository ordersRepo;

	@Autowired
	private OrdersDetailRepository odRepo;

	// 주문 상품 목록 리스트
	public List<OrdersDTO> getOrdersList(List<Long> cseqList) {

		List<OrdersDTO> ordersDTOList = new ArrayList<>();

		for (Long cseq : cseqList) {
			List<Object[]> ordersInfo = ordersRepo.findOrdersByCseq(cseq);

			OrdersDTO ordersDTO = entityToDTO(ordersInfo.get(0));
			ordersDTOList.add(ordersDTO);
		}
		System.out.println("주문하려는 상품 정보: " + ordersDTOList);
		return ordersDTOList;
	}

	// 바로 주문 상품 목록 리스트
	public List<OrdersDTO> getOrder(Long sseq, String member_id) {
		List<Object[]> ordersInfo = ordersRepo.findOrdersBySseq(sseq);

		Long cseq = 0L;
		List<OrdersDTO> orderList = new ArrayList<>();

		for (Object[] info : ordersInfo) {
			OrdersDTO ordersDTO = orderDirectly(cseq, member_id, info);
			orderList.add(ordersDTO);
		}

		return orderList;
	}

	@Transactional
	public void insertOrders(Orders orders) {

		ordersRepo.save(orders);
	}

	@Transactional
	public void insertOrderDetails(List<Order_Detail> orderDetails) {

		odRepo.saveAll(orderDetails);
	}
	
	// 주문 내역 요약
	public Page<Map<String, Object>> getMyOrderSummary(String member_id, Pageable pageable) {
	    List<Long> oseqList = ordersRepo.findOseqByMemberId(member_id);

	    int pageSize = pageable.getPageSize();
	    int currentPage = pageable.getPageNumber();
	    int start = currentPage * pageSize;
	    int end = Math.min(start + pageSize, oseqList.size());

	    List<Long> pagedOseqList = oseqList.subList(start, end);
	    List<Map<String, Object>> myOrder = new ArrayList<>();

	    for (Long oseq : pagedOseqList) {
	        PageRequest pageRequest = PageRequest.of(0, pageSize);
	        Page<Order_Detail> orderDetailsPage = odRepo.findAllByOseq(pageRequest, oseq);

	        if (!orderDetailsPage.isEmpty()) {
	            Map<String, Object> orderMap = new HashMap<>();
	            orderMap.put("orderDetail", orderDetailsPage.getContent().get(0));

	            int totalPayment = odRepo.getTotalPaymentByOseq(oseq);
	            orderMap.put("totalPayment", totalPayment);

	            myOrder.add(orderMap);
	        }
	    }

	    return new PageImpl<>(myOrder, pageable, oseqList.size());
	}

	// 주문 상세보기
	public List<Order_Detail> getMyOrderDetail(Long oseq) {
		
		return odRepo.findAllByOseq(oseq);
	}

}
