package com.sellcon.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sellcon.domain.Cart;
import com.sellcon.domain.Member;
import com.sellcon.domain.Selling_Product;
import com.sellcon.dto.CartDTO;
import com.sellcon.repository.CartRepository;
import com.sellcon.service.CartService;


@Controller
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CartRepository cartRepo;
	
	@RequestMapping("/cart")
	public String getCartList(HttpSession session, Model model) {
	    Member member = (Member) session.getAttribute("member");
	    
	    if (member == null || member.getId() == null) {
	        return "redirect:/login";
	    }
	    
	    List<CartDTO> cartList = cartService.getCartList(member.getId());
	    model.addAttribute("cartItems", cartList);
	    
	    return "cart";
	}

	
	
	@PostMapping("/addToCart")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> insertCart(HttpSession session, @RequestBody Map<String, Long> requestBody) {
	    Long sseq = requestBody.get("sseq");
	    
	    Member member = (Member) session.getAttribute("member");
	    
	    // 로그인 확인
	    if (member == null || member.getId() == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "User not logged in"));
	    }

	    // 중복 상품 체크
	    boolean isDuplicate = cartService.checkDuplicateProduct(sseq);

	    if (isDuplicate) {
	        return ResponseEntity.ok(Collections.singletonMap("duplicate", true));
	    } else {
	        Selling_Product sellingProduct = new Selling_Product();
	        sellingProduct.setSseq(sseq);

	        Cart cart = Cart.builder()
	                .member(member)
	                .selling_product(sellingProduct)
	                .build();

	        cartService.insertCart(cart);

	        Map<String, Object> response = new HashMap<>();
	        response.put("duplicate", false);

	        return ResponseEntity.ok(response);
	    }
	}

	@PostMapping("/deleteCart")
	@ResponseBody
	public ResponseEntity<String> deleteCart(@RequestParam("sseq") Long sseq) {
	    try {
	        cartService.deleteCart(sseq);
	        return ResponseEntity.ok("Cart item deleted");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete cart item");
	    }
	}

	@PostMapping("/selectDeleteCarts")
	public ResponseEntity<String> selectDeleteCarts(@RequestBody Map<String, List<Long>> request) {
	    try {
	        List<Long> sseqs = request.get("sseqs");
	        for (Long sseq : sseqs) {
	            cartService.deleteCart(sseq);
	        }
	        return ResponseEntity.ok("Selected cart items deleted");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete selected cart items");
	    }
	}

}
