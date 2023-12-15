package com.sellcon.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Admin;
import com.sellcon.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepo;

	@Override
	public Admin getId(Admin admin) {
		
		Optional<Admin> findId = adminRepo.findById(admin.getId());
		if(findId.isPresent()) {
			return findId.get();
		}else {
			return null;
		}
		
	}
}
