package com.sellcon.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.sellcon.domain.Admin;
import com.sellcon.domain.Selling_Product;
import com.sellcon.service.AdminService;
import com.sellcon.service.ProductService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductService productService;
	
	@GetMapping("/adminIndex")
	public String adminIndexView(HttpSession session, Model model) {
		Admin adminUser = (Admin)session.getAttribute("admin");
		if(adminUser != null) {
			session.setAttribute("adminUser", adminUser.getId());
			model.addAttribute("adminUser", adminUser.getId());
		}
		return "adminIndex";
	}
	
	@GetMapping("/admin_sellapplication")
	public String sellapplicationView(HttpSession session, Model model) {
		
		Admin adminUser = (Admin)session.getAttribute("admin");
		if(adminUser != null) {
			session.setAttribute("adminUser", adminUser.getId());
			model.addAttribute("adminUser", adminUser.getId());
		}
		
		List<Selling_Product> sproductList = productService.getSellingProductListAdmin();
		model.addAttribute("sproductList", sproductList);
		
		return "admin_sellapplication";
	}
	
}
