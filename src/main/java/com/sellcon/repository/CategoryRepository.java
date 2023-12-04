package com.sellcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellcon.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
	

}
