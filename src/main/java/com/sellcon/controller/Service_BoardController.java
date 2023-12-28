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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sellcon.domain.Member;
import com.sellcon.domain.Service_Board;
import com.sellcon.service.Service_BoardService;

@Controller
@SessionAttributes("member")
public class Service_BoardController {
	@Autowired
	private Service_BoardService serviceBoardService;

	@RequestMapping("/serviceBoardList")
	public String serviceBoardList(Service_Board serviceBoard, Model model, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		System.out.println(member);

		if (member == null || member.getId() == null) {
			return "redirect:/login";
		}

		List<Service_Board> serviceBoardList = serviceBoardService.serviceBoardList(serviceBoard);

		model.addAttribute("serviceBoardList", serviceBoardList);

		return "serviceBoardList";
	}

	@PostMapping("/qna")
	public String showQnaPage(Model model, HttpSession session) {
		Member member = (Member) session.getAttribute("member");

		if (member == null || member.getId() == null) {
			return "redirect:/login";
		}

		return "qna";
	}

	@PostMapping("/insertBoard")
	public String insertBoard(Service_Board serviceBoard) {
		serviceBoardService.insertBoard(serviceBoard);
		System.out.println("-------------------------------------------------------------------");
		System.out.println(serviceBoard);
		return "redirect:/serviceBoardList";
	}

	@GetMapping("/getServiceBoard/{qseq}")
	public String showGetServiceBoard(@PathVariable("qseq") Long qseq, Model model) {
		Service_Board serviceBoard = serviceBoardService.findById(qseq);

		model.addAttribute("serviceBoard", serviceBoard);
		return "getServiceBoard";
	}

	@PostMapping("/updateBoard")
	public String updateBoard(Service_Board serviceBoard) {
		serviceBoardService.updateBoard(serviceBoard);

		return "redirect:serviceBoardList";
	}

	@GetMapping("/modify/{qseq}")
	public String showModifyForm(@PathVariable("qseq") Long qseq, Model model) {
		Service_Board serviceBoard = serviceBoardService.findById(qseq);

		model.addAttribute("serviceBoard", serviceBoard);
		return "modify";
	}

	@PostMapping("/modify/{qseq}")
	public String modifyServiceBoard(@PathVariable("qseq") Long qseq,
	        @RequestParam String title,
	        @RequestParam String content) {

	    Service_Board updateServiceBoard = serviceBoardService.findById(qseq);

	    if ("Y".equals(updateServiceBoard.getRepyn())) {
	        return "redirect:/serviceBoardList?repynError=true";
	    }

	    updateServiceBoard.setTitle(title);
	    updateServiceBoard.setContent(content);

	    serviceBoardService.updateBoard(updateServiceBoard);

	    return "redirect:/serviceBoardList";
	}


	@PostMapping("/deleteBoard")
	public String deleteBoard(Service_Board serviceBoard) {
		serviceBoardService.deleteBoard(serviceBoard);

		return "redirect:/serviceBoardList";
	}
}