package com.sellcon.repository;


import java.math.BigDecimal;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sellcon.domain.Settlement;

public interface SettlementRepository  extends JpaRepository<Settlement, Long>  {

   // 정산 목록 리스트
	@Query(value = "SELECT " +
		            "st.stseq, " +
		            "o.oseq, " +
		            "CASE " +
		            "    WHEN COUNT(p.product_Name) > 1 THEN MIN(p.product_Name) || ' 외 ' || (COUNT(p.product_Name) - 1) || '건' " +
		            "    ELSE MIN(p.product_Name) " +
		            "END as productNames, " +
		            "SUM(s.sellingPrice) as totalPrice, " +
		            "st.rate, " +
		            "st.settle_amount, " +
		            "s.sell_id, " +
		            "st.styn, " +
		            "o.regdate as orderdate, " +
		            "st.settledate " +
		            "FROM Settlement st " +
		            "JOIN Orders o ON st.oseq = o.oseq " +
		            "JOIN Order_Detail od ON o.oseq = od.oseq " +
		            "JOIN Selling_Product s ON s.sseq = od.sseq " +
		            "JOIN Product p ON s.pseq = p.pseq " +
		            "WHERE s.sell_id = st.sell_id AND st.styn= :styn AND st.sell_id= :sell_id " +
		            "GROUP BY st.stseq, o.oseq, st.rate, st.settle_amount, s.sell_id, st.styn, o.regdate, st.settledate", nativeQuery = true)
   Page<Object[]> getSettlementList(Pageable pageable, @Param("styn") String styn, @Param("sell_id") String sell_id);
   
   // 정산여부에 따른 정렬
   @Query(value = "SELECT " +
		   		  "st.stseq, " +
                   "o.oseq, " +
                   "CASE " +
                   "    WHEN COUNT(p.product_Name) > 1 THEN MIN(p.product_Name) || ' 외 ' || (COUNT(p.product_Name) - 1) || '건' " +
                   "    ELSE MIN(p.product_Name) " +
                   "END as productNames, " +
                   "SUM(s.sellingPrice) as totalPrice, " +
                   "st.rate, " +
		           "st.settle_amount, " +
                   "s.sell_id, " +
                   "st.styn, " +
                   "o.regdate as orderdate, " +
                   "st.settledate " +
                   "FROM Settlement st " +
                   "JOIN Orders o ON st.oseq = o.oseq " +
                   "JOIN Order_Detail od ON o.oseq = od.oseq " +
                   "JOIN Selling_Product s ON s.sseq = od.sseq " +
                   "JOIN Product p ON s.pseq = p.pseq " +
                   "WHERE st.styn LIKE '%'||:styn||'%' AND s.sell_id = st.sell_id AND st.sell_id LIKE '%'||:keyword||'%' " +
                   "GROUP BY st.stseq, o.oseq, st.rate, st.settle_amount, s.sell_id, st.styn, o.regdate, st.settledate", nativeQuery = true)
   Page<Object[]> getSettlementListByStyn(Pageable pageable, @Param("styn") String styn, @Param("keyword") String keyword);
   
   // styn에 해당하는 stseq 찾기
   @Query("SELECT st.stseq FROM Settlement st WHERE st.styn= :styn")
   List<Long> findStseqByStyn(@Param("styn") String styn);
   
   // 완료정산의 경우 styn이 Y인 stseq값으로 rate, settle_amount, settledate 가져오기
   @Query("SELECT st.stseq, st.rate, st.settle_amount, st.settledate FROM Settlement st WHERE st.stseq = :stseq AND st.styn='Y'")
   List<Object[]> findRateAndSettleAmountByStseq(@Param("stseq") Long stseq);
   
   // 정산 처리
   @Modifying
   @Query("UPDATE Settlement st SET st.rate = :rate, st.settle_amount = :settle_amount, st.styn='Y', st.settledate = :settledate WHERE st.stseq = :stseq AND st.styn='N'")
   void updateByStseq(@Param("stseq") Long stseq, @Param("rate") BigDecimal rate, @Param("settle_amount") int settle_amount, @Param("settledate") Date settledate);
   
   // 반려 처리
   @Modifying
   @Query("UPDATE Settlement st SET st.styn='R' WHERE st.stseq = :stseq")
   void updateByStseqAndReturn(@Param("stseq") Long stseq);
   
   // 반려 취소
   @Modifying
   @Query("UPDATE Settlement st SET st.styn='N' WHERE st.stseq = :stseq AND st.styn='R'")
   void updateByStseqAndReturnReverse(@Param("stseq") Long stseq);
   
   // 완료 정산금 계산
   @Query(value = "SELECT SUM(st.settle_amount) FROM Settlement st WHERE st.sell_id = :sell_id AND st.styn = 'Y'", nativeQuery = true)
   Integer getTotalSettleAmountBySellId(@Param("sell_id") String sell_id);
   
   // 미완료 정산금 견적을 위한 판매 완료 금액
   @Query(value = "SELECT SUM(s.sellingPrice) " +
		           "FROM Settlement st " +
		           "JOIN Orders o ON o.oseq = st.oseq " +
		           "JOIN Order_Detail od ON od.oseq = o.oseq " +
		           "JOIN Selling_Product s ON od.sseq = s.sseq " +
		           "WHERE st.sell_id = :sell_id AND st.styn <> 'Y'", nativeQuery = true)
   Integer getTotalSellingPriceBySellId(@Param("sell_id") String sell_id);
   
   // sell_id에 해당하는 정산완료건
   @Query("SELECT COUNT(st.stseq) FROM Settlement st WHERE st.sell_id = :sell_id AND st.styn='Y'")
   Integer getTotalSettlementStynY(@Param("sell_id") String sell_id);
   
   // sell_id에 해당하는 미정산 및 반려건
   @Query("SELECT COUNT(st.stseq) FROM Settlement st WHERE st.sell_id = :sell_id AND st.styn<>'Y'")
   Integer getTotalSettlementStynNR(@Param("sell_id") String sell_id);
   
   
}