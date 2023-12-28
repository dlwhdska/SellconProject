package com.sellcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellcon.domain.Service_Board;

public interface Service_BoardRepository extends JpaRepository<Service_Board, Long> {
	List<Service_Board> findAll();
	
	List<Service_Board> findByMemberId(String memberId);
}
