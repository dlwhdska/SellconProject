package com.sellcon.service;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Cart;
import com.sellcon.dto.CartDTO;
import com.sellcon.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepository cartRepo;
	
	// 장바구니 리스트
	@Override
	public List<CartDTO> getCartList(String member_id){
		
	        List<Object[]> cartInfo = cartRepo.getCartByMemberId(member_id);
	        
	        List<CartDTO> cartDTOList = new ArrayList<>();
	        
	        for (Object[] obj : cartInfo) {
	            CartDTO cartDTO = entityToDTO(obj);
	            cartDTOList.add(cartDTO);
	        }
	        
	        return cartDTOList;
	}
	
	// 장바구니 내 중복 상품 여부 확인
	@Override
	public boolean checkDuplicateProduct(Long sseq) {
		
		return cartRepo.existsBySseq(sseq);
	}

	// 장바구니 상품 추가
	@Transactional
	public void insertCart(Cart cart) {
	
		cartRepo.save(cart);
	}

	// 장바구니 상품 삭제
	@Override
	@Transactional
	public void deleteCart(Long sseq) {
		
		Long oseq = cartRepo.findOseqBySseq(sseq);
		
	    cartRepo.deleteByOseq(oseq);
	}


	
}
