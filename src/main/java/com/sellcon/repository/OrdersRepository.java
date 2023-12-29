package com.sellcon.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sellcon.domain.Orders;
import com.sellcon.dto.OrdersDTO;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	
	// 주문하려는 상품 목록 리스트
	@Query(value = "SELECT c.cseq, s.sseq, c.member_id, p.product_name, p.image, p.price, s.sellingprice, s.valid, s.sell_id " +
		            "FROM Cart c " +
		            "JOIN Selling_Product s ON c.sseq = s.sseq " +
		            "JOIN Product p ON s.pseq = p.pseq " +
		            "WHERE c.result = '1' " +
		            "AND c.cseq = :cseq", nativeQuery = true)
	List<Object[]> findOrdersByCseq(@Param("cseq") Long cseq);
	
	// 바로 주문
	@Query(value = "SELECT s.sseq, p.product_name, p.image, p.price, s.sellingprice, s.valid, s.sell_id " +
		            "FROM Selling_Product s " +
		            "JOIN Product p ON s.pseq = p.pseq " +
		            "WHERE s.sseq = :sseq", nativeQuery = true)
	List<Object[]> findOrdersBySseq(@Param("sseq") Long sseq);
	
	// member_id에 해당하는 oseq 찾기
	@Query("SELECT oseq FROM Orders WHERE member_id = :member_id")
	List<Long> findOseqByMemberId(@Param("member_id") String member_id);
	
	
}
