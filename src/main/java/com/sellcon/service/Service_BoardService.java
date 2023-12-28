package com.sellcon.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.sellcon.domain.Service_Board;

public interface Service_BoardService {

	List<Service_Board> serviceBoardList(Service_Board serviceBoard);

	void insertBoard(Service_Board serviceBoard);
	
	public Service_Board getserviceBoard(Service_Board serviceBoard);

	void updateBoard(Service_Board serviceBoard);

	void deleteBoard(Service_Board serviceBoard);

	Service_Board findById(Long qseq);

	void save(Service_Board serviceBoard);

	public Service_Board getServiceBoardById(Long qseq);
	
	public List<Service_Board> getServiceBoardList();
	
	List<Service_Board> getAllServiceBoard();

	void saveServiceBoard(Service_Board serviceBoard);

}