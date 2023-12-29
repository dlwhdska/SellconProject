package com.sellcon.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sellcon.domain.Admin;
import com.sellcon.domain.CS;
import com.sellcon.domain.Member;
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

	@GetMapping("/admin_sellapplication")
	public String sellapplicationView(HttpSession session, Model model) {

		Admin adminUser = (Admin) session.getAttribute("admin");
		if (adminUser != null) {
			session.setAttribute("adminUser", adminUser.getId());
			model.addAttribute("adminUser", adminUser.getId());
		}

		List<Selling_Product> sproductList = productService.getSellingProductListAdmin();
		model.addAttribute("sproductList", sproductList);

		return "admin_sellapplication";
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
