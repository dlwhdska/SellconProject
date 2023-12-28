package com.sellcon.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sellcon.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	// MEMBER_ID에 해당하는 CART 장바구니 목록 가져오기
	@Query(value = "SELECT c.cseq, c.member_id, s.sseq, p.image, p.product_name, p.price, s.sellingprice, s.valid, c.result " +
		            "FROM Cart c " +
		            "JOIN SELLING_PRODUCT s ON c.sseq = s.sseq " +
		            "JOIN PRODUCT p ON s.pseq = p.pseq " +
		            "WHERE c.member_id = :member_id AND c.result = '1'", nativeQuery = true)
    List<Object[]> getCartByMemberId(@Param("member_id") String member_id);
	
	// 장바구니에 해당 상품이 담겨있는지 확인(SSEQ)
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Cart c WHERE c.selling_product.sseq = :sseq AND c.member.id = :member_id AND result='1'")
	Boolean existsBySseq(@Param("sseq") Long sseq, @Param("member_id") String member_id);
	
	// 상품의 sseq에 해당하는 특정 회원의 cseq 찾기
	@Query("SELECT c.cseq FROM Cart c WHERE c.selling_product.sseq = :sseq AND c.member.id = :member_id AND result='1'")
    Long findCseqByMemberBySseq(@Param("sseq") Long sseq, @Param("member_id") String member_id);
	
    // 상품의 sseq에 해당하는 모든 cseq찾기
	@Query("SELECT c.cseq FROM Cart c WHERE c.selling_product.sseq = :sseq")
	List<Long> findCseqsBySseq(@Param("sseq") Long sseq);

    @Modifying
    @Query("DELETE FROM Cart c WHERE c.cseq = :cseq AND c.result = '1'")
    void deleteByCseq(@Param("cseq") Long cseq);
    
    // Cseq에 해당하는 장바구니 주문여부 업데이트   
    @Modifying
    @Query("UPDATE Cart c SET c.result='2' WHERE c.cseq IN :cseqList ")
    void updateByCseqList(@Param("cseqList") List<Long> cseqList);

}
