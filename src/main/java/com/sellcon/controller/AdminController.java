package com.sellcon.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sellcon.domain.Admin;
import com.sellcon.domain.Brand;
import com.sellcon.domain.CS;
import com.sellcon.domain.Category;
import com.sellcon.domain.Member;
import com.sellcon.domain.Product;
import com.sellcon.domain.Selling_Product;
import com.sellcon.domain.Service_Board;
import com.sellcon.repository.CSRepository;
import com.sellcon.service.AdminService;
import com.sellcon.service.CSService;
import com.sellcon.service.MemberService;
import com.sellcon.service.ProductService;
import com.sellcon.service.Service_BoardService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private ProductService productService;
	@Autowired
	private Service_BoardService serviceBoardService;
	@Autowired
	private CSRepository csRepo;
	@Autowired
	private CSService csService;
	@Autowired
	private MemberService memberService;

	@GetMapping("/adminIndex")
	public String adminIndexView(HttpSession session, Model model) {
		Admin adminUser = (Admin) session.getAttribute("admin");
		if (adminUser != null) {
			session.setAttribute("adminUser", adminUser.getId());
			model.addAttribute("adminUser", adminUser.getId());
		}
		return "adminIndex";
	}

	@GetMapping("/admin_sell")
	public String sellapplicationView(HttpSession session, Model model) {

		Admin adminUser = (Admin) session.getAttribute("admin");
		if (adminUser != null) {
			session.setAttribute("adminUser", adminUser.getId());
			model.addAttribute("adminUser", adminUser.getId());
		}

		List<Selling_Product> sproductList = productService.getSellingProductListAdmin();
		model.addAttribute("sproductList", sproductList);

		return "admin_sell";
	}

	@GetMapping("/admin_product")
	public String adminPorudctListView(HttpSession session, Model model) {

		Admin adminUser = (Admin) session.getAttribute("admin");
		if (adminUser != null) {
			session.setAttribute("adminUser", adminUser.getId());
			model.addAttribute("adminUser", adminUser.getId());
		}

		List<Product> productList = productService.getProductListAdmin();
		model.addAttribute("productList", productList);

		return "admin_product";
	}

	@GetMapping("/admin_cb")
	public String adminpbListView(HttpSession session, Model model) {

		Admin adminUser = (Admin) session.getAttribute("admin");
		if (adminUser != null) {
			session.setAttribute("adminUser", adminUser.getId());
			model.addAttribute("adminUser", adminUser.getId());
		}

		List<Brand> brandList = productService.getAllBrandList();
		model.addAttribute("brandList", brandList);

		return "admin_cb";
	}

	@GetMapping("adminSellDetail={sseq}")
	public String adminSellDetail(@PathVariable Long sseq, Model model) {

		Selling_Product sproduct = productService.getSellingProductBySseq(sseq);
		List<Brand> brandList = productService.getBrandList();
		List<Product> productList = productService.getProductListAdmin();

		model.addAttribute("sproduct", sproduct);
		model.addAttribute("brandList", brandList);
		model.addAttribute("productList", productList);

		return "admin_sell_detail";
	}

	@GetMapping("cbDetail={bseq}")
	public String admincdDetail(@PathVariable Long bseq, Model model) {

		Brand brand = productService.getBrandByBseq(bseq);
		List<Category> categoryList = productService.getCategoryList();
		List<Brand> selectedcategory = productService.getSelectedCategory(bseq);
		System.out.println("selectedcategory: " + selectedcategory);

		model.addAttribute("brand", brand);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("selectedcategory", selectedcategory);
		return "admin_cb_detail";
	}

	@PostMapping("/updateBrand")
	@ResponseBody
	public List<Brand> updateBrand(@RequestParam("bseq") Long bseq,
			@RequestParam(name = "kind", required = false) String kind,
			@RequestParam(name = "brandName", required = false) String brandName,
			@RequestParam(name = "brandImage", required = false) MultipartFile brandImage) throws IOException {

		List<Brand> result = productService.updateBrandList(bseq, kind, brandName, brandImage);
		return result;
	}

	@GetMapping("/addBrand")
	public String addBrandView(HttpSession session, Model model) {

		List<Category> categoryList = productService.getCategoryList();
		model.addAttribute("categoryList", categoryList);

		return "admin_cb_add";
	}

	@PostMapping("/uploadBrand")
	@ResponseBody
	public List<Brand> uploadBrand(@RequestParam("kind") String kind, @RequestParam("brandName") String brandName,
			@RequestParam("brandImage") MultipartFile brandImage) throws IOException {

		List<Brand> result = productService.uploadBrandList(kind, brandName, brandImage);
		return result;
	}

	@GetMapping("adminProductDetail={pseq}")
	public String adminProductDetail(@PathVariable Long pseq, Model model) {

		Product product = productService.getProductByPseq(pseq);
		List<Brand> brandList = productService.getBrandList();
		List<Product> selectedbrand = productService.getSelectedBrand(pseq);

		model.addAttribute("product", product);
		model.addAttribute("brandList", brandList);
		model.addAttribute("selectedbrand", selectedbrand);
		return "admin_product_detail";
	}

	@PostMapping("/updateProduct")
	@ResponseBody
	public List<Product> updateProduct(@RequestParam("pseq") Long pseq,
			@RequestParam(name = "bseq", required = false) Long bseq,
			@RequestParam(name = "productName", required = false) String productName,
			@RequestParam(name = "content", required = false) String content,
			@RequestParam(name = "image", required = false) MultipartFile image,
			@RequestParam(name = "price", required = false) Integer price) throws IOException {

		List<Product> result = productService.updateProductList(pseq, bseq, productName, content, image, price);
		return result;
	}

	@GetMapping("/addProduct")
	public String addProductView(HttpSession session, Model model) {

		List<Brand> brandList = productService.getBrandList();
		model.addAttribute("brandList", brandList);
		return "admin_product_add";
	}

	@PostMapping("/uploadProduct")
	@ResponseBody
	public List<Product> uploadProduct(@RequestParam("bseq") Long bseq, @RequestParam("productName") String productName,
			@RequestParam("image") MultipartFile image, @RequestParam("content") String content,
			@RequestParam("price") int price) throws IOException {

		List<Product> result = productService.uploadProductList(bseq, productName, image, content, price);
		return result;
	}

	@PostMapping("/updateCheckP")
	@ResponseBody
	public List<Selling_Product> updateCheckP(@RequestParam Long sseq, @RequestParam String checkp) {

		List<Selling_Product> result = productService.updateSProductListCheckp(sseq, checkp);
		return result;
	}

	@GetMapping("/getProductsByBrandAdmin")
	@ResponseBody
	public List<Product> getProductsByBrandAdmin(@RequestParam Long bseq) {
		List<Product> productList = productService.findProductByBseq(bseq);
		return productList;
	}

	@PostMapping("/updateSellAdmin")
	@ResponseBody
	public List<Selling_Product> updateSellAdmin(@RequestParam("sseq") Long sseq,
			@RequestParam(name = "bseq", required = false) Long bseq,
			@RequestParam(name = "pseq", required = false) Long pseq,
			@RequestParam(name = "sellingprice", required = false) Integer sellingprice,
			@RequestParam(name = "barcode", required = false) Long barcode,
			@RequestParam(name = "valid", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date valid,
			@RequestParam(name = "barcode_image", required = false) MultipartFile barcode_image) throws IOException {

		List<Selling_Product> result = productService.updateSellingProductList(sseq, bseq, pseq, sellingprice, barcode,
				valid, barcode_image);
		return result;
	}

	@PostMapping("/deleteSellAdmin")
	@ResponseBody
	public List<Selling_Product> deleteSellAdmin(@RequestParam("sseq") Long sseq) {
		List<Selling_Product> sproductList = productService.deleteSellingProduct(sseq);
		return sproductList;
	}

	@PostMapping("/deleteProductAdmin")
	@ResponseBody
	public List<Product> deleteProductAdmin(@RequestParam("pseq") Long pseq) {
		List<Product> productList = productService.deleteProduct(pseq);
		return productList;
	}

	@PostMapping("/deleteBrandAdmin")
	@ResponseBody
	public List<Brand> deleteBrandADmin(@RequestParam("bseq") Long bseq) {
		List<Brand> brandList = productService.deleteBrand(bseq);
		return brandList;
	}

	@RequestMapping("tables")
	public String serviceBoardListForm(Model model) {
		List<Service_Board> serviceBoardList = serviceBoardService.getAllServiceBoard();
		model.addAttribute("serviceBoardList", serviceBoardList);
		return "tables";
	}

	@GetMapping("/serviceBoardReply/{qseq}")
	public String showserviceBoardModify(@PathVariable("qseq") Long qseq, Model model) {
		Service_Board serviceBoard = serviceBoardService.findById(qseq);

		model.addAttribute("serviceBoard", serviceBoard);
		return "serviceBoardReply";
	}

	@PostMapping("/serviceBoardReply/{qseq}")
	public String handleServiceBoardReply(@PathVariable("qseq") Long qseq,
			@RequestParam("replyContent") String replyContent, RedirectAttributes redirectAttributes) {

		Service_Board serviceBoard = serviceBoardService.findById(qseq);

		if (serviceBoard != null) {
			if (!replyContent.isEmpty()) {
				serviceBoard.setReply(replyContent);
				serviceBoard.setRepyn("Y");
				serviceBoardService.saveServiceBoard(serviceBoard);
				redirectAttributes.addFlashAttribute("message", "답변이 저장되었습니다.");
			} else {
				redirectAttributes.addFlashAttribute("error", "답변 내용을 입력해주세요.");
			}
			return "redirect:/tables";
		} else {
			return "redirect:error";
		}
	}

	@RequestMapping("/adminNotice")
	public String adminNotice(Model model) {
		List<CS> CSList = csService.getAllCSList();
		model.addAttribute("CSList", CSList);
		System.out.println(CSList);
		return "adminNotice";
	}

	@PostMapping("/writeAdminNotice")
	public String showWriteAdminNoticeForm(Model model) {
		model.addAttribute("cs", new CS());
		return "writeAdminNotice";
	}

	@PostMapping("/adminNoticeWrite")
	public String saveAdminNotice(@ModelAttribute("cs") CS cs, HttpSession session) {
		csService.saveCS(cs);
		System.out.println("------------------------------------------------------");
		System.out.println(cs);
		return "redirect:/adminNotice";
	}

	@GetMapping("/updateAdminNotice/{csseq}")
	public String showUpdateAdminNoticeForm(@PathVariable Long csseq, Model model) {
		CS cs = csService.findCSById(csseq);
		model.addAttribute("cs", cs);
		return "updateAdminNotice";
	}

	@PostMapping("/updateAdminNotice/{csseq}")
	public String updateAdminNotice(@PathVariable Long csseq, @ModelAttribute CS updatedCS) {
		csService.updateCS(csseq, updatedCS);
		return "redirect:/adminNotice";
	}

	@PostMapping("/deleteAdminNotice/{csseq}")
	public String deleteAdminNotice(@PathVariable Long csseq) {
		csService.deleteCS(csseq);
		return "redirect:/adminNotice";
	}

	@GetMapping("/adminMemberList")
	public String adminMemberList(Member member, Model model, HttpSession session) {
		Admin adminUser = (Admin) session.getAttribute("admin");
		if (adminUser != null) {
			session.setAttribute("adminUser", adminUser.getId());
			model.addAttribute("adminUser", adminUser.getId());
		}

		List<Member> memberList = memberService.getMemberList(member);

		model.addAttribute("adminMemberList", memberList);

		return "adminMemberList";
	}

	@PostMapping("/memberListModify")
	public String memberListModify(Member member, RedirectAttributes rattr, HttpSession session) {
		memberService.memberListModify(member);

		session.setAttribute("member", member);

		rattr.addAttribute("id", member.getId());

		return "redirect:adminMemberList";
	}

	@PostMapping("/memberListDelete")
	public String memberListDelete(Member member) {

		memberService.memberListDelete(member);

		return "redirect:adminMemberList";
	}

}
