package com.sellcon.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Category;
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
	
	List<Selling_Product> getSellingProductSamePseq(Long pseq, String checkp);

	Page<Selling_Product> getSellingProductByBseq(Pageable pageable, Long bseq);

	Page<Selling_Product> getSellingProductFilter(Pageable pageable, Long bseq, String kind);

	Page<Selling_Product> getSellingProductListAll(Pageable pageable, Long bseq);

	// 판매페이지
	List<Brand> findAllBrandList();

	List<Brand> findByBrandNameSearch(String brandKeyword);

	List<Product> findByProductNameSearch(String brandName, String productKeyword);

	List<Selling_Product> findSellingProductByProductName(String productName);
	
	List<Selling_Product> getSellingProductRecomend(Brand bseq, String checkp);

	// 판매 신청
	List<Selling_Product> submitSellForm(Long pseq, int sellingprice, Long barcode, Date valid, MultipartFile barcodeImage, String sellerId) throws IOException;
	
	// 어드민페이지
	List<Selling_Product> getSellingProductListAdmin();
	
	List<Product> getProductListAdmin();
	
	// 카테고리, 브랜드 설정
	Brand getBrandByBseq(Long bseq);
	
	List<Category> getCategoryList();
	
	List<Brand> getSelectedCategory(Long bseq);
	
	List<Brand> updateBrandList(Long bseq, String kind, String brandName, MultipartFile brandImage) throws IOException;
	
	List<Brand> uploadBrandList(String kind, String brandName, MultipartFile brandImage) throws IOException;
	
	// 프로덕트 설정
	Product getProductByPseq(Long pseq);
	
	List<Brand> getBrandList();
	
	List<Product> getSelectedBrand(Long pseq);
	
	List<Product> updateProductList(Long pseq, Long bseq, String productName, String content, MultipartFile image, Integer price) throws IOException;
	
	List<Product> uploadProductList(Long bseq, String productName, MultipartFile image, String content, int price) throws IOException;
	
	// 판매신청 설정
	List<Selling_Product> updateSProductListCheckp(Long sseq, String checkp);
	
	Selling_Product getSellingProductBySseq(Long sseq);
	
	List<Product> findProductByBseq(Long bseq);
	
	List<Selling_Product> updateSellingProductList(Long sseq, Long bseq, Long pseq,Integer sellingprice, Long barcode, Date valid, MultipartFile barcode_image) throws IOException;
	
	List<Selling_Product> deleteSellingProduct(Long sseq);

	List<Product> deleteProduct(Long pseq);
	
	List<Brand> deleteBrand(Long bseq);
	
	// 마이페이지
	Map<String, Long> salesApplication(String memberId);
	
	Page<Selling_Product> salesApplicationList(Pageable pageable, String memberId);
	
	Page<Selling_Product> salesApplicationListY(Pageable pageable, String memberId, String checkp);
	
	// 최신 등록 상품
	List<Selling_Product> findNewProductsByCategory(String kind);
	
	public void updateSellingProductCheckp(Selling_Product sellingProduct);

}