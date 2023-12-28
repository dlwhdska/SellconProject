package com.sellcon.service;

import java.util.List;

import com.sellcon.domain.CS;

public interface CSService {

	void saveCS(CS cs);

	void deleteCS(Long csseq);
	 
	public void saveCSWithAdmin(String adminId);
	
	public CS updateCS(Long csseq, CS updatedCS);

	CS findCSById(Long csseq);
	
	List<CS> getAllCSList();
}