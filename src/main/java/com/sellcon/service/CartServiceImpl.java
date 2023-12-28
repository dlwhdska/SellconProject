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
	public boolean checkDuplicateProduct(Long sseq, String member_id) {
		
		return cartRepo.existsBySseq(sseq, member_id);
	}

	// 장바구니 상품 추가
	@Transactional
	public void insertCart(Cart cart) {
	
		cartRepo.save(cart);
	}
	
	@Override
	@Transactional
	public void deleteCart(Long sseq, String member_id) {
		
		Long cseq = cartRepo.findCseqByMemberBySseq(sseq, member_id);
		
	    cartRepo.deleteByCseq(cseq);
	}
	
	@Transactional
	public void updateCart(List<Long> sseqList) {
		
        List<Long> cseqs = new ArrayList<>();

        for (Long sseq : sseqList) {
            List<Long> findCseqs = cartRepo.findCseqsBySseq(sseq);
            
            // 만약 해당 sseq에 대한 cseq가 존재하면 cseqs 리스트에 모두 추가
            if (findCseqs != null && !findCseqs.isEmpty()) {
            	cseqs.addAll(findCseqs);
            }
        }

        // 장바구니에 상품이 비어있지 않다면 update
        if (!cseqs.isEmpty()) {
            cartRepo.updateByCseqList(cseqs);
        }
    }
	
}
