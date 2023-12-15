package com.sellcon.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Member;
import com.sellcon.domain.Product;
import com.sellcon.domain.Search;
import com.sellcon.domain.Selling_Product;
import com.sellcon.service.ProductService;

@Controller
public class MainController {

	@Autowired
	private ProductService productService;

	// 메인 페이지
	@GetMapping("main")
	public String mainView(Model model) {

		List<Selling_Product> sproductList = productService.getAllSellingProductRegDate();
		List<Brand> brandList = productService.getAllBrandList();

		model.addAttribute("brandList", brandList);
		model.addAttribute("sproductList", sproductList);

		return "main";
	}
	
	@PostMapping("mypage")
	public String mypageView() {
		return "mypage";
	}

	// 상품 페이지
	@GetMapping("/product")
	public String productView(Model model, Search search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "default") String sort,
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "brand", required = false) Long bseq) {

		int pageSize = 12;
		Pageable pageable;
		if ("latest".equalsIgnoreCase(sort)) {
			pageable = PageRequest.of(page, pageSize, Sort.by("sseq").descending());
		} else if ("lowprice".equalsIgnoreCase(sort)) {
			pageable = PageRequest.of(page, pageSize, Sort.by("sellingprice").ascending());
		} else if ("highprice".equalsIgnoreCase(sort)) {
			pageable = PageRequest.of(page, pageSize, Sort.by("sellingprice").descending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("sseq").descending());
		}

		List<Brand> brandList;
		if (StringUtils.isEmpty(category) || "false".equalsIgnoreCase(category)) {
			brandList = productService.getAllBrandList();
		} else {
			brandList = productService.getBrandByCategoryKind(category);
		}

		Page<Selling_Product> sproductList;
		if (StringUtils.isEmpty(category) || "false".equalsIgnoreCase(category)) {
			sproductList = productService.getSellingProductListAll(pageable, bseq);
		} else {
			sproductList = productService.getSellingProductFilter(pageable, bseq, category);
		}

		int currentPage = sproductList.getNumber();
		int totalPages = sproductList.getTotalPages();

		System.out.println("Current Page: " + currentPage);
		System.out.println("Total Pages: " + totalPages);
		System.out.println("Brand LIST SIZE : " + brandList.size());

		model.addAttribute("sproductList", sproductList);
		model.addAttribute("totalProducts", sproductList.getTotalElements());
		model.addAttribute("sort", sort);
		model.addAttribute("brandList", brandList);
		model.addAttribute("categoryKind", category);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);

		return "product";

	}

	@GetMapping("/updateProductList")
	public String updateProductList(Model model, Search search,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "default") String sort,
			@RequestParam(defaultValue = "false") String category,
			@RequestParam(name = "brand", required = false) Long bseq) {

		int pageSize = 12;
		Pageable pageable;
		if ("latest".equalsIgnoreCase(sort)) {
			pageable = PageRequest.of(page, pageSize, Sort.by("sseq").descending());
		} else if ("lowprice".equalsIgnoreCase(sort)) {
			pageable = PageRequest.of(page, pageSize, Sort.by("sellingprice").ascending());
		} else if ("highprice".equalsIgnoreCase(sort)) {
			pageable = PageRequest.of(page, pageSize, Sort.by("sellingprice").descending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("sseq").descending());
		}

		List<Brand> brandList = productService.getAllBrandList();

		Page<Selling_Product> sproductList;
		if (StringUtils.isEmpty(category) || "false".equalsIgnoreCase(category)) {
			sproductList = productService.getSellingProductListAll(pageable, bseq);
		} else {
			sproductList = productService.getSellingProductFilter(pageable, bseq, category);
		}

		int currentPage = sproductList.getNumber();
		int totalPages = sproductList.getTotalPages();

		System.out.println("Current Page: " + currentPage);
		System.out.println("Total Pages: " + totalPages);
		System.out.println("Brand LIST SIZE : " + brandList.size());

		model.addAttribute("sproductList", sproductList);
		model.addAttribute("totalProducts", sproductList.getTotalElements());
		model.addAttribute("sort", sort);
		model.addAttribute("brandList", brandList);
		model.addAttribute("categoryKind", category);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);

		return "product :: product_list_elements";

	}

	@GetMapping("product_detail={sseq}")
	public String productDetailView(Model model, @PathVariable Long sseq) {

		List<Selling_Product> sproduct = productService.getSellingProductById(sseq);
		model.addAttribute("sproduct", sproduct);

		return "product_detail";
	}

	@GetMapping("product_detail")
	public String productDetailTestView() {
		return "product_detail";
	}

	// 판매 페이지
	@GetMapping("sell")
	public String sellView(HttpSession session, Model model) {

		Member member = (Member) session.getAttribute("member");
		if (member == null || member.getId() == null) {
			return "redirect:/login";
		}

		List<Brand> brandList = productService.findAllBrandList();
		model.addAttribute("brandList", brandList);
		return "sell";
	}

	@GetMapping("/getProductByBrand/{brandName}")
	@ResponseBody
	public List<Product> getProductByBrand(@PathVariable String brandName,
			@RequestParam(required = false) String productKeyword) {
		List<Product> productList = productService.findByProductNameSearch(brandName, productKeyword);
		System.out.println("productList : " + productList);
		return productList;
	}

	@GetMapping("/getSellingPriceList/{productName}")
	@ResponseBody
	public List<Selling_Product> getSellingPriceList(@PathVariable String productName) {
		List<Selling_Product> sproductList = productService.findSellingProductByProductName(productName);
		System.out.println("sproductList : " + sproductList);
		return sproductList;
	}

	@PostMapping("/submitSellForm")
	@ResponseBody
	public List<Selling_Product> submitSellFormf(@RequestParam("pseq") Long pseq,
			@RequestParam("sellingprice") int sellingprice, @RequestParam("barcode") Long barcode,
			@RequestParam("valid") @DateTimeFormat(pattern = "yyyy-MM-dd") Date valid,
			@RequestParam("barcode_image") MultipartFile barcodeImage,
			@RequestParam("sellerId") String sellerId) throws IOException{

		List<Selling_Product> result = productService.submitSellForm(pseq, sellingprice, barcode, valid, barcodeImage, sellerId);
		return result;
	}

	@GetMapping("notice")
	public String showNotice() {
		return "notice";
	}
	
}
