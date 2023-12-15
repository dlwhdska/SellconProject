package com.sellcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellcon.domain.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {

}
