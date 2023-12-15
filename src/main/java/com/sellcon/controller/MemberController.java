package com.sellcon.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sellcon.domain.Admin;
import com.sellcon.domain.Member;
import com.sellcon.service.AdminService;
import com.sellcon.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/login")
	public void loginView() {
		
	}
	
	@PostMapping("/login")
	public String login(Member member, Admin admin, Model model, HttpSession session) {
		
		Member findMember = memberService.getMember(member);
		Admin findAdmin = adminService.getId(admin);
		
	    if (findMember != null && findMember.getPwd().equals(member.getPwd())) {
	        session.setAttribute("member", findMember);
	        session.removeAttribute("admin");
	        return "redirect:/";
	    } else if (findAdmin != null && findAdmin.getPwd().equals(admin.getPwd())) {
	        session.setAttribute("admin", findAdmin);
	        session.removeAttribute("member");
	        return "redirect:/";
	    } else {
	        return "redirect:login";
	    }
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public void joinView() {
		
	}
	
	@PostMapping("/join")
	public String join(Member member) {
		
		memberService.joinMember(member);
		
		return "redirect:login";
	}
	
	@PostMapping("/idCheck")
	public ResponseEntity<Boolean> idCheck(@RequestParam("id") String id) {
		
		boolean isIdAvailable = memberService.idCheck(id);
		
		return ResponseEntity.ok(isIdAvailable);
	}
	
	@PostMapping("/emailCheck")
	public ResponseEntity<Boolean> emailCheck(@RequestParam("email") String email) {
		
		boolean isEmailAvailable = memberService.emailCheck(email);
		
		return ResponseEntity.ok(isEmailAvailable);
	}
	
	@GetMapping("/checkSession")
    public String checkSession(HttpSession session) {
        Object sessionData = session.getAttribute("member");
        Object sessionData2 = session.getAttribute("admin");
        System.out.println(sessionData);
        System.out.println(sessionData2);
        return "somePage";
    }
	
	@GetMapping("/mr")
	public void mdView() {
		
	}
	
	@PostMapping("/mr")
	public String modify(Member member, RedirectAttributes rattr) {
		memberService.modify(member);
		
		return "redirect:mr";
	}
	
	@GetMapping("/mypage")
	public void mypageView() {
		
	}
}
