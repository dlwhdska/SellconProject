package com.sellcon.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Product;
import com.sellcon.domain.Search;
import com.sellcon.domain.Selling_Product;

public interface ProductService {

	List<Selling_Product> getAllSellingProductRegDate();

	List<Selling_Product> getAllSellingProductPrice();

	List<Brand> getAllBrandList();

	List<Brand> getBrandByCategoryKind(String kind);

	List<Selling_Product> getProductsByBrand(Long brandId);

	List<Selling_Product> getSellingProductById(Long sseq);

	Page<Selling_Product> getSellingProductByBseq(Pageable pageable, Long bseq);

	Page<Selling_Product> getSellingProductFilter(Pageable pageable, Long bseq, String kind);

	Page<Selling_Product> getSellingProductListAll(Pageable pageable, Long bseq);

	// 판매페이지
	List<Brand> findAllBrandList();

	List<Brand> findByBrandNameSearch(String brandKeyword);

	List<Product> findByProductNameSearch(String brandName, String productKeyword);

	List<Selling_Product> findSellingProductByProductName(String productName);

	// 판매 신청
	List<Selling_Product> submitSellForm(Long pseq, int sellingprice, Long barcode, Date valid, MultipartFile barcodeImage, String sellerId) throws IOException;
	
	// 어드민페이지
	List<Selling_Product> getSellingProductListAdmin();

}