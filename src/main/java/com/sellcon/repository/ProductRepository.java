package com.sellcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sellcon.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p JOIN p.brand b WHERE b.brandName = :brandName AND p.productName LIKE %:productKeyword%")
	List<Product> findByBrandBrandNameAndProductNameContaining(@Param("brandName") String brandName, @Param("productKeyword") String productKeyword);
	
	Product findByPseq(Long pseq);

	List<Product> findBrandByPseq(Long pseq);
	
	List<Product> findByBrandBseq(Long bseq);
	
}
