package com.sellcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Selling_Product;

public interface SellingProductRepository extends JpaRepository<Selling_Product, Long>, QuerydslPredicateExecutor<Selling_Product> {
	
	List<Selling_Product> findAllByOrderByRegdateDesc();
	
	List<Selling_Product> findAllByOrderBySellingpriceAsc();
	
	List<Selling_Product> findAllByOrderBySellingpriceDesc();
	
	//프로덕트 : 브랜드목록
	List<Selling_Product> findByProductBrand(Brand brand);
	
	//프로덕트 : 상품목록
	Page<Selling_Product> findAll(Pageable pageable);

	Page<Selling_Product> findAllByOrderByRegdateDesc(Pageable pageable);

	Page<Selling_Product> findAllByOrderBySellingpriceDesc(Pageable pageable);

	Page<Selling_Product> findAllByOrderBySellingpriceAsc(Pageable pageable);
	
	// 카테고리에따른 상품리스트
	@Query("SELECT sp FROM Selling_Product sp " +
            "JOIN sp.product p " +
            "JOIN p.brand b " +
            "JOIN b.category c " +
            "WHERE c.kind = :kind")
	Page<Selling_Product> findAllByProductBrandCategoryKind(Pageable pageable, String kind);
	
	// 브랜드에따른 상품리스트
	@Query("SELECT sp FROM Selling_Product sp WHERE sp.product.brand.bseq = :bseq")
	Page<Selling_Product> findAllByProductBrandBseq(Pageable pageable, Long bseq);
	
	// 프로덕트 디테일
	List<Selling_Product> findAllBySseq(Long sseq);
	
	// 판매페이지
	@Query("SELECT sp FROM Selling_Product sp JOIN sp.product p WHERE p.productName = :productName")
	List<Selling_Product> findByProductProductName(String productName);

	
}
