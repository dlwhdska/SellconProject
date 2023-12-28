package com.sellcon.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Service_Board;
import com.sellcon.repository.Service_BoardRepository;

@Service
public class Service_BoardServiceImpl implements Service_BoardService {
	@Autowired
	private Service_BoardRepository sbRepo;

	@Override
	public List<Service_Board> serviceBoardList(Service_Board serviceBoard) {
		return sbRepo.findAll();
	}

	@Override
	public void insertBoard(Service_Board serviceBoard) {
		sbRepo.save(serviceBoard);

	}

	@Override
	public Service_Board getserviceBoard(Service_Board serviceBoard) {
		return sbRepo.findById(serviceBoard.getQseq()).get();
	}

	@Override
	public void updateBoard(Service_Board serviceBoard) {
		Service_Board newBoard = sbRepo.findById(serviceBoard.getQseq()).get();

		newBoard.setTitle(serviceBoard.getTitle());
		newBoard.setContent(serviceBoard.getContent());

		sbRepo.save(newBoard);
	}

	@Override
	public void deleteBoard(Service_Board serviceBoard) {
		sbRepo.deleteById(serviceBoard.getQseq());
	}

	@Override
	public Service_Board findById(Long qseq) {
		return sbRepo.findById(qseq).orElse(null);
	}

	@Override
	public void save(Service_Board serviceBoard) {
		sbRepo.save(serviceBoard);
	}
	
	@Override
    public Service_Board getServiceBoardById(Long qseq) {
        return sbRepo.findById(qseq).orElseThrow(() -> new EntityNotFoundException("Service_Board with ID " + qseq + " not found"));
    }
	
	@Override
	public List<Service_Board> getServiceBoardList() {
	    return sbRepo.findAll();
	}

	@Override
	public List<Service_Board> getAllServiceBoard() {
		return sbRepo.findAll();
	}

	@Override
	public void saveServiceBoard(Service_Board serviceBoard) {
		sbRepo.save(serviceBoard);
		
	}
}
