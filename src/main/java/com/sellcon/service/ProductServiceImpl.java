package com.sellcon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Search;
import com.sellcon.domain.Selling_Product;
import com.sellcon.repository.BrandRepository;
import com.sellcon.repository.SellingProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private SellingProductRepository sproRepo;
	@Autowired
	private BrandRepository brandRepo;
	
	// 메인 : 판매상품 최신순 정렬
	@Override
	public List<Selling_Product> getAllSellingProductRegDate(){
		return sproRepo.findAllByOrderByRegdateDesc();
	}
	
	// 메인 : 판매상품 가격순 정렬
	@Override
	public List<Selling_Product> getAllSellingProductPrice(){
		return sproRepo.findAllByOrderBySellingpriceAsc();
	}
	
	// 프로덕트 : 카테고리
	@Override
	public List<Brand> getBrandByCategoryKind(String kind){
		return brandRepo.findAllByCategoryKind(kind);
	}
	
	// 프로덕트 : 브랜드
	@Override
	public List<Brand> getAllBrandList(){
		return brandRepo.findAllByOrderByBseq();
	}
	
	// 프로덕트 카테고리와 브랜드에따른 정렬
	@Override
	public Page<Selling_Product> getSellingProductFilter(Pageable pageable, Long bseq, String kind){
		
		if(bseq != null) {
			return sproRepo.findAllByProductBrandBseq(pageable, bseq);
		}else {
			return sproRepo.findAllByProductBrandCategoryKind(pageable, kind);
		}
		
	}
	
	// 프로덕트 브랜드에따른 정렬
	@Override
	public Page<Selling_Product> getSellingProductByBseq(Pageable pageable, Long bseq){
		return sproRepo.findAllByProductBrandBseq(pageable, bseq);
	}
	
	// 프로덕트 전체상품
	@Override
	public Page<Selling_Product> getSellingProductListAll(Pageable pageable, Long bseq){
		
		if(bseq != null) {
			return sproRepo.findAllByProductBrandBseq(pageable, bseq);
		}else {
			return sproRepo.findAll(pageable);
		}
		
	}
	
	@Override
	public Page<Selling_Product> getAllSellingProductListSortedBy(Pageable pageable, Search search, String sort){
		
		Page<Selling_Product> sproduct;
		
		switch(sort) {
			
			case "latest":
				sproduct = sproRepo.findAllByOrderByRegdateDesc(pageable);
				break;
			
			case "lowprice":
				sproduct = sproRepo.findAllByOrderBySellingpriceAsc(pageable);
				break;
			
			case "highprice":
				sproduct = sproRepo.findAllByOrderBySellingpriceDesc(pageable);
				break;
				
			default:
				sproduct = sproRepo.findAll(pageable);
				
		}
		
		return sproduct;
	}
	
	@Override
	public List<Selling_Product> getSellingProductById(Long sseq){
		return sproRepo.findAllBySseq(sseq);
	}
	
	@Override
	public List<Selling_Product> getProductsByBrand(Long brandId){
		Brand brand = brandRepo.findById(brandId).orElse(null);
		
		if(brand == null) {
			return List.of();
		}
		
		return sproRepo.findByProductBrand(brand);
	}
	
//	@Override
//	public Page<Selling_Product> getAllSellingProductList(Pageable pageable, Search search){
//		
//		BooleanBuilder builder = new BooleanBuilder();
//		QSelling_Product sproduct = QSelling_Product.selling_Product;
//		
//	    if (search.getSearchProduct() != null) {
//	        builder.and(sproduct.product.product_name.like("%" + search.getSearchProduct() + "%"));
//	    }
//		
//		return sproRepo.findAll(builder, pageable);
//	}

}
