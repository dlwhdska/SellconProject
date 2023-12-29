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
	
	// 최신 상품
    @Query("SELECT sp FROM Selling_Product sp WHERE sp.product.brand.category.kind = :categoryKind ORDER BY sp.regdate DESC")
    List<Selling_Product> findLatestProductsByCategory(String categoryKind);
	
	//프로덕트 : 브랜드목록
	List<Selling_Product> findByProductBrand(Brand brand);
	
	//프로덕트 : 상품목록
	@Query("SELECT sp FROM Selling_Product sp WHERE sp.checkp = 'Y'")
	Page<Selling_Product> findAllByCheckp(Pageable pageable);

	Page<Selling_Product> findAllByOrderByRegdateDesc(Pageable pageable);

	Page<Selling_Product> findAllByOrderBySellingpriceDesc(Pageable pageable);

	Page<Selling_Product> findAllByOrderBySellingpriceAsc(Pageable pageable);
	
	// 카테고리에따른 상품리스트
	@Query("SELECT sp FROM Selling_Product sp " +
	        "JOIN sp.product p " +
	        "JOIN p.brand b " +
	        "JOIN b.category c " +
	        "WHERE c.kind = :kind AND sp.checkp = 'Y'")
	Page<Selling_Product> findAllByProductBrandCategoryKindAndCheckp(Pageable pageable, String kind);
	
	// 브랜드에따른 상품리스트
	@Query("SELECT sp FROM Selling_Product sp WHERE sp.product.brand.bseq = :bseq AND sp.checkp = 'Y'")
	Page<Selling_Product> findAllByProductBrandBseqAndCheckp(Pageable pageable, Long bseq);
	
	// 프로덕트 디테일
	List<Selling_Product> findAllBySseq(Long sseq);
	
	List<Selling_Product> findAllByProductPseqAndCheckp(Long pseq, String checkp);
	
	List<Selling_Product> findAllByProductBrandBseqAndCheckp(Long bseq, String checkp);
	
	// 판매페이지
	@Query("SELECT sp FROM Selling_Product sp JOIN sp.product p WHERE p.productName = :productName")
	List<Selling_Product> findByProductProductName(String productName);
	
	// 어드민페이지
	Selling_Product findBySseq(Long sseq);
	
	List<Selling_Product> findByProductPseq(Long pseq);
	
	// 마이페이지
	
	List<Selling_Product> findAllByMemberIdOrderBySseqDesc(String memberId);
	
	Page<Selling_Product> findAllByMemberIdAndCheckpIsNullOrderBySseqDesc(Pageable pageable, String memberId);
	
	Page<Selling_Product> findAllByMemberIdAndCheckpOrderBySseqDesc(Pageable pageable, String memberId, String checkp);

}
