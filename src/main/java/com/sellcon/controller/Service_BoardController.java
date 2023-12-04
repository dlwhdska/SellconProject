package com.sellcon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sellcon.domain.Service_Board;
import com.sellcon.service.Service_BoardService;

@Controller
public class Service_BoardController {
	@Autowired
	private Service_BoardService serviceBoardService;

	@RequestMapping("/serviceBoardList")
	public String serviceBoardList(Service_Board serviceBoard, Model model) {
		List<Service_Board> serviceBoardList = serviceBoardService.serviceBoardList(serviceBoard);

		model.addAttribute("serviceBoardList", serviceBoardList);

		return "serviceBoardList";
	}

	@GetMapping("/insertBoard")
	public String insertBoardView() {

		return "insertBoard";
	}

	@PostMapping("/insertBoard")
	public String insertBoard(Service_Board serviceBoard) {
		serviceBoardService.insertBoard(serviceBoard);

		return "redirect:serviceBoardList";
	}

	@GetMapping("getserviceBoard")
	public String getserviceBoard(Service_Board serviceBoard, Model model) {
		model.addAttribute("serviceBoard", serviceBoardService.getserviceBoard(serviceBoard));

		return "getserviceBoard";
	}

	@PostMapping("updateBoard")
	public String updateBoard(Service_Board serviceBoard) {
		serviceBoardService.updateBoard(serviceBoard);

		return "redirect:serviceBoardList";
	}

	@GetMapping("/deleteBoard")
	public String deleteBoard(Service_Board serviceBoard) {
		serviceBoardService.deleteBoard(serviceBoard);

		return "redirect:serviceBoardList";
	}
}