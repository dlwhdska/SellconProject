package com.sellcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sellcon.domain.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {

	List<Brand> findAllByOrderByBseq();
	
	@Query("SELECT b FROM Brand b WHERE b.category.kind = :kind")
	List<Brand> findAllByCategoryKind(@Param("kind") String kind);
	
	List<Brand> findByBrandNameContaining(String brandkeyword);
	
}
