package com.sellcon.service;

import java.util.*;

import com.sellcon.domain.Cart;
import com.sellcon.domain.Member;
import com.sellcon.domain.Selling_Product;
import com.sellcon.dto.CartDTO;

public interface CartService {

	// 장바구니 리스트
	List<CartDTO> getCartList(String member_id);
	
	boolean checkDuplicateProduct(Long sseq, String member_id);
	
	void insertCart(Cart cart);
	
	void deleteCart(Long sseq, String member_id);
	
	void updateCart(List<Long> sseqList);
	
	/*
	 * 화면에서 수신한 DTO 객체를 Entity 객체로 변환하는 메소드
	 */
	default Cart dtoToEntity(CartDTO dto) {
		
		Member member = Member.builder()
				.id(dto.getMember_id()).build();

		Selling_Product selling_product = Selling_Product.builder()
				.sseq(dto.getSseq()).build();
		
		Cart cart = Cart.builder()
				.cseq(dto.getCseq())
				.member(member)
				.selling_product(selling_product)
				.build();
		
		return cart;
	}

	
	default CartDTO entityToDTO(Object[] obj) {
		/*
		 * for(Object item : obj) { System.out.println(item); }
		 */
		
		CartDTO cartDTO = CartDTO.builder()
				.cseq(Long.parseLong(obj[0].toString()))
				.member_id(obj[1].toString())
				.sseq(Long.parseLong(obj[2].toString()))
				.image(obj[3].toString())
				.product_name(obj[4].toString())
				.price(Integer.parseInt(obj[5].toString()))
				.selling_price(Integer.parseInt(obj[6].toString()))
				.valid((Date)obj[7])
				.result(obj[8].toString())
				.build();
				
		return cartDTO;
	}

	
}