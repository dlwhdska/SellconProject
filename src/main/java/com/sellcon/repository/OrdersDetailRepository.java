package com.sellcon.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sellcon.domain.Order_Detail;

public interface OrdersDetailRepository extends JpaRepository<Order_Detail, Long> {

	// oseq에 해당하는 모든 Order_Detail 가져오기
	@Query("SELECT od FROM Order_Detail od WHERE od.order.id= :oseq")
	List<Order_Detail> findAllByOseq(@Param("oseq") Long oseq);
	
	// oseq에 해당하는 모든 Order_Detail 가져오기(페이징)
	@Query(value = "SELECT od FROM Order_Detail od JOIN od.order o WHERE o.id = :oseq")
	Page<Order_Detail> findAllByOseq(Pageable pageable, @Param("oseq") Long oseq);

	// oseq에 해당하는 총 결제금액
	@Query(value = "SELECT SUM(s.sellingprice) FROM Order_Detail od JOIN Selling_Product s ON s.sseq = od.sseq WHERE od.oseq = :oseq", nativeQuery = true)
	Integer getTotalPaymentByOseq(@Param("oseq") Long oseq);

}
