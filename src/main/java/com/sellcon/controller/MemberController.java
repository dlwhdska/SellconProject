package com.sellcon.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public String login(Member member, Admin admin, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		
		Optional<Member> findMember = memberService.getMemberById(member.getId());
		Admin findAdmin = adminService.getId(admin);
		
		if (findMember.isPresent() && findMember.get().getPwd().equals(member.getPwd())) {
	        session.setAttribute("member", findMember.get());
	        session.removeAttribute("admin");
	        return "redirect:/";
	    } else if (findAdmin != null && findAdmin.getPwd().equals(admin.getPwd())) {
	        session.setAttribute("admin", findAdmin);
	        session.removeAttribute("member");
	        return "redirect:/";
	    } else {
	    	redirectAttributes.addFlashAttribute("loginError", "아이디 또는 비밀번호가 잘못되었습니다.");
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
	
	@GetMapping("/modify")
	public String modifyView(Model model, HttpSession session) {
	    Member member = (Member) session.getAttribute("member");
	    if (member != null) {
	        model.addAttribute("member", member);
	        return "modify"; 
	    } else {
	        return "redirect:/login"; 
	    }
	}
	
	@PostMapping("/modify")
	public String modify(Member member, RedirectAttributes rattr, HttpSession session) {
		memberService.modify(member);
		
		session.setAttribute("member", member);
		
		rattr.addAttribute("id", member.getId());
		
		return "redirect:modify";
	}
	

	@PostMapping("/remove")
	public String remove(Member member, HttpSession session) {
		memberService.remove(member);
		
		session.invalidate();
		
		return "redirect:main";
	}

	@GetMapping("/findpwd")
	public void findpwdView() {
		
	}
	
	@PostMapping("/findpwd")
	public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {
		boolean emailExists = memberService.doesEmailExist(email);
		if (emailExists) {
			memberService.resetPwdAndSendEmail(email);
			return ResponseEntity.ok("exists");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
		}
	}
	
	@GetMapping("/findid")
	public void findidView() {
		
	}
	
	@PostMapping("/findid")
	public ResponseEntity<String> findIdAndCheckNameEmail(@RequestParam("name") String name, @RequestParam("email") String email, HttpSession session, Model model) {
	    try {
	        boolean isMatch = memberService.checkNameEmailMatch(name, email);

	        if (isMatch) {
	            memberService.requestVerificationCode(name, email);
	            session.setAttribute("name", name); 
	            session.setAttribute("email", email);
	            model.addAttribute("name", name); 
	            model.addAttribute("email", email);
	            System.out.println("1name = " + name);
	            System.out.println("1email =" + email);
	            return ResponseEntity.ok("success");
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이름과 이메일이 일치하지 않습니다.");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
	
	@GetMapping("/verifycode")
	public void verifycodeView() {
	    
	}
	
	// 인증번호 체크단계
	@PostMapping("/verifycode")
    public String verifycode(@RequestParam("code") String verificationCode, HttpSession session, Model model) {
		String name = (String) session.getAttribute("name");
		String email = (String) session.getAttribute("email"); 
		
		try {
        	
            boolean isVerified = memberService.verifyCodeAndRetrieveId(email, verificationCode);
            if (isVerified) {
                model.addAttribute("successMessage", "인증되었습니다!");
                model.addAttribute("email", email);
                session.setAttribute("email", email);
                String member = memberService.getIdByEmail(email);
                System.out.println("controller verifycode email="+email);
                System.out.println("controller verifycode member="+member);
                model.addAttribute("id", member);
                session.setAttribute("id", member);
                System.out.println("성공");
                System.out.println("controller verifycode model="+model);
                System.out.println("controller verifycode session="+session);
                return "/confirmid";
            } else {
                model.addAttribute("errorMessage", "인증 실패: 인증번호가 일치하지 않습니다!");
                System.out.println("실패");
                return "/confirmid";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println("오류발생 "+e);
            return "redirect:/verifycode"; 
        }
    }
	
	@GetMapping("/confirmid")
	public String showVerificationResult(Model model, HttpSession session) {
		String id = (String) model.getAttribute("id");
	    if (id != null && !id.isEmpty()) {
	        model.addAttribute("id", id);
	    	return "confirmid";
	    } else {
	        return "redirect:/login";
	    }
	}
}
