package com.sellcon.service;

import java.util.List;

import com.sellcon.domain.Service_Board;

public interface Service_BoardService {

	List<Service_Board> serviceBoardList(Service_Board serviceBoard);

	void insertBoard(Service_Board serviceBoard);
	
	public Service_Board getserviceBoard(Service_Board serviceBoard);

	void updateBoard(Service_Board serviceBoard);

	void deleteBoard(Service_Board serviceBoard);
}