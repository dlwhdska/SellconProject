package com.sellcon.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Search;
import com.sellcon.domain.Selling_Product;

public interface ProductService {

	List<Selling_Product> getAllSellingProductRegDate();
	
	List<Selling_Product> getAllSellingProductPrice();
		
	Page<Selling_Product> getAllSellingProductListSortedBy(Pageable pageable, Search search, String sort);

	List<Brand> getAllBrandList();
	List<Brand> getBrandByCategoryKind(String kind);

	List<Selling_Product> getProductsByBrand(Long brandId);
	
	List<Selling_Product> getSellingProductById(Long sseq);
	
	Page<Selling_Product> getSellingProductByBseq(Pageable pageable, Long bseq);
	
	Page<Selling_Product> getSellingProductFilter(Pageable pageable, Long bseq, String kind);
	
	Page<Selling_Product> getSellingProductListAll(Pageable pageable, Long bseq);
//	Page<Selling_Product> getAllSellingProductList(Pageable pageable, Search search);
	
}