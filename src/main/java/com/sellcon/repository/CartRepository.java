package com.sellcon.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sellcon.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	// MEMBER_ID에 해당하는 CART 장바구니 목록 가져오기
	@Query(value="SELECT * FROM cart_view WHERE member_id=:member_id AND result='1'", nativeQuery = true)
	List<Object[]> getCartByMemberId(@Param("member_id") String member_id);
	
	// 장바구니에 해당 상품이 담겨있는지 확인(SSEQ)
	@Query("SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END FROM Cart c WHERE c.selling_product.sseq = :sseq")
	Boolean existsBySseq(@Param("sseq") Long sseq);
	
	// 삭제하려는 상품의 sseq에 해당하는 oseq찾기
    @Query("SELECT c.oseq FROM Cart c WHERE c.selling_product.sseq = :sseq")
    Long findOseqBySseq(@Param("sseq") Long sseq);

    // oseq에 해당하는 장바구니 상품 삭제
    void deleteByOseq(Long oseq);
}
