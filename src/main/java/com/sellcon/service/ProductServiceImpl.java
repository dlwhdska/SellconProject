package com.sellcon.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Member;
import com.sellcon.domain.Product;
import com.sellcon.domain.Search;
import com.sellcon.domain.Selling_Product;
import com.sellcon.repository.BrandRepository;
import com.sellcon.repository.ProductRepository;
import com.sellcon.repository.SellingProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private SellingProductRepository sproRepo;
	@Autowired
	private BrandRepository brandRepo;
	@Autowired
	private ProductRepository proRepo;
	@Autowired
	private ResourceLoader resourceLoader;

	// 메인 : 판매상품 최신순 정렬
	@Override
	public List<Selling_Product> getAllSellingProductRegDate() {
		return sproRepo.findAllByOrderByRegdateDesc();
	}

	// 메인 : 판매상품 가격순 정렬
	@Override
	public List<Selling_Product> getAllSellingProductPrice() {
		return sproRepo.findAllByOrderBySellingpriceAsc();
	}

	// 프로덕트 : 카테고리
	@Override
	public List<Brand> getBrandByCategoryKind(String kind) {
		return brandRepo.findAllByCategoryKind(kind);
	}

	// 프로덕트 : 브랜드
	@Override
	public List<Brand> getAllBrandList() {
		return brandRepo.findAllByOrderByBseq();
	}

	// 프로덕트 카테고리와 브랜드에따른 정렬
	@Override
	public Page<Selling_Product> getSellingProductFilter(Pageable pageable, Long bseq, String kind) {

		if (bseq != null) {
			return sproRepo.findAllByProductBrandBseq(pageable, bseq);
		} else {
			return sproRepo.findAllByProductBrandCategoryKind(pageable, kind);
		}

	}

	// 프로덕트 브랜드에따른 정렬
	@Override
	public Page<Selling_Product> getSellingProductByBseq(Pageable pageable, Long bseq) {
		return sproRepo.findAllByProductBrandBseq(pageable, bseq);
	}

	// 프로덕트 전체상품
	@Override
	public Page<Selling_Product> getSellingProductListAll(Pageable pageable, Long bseq) {

		if (bseq != null) {
			return sproRepo.findAllByProductBrandBseq(pageable, bseq);
		} else {
			return sproRepo.findAll(pageable);
		}

	}

	@Override
	public List<Selling_Product> getSellingProductById(Long sseq) {
		return sproRepo.findAllBySseq(sseq);
	}

	@Override
	public List<Selling_Product> getProductsByBrand(Long brandId) {
		Brand brand = brandRepo.findById(brandId).orElse(null);

		if (brand == null) {
			return List.of();
		}

		return sproRepo.findByProductBrand(brand);
	}

	// 판매 페이지
	public List<Brand> findAllBrandList() {
		return brandRepo.findAll();
	}

	public List<Brand> findByBrandNameSearch(String brandKeyword) {
		return brandRepo.findByBrandNameContaining(brandKeyword);
	}

	public List<Product> findByProductNameSearch(String brandName, String productKeyword) {
		return proRepo.findByBrandBrandNameAndProductNameContaining(brandName, productKeyword);
	}

	public List<Selling_Product> findSellingProductByProductName(String productName) {
		return sproRepo.findByProductProductName(productName);
	}

	// 판매상품 추가
	public List<Selling_Product> submitSellForm(Long pseq, int sellingprice, Long barcode, Date valid,
			MultipartFile barcodeImage, String sellerId) throws IOException {

		Selling_Product sproduct = new Selling_Product();
		Product product = new Product();
		Member member = new Member();
		
		String relativePath = "static/images/barcode";
		Resource resource = resourceLoader.getResource("classpath:" + relativePath);
		String imagePath = resource.getFile().getAbsolutePath();
		String imageName = System.currentTimeMillis() + "_" + barcodeImage.getOriginalFilename();
		String fullPath = imagePath + File.separator + imageName;

		member.setId(sellerId);
		product.setPseq(pseq);
		
		sproduct.setMember(member);
		sproduct.setProduct(product);
		sproduct.setSellingprice(sellingprice);
		sproduct.setBarcode(barcode);
		sproduct.setValid(valid);
		sproduct.setBarcode_image(fullPath);
	
		File dest = new File(fullPath);
		barcodeImage.transferTo(dest);
		
		sproRepo.save(sproduct);

		return sproRepo.findAll();
	}

	// 어드민페이지
	@Override
	public List<Selling_Product> getSellingProductListAdmin(){
		return sproRepo.findAll();
	}
}
