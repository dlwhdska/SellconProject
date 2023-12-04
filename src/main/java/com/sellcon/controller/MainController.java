package com.sellcon.controller;

import java.util.List;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Search;
import com.sellcon.domain.Selling_Product;
import com.sellcon.service.ProductService;

@Controller
public class MainController {

	@Autowired
	private ProductService productService;

	@GetMapping("main")
	public String mainView(Model model) {

		List<Selling_Product> sproductList = productService.getAllSellingProductRegDate();
		model.addAttribute("sproductList", sproductList);

		return "main";
	}

	@GetMapping("/product")
	public String productView(Model model, Search search, @RequestParam(defaultValue = "0") int page,
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

		System.out.println("Brand LIST SIZE : " + brandList.size());
		model.addAttribute("sproductList", sproductList);
		model.addAttribute("totalProducts", sproductList.getTotalElements());
		model.addAttribute("sort", sort);
		model.addAttribute("brandList", brandList);
		model.addAttribute("categoryKind", category);

		return "product";

	}

	@GetMapping("/updateProductList")
	public String updateProductList(Model model, Search search, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "default") String sort, @RequestParam(defaultValue = "false") String category,
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

		model.addAttribute("sproductList", sproductList);
		model.addAttribute("totalProducts", sproductList.getTotalElements());
		model.addAttribute("sort", sort);
		model.addAttribute("brandList", brandList);
		model.addAttribute("categoryKind", category);

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

	@GetMapping("notice")
	public String showNotice() {
		return "notice";
	}
}
