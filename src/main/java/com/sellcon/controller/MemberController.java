package com.sellcon.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sellcon.domain.Member;
import com.sellcon.service.MemberService;

@Controller
@SessionAttributes("member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/login")
	public void loginView() {
		
	}
	
	@PostMapping("/login")
	public String login(Member member, Model model) {
		Member findMember = memberService.getMember(member);
		
		if (findMember != null && findMember.getPwd().equals(member.getPwd())) {
			model.addAttribute("member", findMember);
			
			return "redirect:/";
		} else {
			return "redirect:login";
		}
	}
	
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		status.setComplete();
		
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
        System.out.println(sessionData);
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
