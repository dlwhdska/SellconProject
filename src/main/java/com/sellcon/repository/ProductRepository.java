package com.sellcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sellcon.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
