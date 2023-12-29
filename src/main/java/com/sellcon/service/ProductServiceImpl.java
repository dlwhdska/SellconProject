package com.sellcon.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sellcon.domain.Brand;
import com.sellcon.domain.Category;
import com.sellcon.domain.Member;
import com.sellcon.domain.Product;
import com.sellcon.domain.Search;
import com.sellcon.domain.Selling_Product;
import com.sellcon.repository.BrandRepository;
import com.sellcon.repository.CartRepository;
import com.sellcon.repository.CategoryRepository;
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
	private CategoryRepository cateRepo;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private CartRepository cartRepo;

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

	// 프로덕트 카테고리에따른 정렬
	@Override
	public Page<Selling_Product> getSellingProductFilter(Pageable pageable, Long bseq, String kind) {

		if (bseq != null) {
			return sproRepo.findAllByProductBrandBseqAndCheckp(pageable, bseq);
		} else {
			return sproRepo.findAllByProductBrandCategoryKindAndCheckp(pageable, kind);
		}

	}

	// 프로덕트 브랜드에따른 정렬
	@Override
	public Page<Selling_Product> getSellingProductByBseq(Pageable pageable, Long bseq) {
		return sproRepo.findAllByProductBrandBseqAndCheckp(pageable, bseq);
	}

	// 프로덕트 전체상품
	@Override
	public Page<Selling_Product> getSellingProductListAll(Pageable pageable, Long bseq) {

		if (bseq != null) {
			return sproRepo.findAllByProductBrandBseqAndCheckp(pageable, bseq);
		} else {
			return sproRepo.findAllByCheckp(pageable);
		}

	}

	// 프로덕트 디테일
	@Override
	public List<Selling_Product> getSellingProductById(Long sseq) {
		return sproRepo.findAllBySseq(sseq);
	}

	@Override
	public List<Selling_Product> getSellingProductSamePseq(Long pseq, String checkp) {
		checkp = "Y";
		return sproRepo.findAllByProductPseqAndCheckp(pseq, checkp);
	}

	@Override
	public List<Selling_Product> getSellingProductRecomend(Brand bseq, String checkp) {
		checkp = "Y";
		return sproRepo.findAllByProductBrandBseqAndCheckp(bseq.getBseq(), checkp);
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
		String dbImageName = "/images/barcode/" + imageName;

		member.setId(sellerId);
		product.setPseq(pseq);

		sproduct.setMember(member);
		sproduct.setProduct(product);
		sproduct.setSellingprice(sellingprice);
		sproduct.setBarcode(barcode);
		sproduct.setValid(valid);
		sproduct.setBarcode_image(dbImageName);

		File dest = new File(fullPath);
		barcodeImage.transferTo(dest);

		sproRepo.save(sproduct);

		return sproRepo.findAll();
	}

	// 어드민페이지
	@Override
	public List<Selling_Product> getSellingProductListAdmin() {
		return sproRepo.findAll();
	}

	@Override
	public List<Product> getProductListAdmin() {
		return proRepo.findAll();
	}

	// 브랜드
	@Override
	public Brand getBrandByBseq(Long bseq) {
		return brandRepo.findByBseq(bseq);
	}

	@Override
	public List<Category> getCategoryList() {
		return cateRepo.findAll();
	}

	@Override
	public List<Brand> getSelectedCategory(Long bseq) {
		return brandRepo.findCategoryByBseq(bseq);
	}

	// 판매
	@Override
	public Selling_Product getSellingProductBySseq(Long sseq) {
		return sproRepo.findBySseq(sseq);
	}

	// 프로덕트
	@Override
	public Product getProductByPseq(Long pseq) {
		return proRepo.findByPseq(pseq);
	}

	@Override
	public List<Brand> getBrandList() {
		return brandRepo.findAll();
	}

	@Override
	public List<Product> getSelectedBrand(Long pseq) {
		return proRepo.findBrandByPseq(pseq);
	}

	@Override
	public List<Brand> updateBrandList(Long bseq, String kind, String brandName,MultipartFile brandImage)
			throws IOException {

		Optional<Brand> brand = brandRepo.findById(bseq);

		if (brand.isPresent()) {

			Category category = new Category();
			Brand nbrand = brand.get();

			if (kind != null && !kind.isEmpty()) {
				category.setKind(kind);
				nbrand.setCategory(category);
			}

			if (brandName != null && !brandName.isEmpty()) {
				nbrand.setBrandName(brandName);
			}

			if (brandImage != null && !brandImage.isEmpty()) {

				String relativePath = "static/images/brand";
				Resource resource = resourceLoader.getResource("classpath:" + relativePath);
				String imagePath = resource.getFile().getAbsolutePath();
				String imageName = brandImage.getOriginalFilename();
				String fullPath = imagePath + File.separator + imageName;
				String dbImageName = "/images/brand/" + imageName;

				File dest = new File(fullPath);
				brandImage.transferTo(dest);

				nbrand.setBrandImage(dbImageName);

			}

			brandRepo.save(nbrand);

			return brandRepo.findAll();
		} else {
			return null;
		}
	}

	@Override
	public List<Selling_Product> updateSProductListCheckp(Long sseq, String checkp) {

		Optional<Selling_Product> sproduct = sproRepo.findById(sseq);

		if (sproduct.isPresent()) {
			Selling_Product nsproduct = sproduct.get();
			nsproduct.setCheckp(checkp);
			sproRepo.save(nsproduct);

			return sproRepo.findAll();
		} else {
			return null;
		}
	}

	@Override
	public List<Selling_Product> updateSellingProductList(Long sseq, Long bseq, Long pseq, Integer sellingprice,
			Long barcode, Date valid, MultipartFile barcode_image) throws IOException {

		Optional<Selling_Product> sproduct = sproRepo.findById(sseq);

		if (sproduct.isPresent()) {

			Brand brand = new Brand();
			Product product = new Product();
			Selling_Product nsproduct = sproduct.get();

			if (bseq != null) {
				brand.setBseq(bseq);
				nsproduct.getProduct().setBrand(brand);
			}

			if (pseq != null) {
				product.setPseq(pseq);
				nsproduct.setProduct(product);
			}

			if (sellingprice != null && sellingprice != 0) {
				nsproduct.setSellingprice(sellingprice);
			}

			if (barcode != null && barcode != 0) {
				nsproduct.setBarcode(barcode);
			}

			if (valid != null) {
				nsproduct.setValid(valid);
			}

			if (barcode_image != null && !barcode_image.isEmpty()) {
				String relativePath = "static/images/barcode";
				Resource resource = resourceLoader.getResource("classpath:" + relativePath);
				String imagePath = resource.getFile().getAbsolutePath();
				String imageName = System.currentTimeMillis() + "_" + barcode_image.getOriginalFilename();
				String fullPath = imagePath + File.separator + imageName;
				String dbImageName = "/images/barcode/" + imageName;

				File dest = new File(fullPath);
				barcode_image.transferTo(dest);

				nsproduct.setBarcode_image(dbImageName);
			}

			sproRepo.save(nsproduct);

			return sproRepo.findAll();

		} else {
			return null;
		}

	}

	@Override
	public List<Product> updateProductList(Long pseq, Long bseq, String productName, String content,
			MultipartFile image, Integer price) throws IOException {

		Optional<Product> product = proRepo.findById(pseq);

		if (product.isPresent()) {

			Brand brand = new Brand();
			Product nproduct = product.get();

			if (bseq != null) {
				brand.setBseq(bseq);
				nproduct.setBrand(brand);
			}

			if (productName != null && !productName.isEmpty()) {
				nproduct.setProductName(productName);
			}

			if (content != null && !content.isEmpty()) {
				nproduct.setContent(content);
			}

			if (image != null && !image.isEmpty()) {
				String relativePath = "static/images/product";
				Resource resource = resourceLoader.getResource("classpath:" + relativePath);
				String imagePath = resource.getFile().getAbsolutePath();
				String imageName = image.getOriginalFilename();
				String fullPath = imagePath + File.separator + imageName;
				String dbImageName = "/images/product/" + imageName;

				File dest = new File(fullPath);
				image.transferTo(dest);

				nproduct.setImage(dbImageName);
			}

			if (price != null && price != 0) {
				nproduct.setPrice(price);
			}

			proRepo.save(nproduct);

			return proRepo.findAll();
		} else {
			return null;
		}

	}

	@Override
	public List<Brand> uploadBrandList(String kind, String brandName, MultipartFile brandImage) throws IOException {

		Category category = new Category();
		Brand brand = new Brand();

		String relativePath = "static/images/brand";
		Resource resource = resourceLoader.getResource("classpath:" + relativePath);
		String imagePath = resource.getFile().getAbsolutePath();
		String imageName = brandImage.getOriginalFilename();
		String fullPath = imagePath + File.separator + imageName;
		String dbImageName = "/images/brand/" + imageName;

		category.setKind(kind);
		brand.setCategory(category);
		brand.setBrandName(brandName);
		brand.setBrandImage(dbImageName);

		File dest = new File(fullPath);
		brandImage.transferTo(dest);

		brandRepo.save(brand);

		return brandRepo.findAll();

	}

	@Override
	public List<Product> uploadProductList(Long bseq, String productName, MultipartFile image, String content,
			int price) throws IOException {

		Brand brand = new Brand();
		Product product = new Product();

		String relativePath = "static/images/product";
		Resource resource = resourceLoader.getResource("classpath:" + relativePath);
		String imagePath = resource.getFile().getAbsolutePath();
		String imageName = image.getOriginalFilename();
		String fullPath = imagePath + File.separator + imageName;
		String dbImageName = "/images/product/" + imageName;

		brand.setBseq(bseq);
		product.setBrand(brand);
		product.setProductName(productName);
		product.setImage(dbImageName);
		product.setContent(content);
		product.setPrice(price);

		File dest = new File(fullPath);
		image.transferTo(dest);

		proRepo.save(product);

		return proRepo.findAll();

	}

	@Override
	public List<Product> findProductByBseq(Long bseq) {
		return proRepo.findByBrandBseq(bseq);
	}

	@Override
	public List<Selling_Product> deleteSellingProduct(Long sseq) {

		Optional<Selling_Product> sproduct = sproRepo.findById(sseq);
		List<Selling_Product> nsproduct = new ArrayList<>();

		if (sproduct.isPresent()) {
			cartRepo.deleteBySseq(sseq);
			sproRepo.deleteById(sseq);
			nsproduct.add(sproduct.get());
		}

		return nsproduct;
	}

	@Override
	public List<Product> deleteProduct(Long pseq) {

		Optional<Product> product = proRepo.findById(pseq);

		if (product.isPresent()) {
			Product nproduct = product.get();
			List<Selling_Product> sproduct = sproRepo.findByProductPseq(pseq);

			if (sproduct.isEmpty()) {
				proRepo.delete(nproduct);
			} else {
				throw new RuntimeException("판매 중인 상품이 존재하여 삭제할 수 없습니다.");
			}
		}

		return proRepo.findAll();
	}

	@Override
	public List<Brand> deleteBrand(Long bseq) {

		Optional<Brand> brand = brandRepo.findById(bseq);

		if (brand.isPresent()) {
			Brand nbrand = brand.get();
			List<Product> product = proRepo.findByBrandBseq(bseq);

			if (product.isEmpty()) {
				brandRepo.delete(nbrand);
			} else {
				throw new RuntimeException("상품이 존재하여 삭제할 수 없습니다.");
			}
		}

		return brandRepo.findAll();

	}

	// 마이페이지
	@Override
	public Map<String, Long> salesApplication(String memberId) {
		List<Selling_Product> sproductList = sproRepo.findAllByMemberIdOrderBySseqDesc(memberId);

		long countY = sproductList.stream().filter(sproduct -> "Y".equals(sproduct.getCheckp())).count();
		long countNull = sproductList.stream().filter(sproduct -> sproduct.getCheckp() == null).count();

		Map<String, Long> counts = new HashMap<>();
		counts.put("countY", countY);
		counts.put("countNull", countNull);

		// 기본값
		counts.putIfAbsent("countY", 0L);
		counts.putIfAbsent("countWithNull", 0L);

		return counts;
	}

	@Override
	public Page<Selling_Product> salesApplicationList(Pageable pageable, String memberId) {
		return sproRepo.findAllByMemberIdAndCheckpIsNullOrderBySseqDesc(pageable, memberId);
	}

	@Override
	public Page<Selling_Product> salesApplicationListY(Pageable pageable, String memberId, String checkp) {
		return sproRepo.findAllByMemberIdAndCheckpOrderBySseqDesc(pageable, memberId, checkp);
	}

	// 최신 상품
	public List<Selling_Product> findNewProductsByCategory(String kind) {
		return sproRepo.findLatestProductsByCategory(kind);
	}

}
