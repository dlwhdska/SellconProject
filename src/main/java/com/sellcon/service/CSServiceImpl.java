package com.sellcon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Admin;
import com.sellcon.domain.CS;
import com.sellcon.repository.AdminRepository;
import com.sellcon.repository.CSRepository;

@Service
public class CSServiceImpl implements CSService {
	@Autowired
	private CSRepository csRepo;
	@Autowired
	private AdminRepository adminRepo;

	@Override
	public void saveCS(CS cs) {
		csRepo.save(cs);
	}

	@Override
	public void saveCSWithAdmin(String adminId) {
		Admin admin = adminRepo.findById(adminId).orElse(null);

		if (admin != null) {
			CS cs = new CS();
			cs.setAdmin(admin);
			csRepo.save(cs);
		}
	}

	@Override
	public CS updateCS(Long csseq, CS updatedCS) {
		Optional<CS> existingCS = csRepo.findById(csseq);
		if (existingCS.isPresent()) {
			updatedCS.setCsseq(csseq);
			return csRepo.save(updatedCS);
		}
		return null;
	}
	
	@Override
	public void deleteCS(Long csseq) {
		csRepo.deleteById(csseq);
	} 

	@Override
	public CS findCSById(Long csseq) {
		return csRepo.findById(csseq).orElse(null);
	}

	@Override
	public List<CS> getAllCSList() {
		return csRepo.findAll();
	}
}
