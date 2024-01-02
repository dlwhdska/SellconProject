
$(document).ready(function() {
	localStorage.setItem("selectedCategory", "");
	updateProductList('');
});

/* 탭 스크립트 */
const tabs = document.querySelectorAll(".tab");
const contents = document.querySelectorAll(".cont");

tabs.forEach((tab, index) => {

	tab.addEventListener("click", function(event) {
		event.preventDefault();

		tabs.forEach((t) => t.classList.remove("current"));
		contents.forEach((c) => c.classList.remove("current"));

		tab.classList.add("current");
		contents[index].classList.add("current");
	});
});

/* 브랜드 검색 스크립트 */
var brandSearchContainer = $(".PD_product_brand");
var brandSearchShow = $(".PD_detail_search");
var brandSearchClose = $(".PD_search_box_coloseBtn");

brandSearchContainer.click(function(event) {
	event.stopPropagation();
	brandSearchShow.show();
});

function closeBrandSearchPopup() {
	console.log("Closing popup");
	brandSearchShow.hide();
}

brandSearchClose.click(function(event) {
	event.stopPropagation();
	closeBrandSearchPopup();
});

function filterBrands() {
	var keyword = $(".PD_serach_text input").val().toLowerCase();
	var brands = document.getElementsByClassName('brand-results')[0].children;

	for (var i = 0; i < brands.length; i++) {
		var brand = brands[i];
		var brandText = brand.textContent || brand.innerText;

		if (brandText.toLowerCase().indexOf(keyword) > -1) {
			brand.style.display = "";
		} else {
			brand.style.display = "none";
		}

		document.getElementById('filteredBrandList').scrollTop = 0;
	}
}

function selectBrand(element) {
	var brandName = element.getAttribute('data-brandName');
	document.getElementById('brandSearchInput').value = brandName;
}

function confirmBrand() {
	$(document).ready(function() {
		var selectedBrand = document.getElementById('brandSearchInput').value;

		if (!selectedBrand) {
			alert('브랜드를 선택해주세요.');
			return;
		}

		document.getElementById('sellBrandInput').value = selectedBrand;
		updateProductList(selectedBrand);
		closeBrandSearchPopup();

	});
}

$('#filteredBrandList div').click(function() {
	$(this).addClass('choice');
	$(this).siblings().removeClass('choice');
});

/* 상품 검색 스크립트 */
var productSearchContainer = $(".PD_product_name");
var productSearchShow = $(".PS_detail_search");
var productSearchClose = $(".PS_search_box_coloseBtn");
var allProducts = [];

// 상품검색 모달창 띄우기
productSearchContainer.click(function(event) {

	var selectedBrand = document.getElementById('sellBrandInput').value;
	if (!selectedBrand) {
		alert('브랜드를 먼저 선택해주세요.');
		return;
	}

	$('#filteredProductList div').click(function() {
		$(this).addClass('choice2');
		$(this).siblings().removeClass('choice2');
	});

	event.stopPropagation();
	productSearchShow.show();
});

// 상품검색 모달창 닫기
function closeProduct() {
	productSearchShow.hide();
}

productSearchClose.click(function(event) {
	event.stopPropagation();
	closeProduct();
});

function updateProductList(brandName) {
	$.ajax({
		url: '/getProductByBrand/' + brandName,
		type: 'GET',
		dataType: 'json',
		success: function(data) {
			allProducts = data;
			updateProductListView(allProducts);
		},
		error: function(error) {
			console.error('정보전달 실패요' + error);
		}
	});
}

function filterProducts(keyword) {
	var filteredProducts = allProducts.filter(function(product) {
		return (product.productName && typeof product.productName === 'string' && keyword) ?
			product.productName.toLowerCase().includes(keyword.toLowerCase()) :
			false;
	});
	updateProductListView(filteredProducts);
}

function updateProductListView(productList) {
	var filteredProductList = $('#filteredProductList');
	filteredProductList.empty();

	for (var i = 0; i < productList.length; i++) {
		var product = productList[i];
		var productItem = $('<div>')
			.addClass('product-item')
			.html('<span>' + product.productName + '</span>')
			.attr('data-productName', product.productName)
			.attr('data-pseq', product.pseq)
			.on('click', function() {
				selectProduct(this);
			});
		filteredProductList.append(productItem);
	}
}

$('#productSearchInput').on('input', function() {
	var keyword = $(this).val();
	filterProducts(keyword);
});

// pseq전역변수 선언
var selectedProductPseq;

function selectProduct(element) {
	var productName = element.getAttribute('data-productName');
	var pseq = element.getAttribute('data-pseq');
	selectedProductPseq = pseq;

	document.getElementById('productSearchInput').value = productName;
	document.querySelector('.PD_detailContants h3 span').innerText = productName;

	$.ajax({
		url: '/getSellingPriceList/' + productName,
		type: 'GET',
		dataType: 'json',
		success: function(data) {
			sellingPriceListView(data);
		},
		error: function(error) {
			console.error("정보전달 개같이 실패" + error);
		}
	});
}

// 이미지 업로드 미리보기
function previewImage(input) {

	var preview = document.getElementById('imgPreview');
	var label = document.getElementById('selectedImageLabel');
	var file = input.files[0];
	var reader = new FileReader;

	reader.onload = function(e) {
		preview.src = e.target.result;
		preview.style.display = 'block';
		label.innerText = file.name;
	}

	if (file) {
		reader.readAsDataURL(file);
	}

}

// 확인버튼
function confirmProduct() {
	$(document).ready(function() {
		var selectedProduct = document.getElementById('productSearchInput').value;

		if (!selectedProduct) {
			alert('상품을 선택해주세요');
			return;
		}

		// 선택된 상품의 가격 가져오기
		var selectedProductPrice = getProductPrice(selectedProduct);
		if (selectedProductPrice) {
			document.getElementById('sellProductInput').value = selectedProduct;
			document.getElementById('productPrice').innerText = selectedProductPrice;
			closeProduct();
		} else {
			alert('가격가겨오기 개같이 실패');
		}
	});
}

function getProductPrice(productName) {
	// productList는 서버에서 받아온 전체 상품 리스트로 가정
	for (var i = 0; i < allProducts.length; i++) {
		var product = allProducts[i];
		if (product.productName === productName) {
			return product.price;
		}
	}
	return null;
}


/* 판매 현황 */
function sellingPriceListView(sellingProduct) {
	// 그룹화할 맵 생성
	var groupedProducts = new Map();

	// 판매상품 리스트를 돌면서 가격을 기준으로 그룹화
	for (var i = 0; i < sellingProduct.length; i++) {
		var item = sellingProduct[i];
		var priceKey = item.sellingprice;

		// 그룹이 이미 존재하면 수량을 더하고, 없으면 새로운 그룹을 생성
		if (groupedProducts.has(priceKey)) {
			groupedProducts.set(priceKey, groupedProducts.get(priceKey) + 1);
		} else {
			groupedProducts.set(priceKey, 1);
		}

	}

	// 결과를 출력할 tbody 선택
	var tableBody = $('#cont1 table tbody');
	var firstRow = tableBody.children().first().detach();
	tableBody.empty().append(firstRow);

	// 그룹화된 결과를 테이블에 추가
	if (groupedProducts.size > 0) {
		groupedProducts.forEach(function(quantity, price) {
			var row = $('<tr>')
				.append('<td><p>' + price + '원</p></td>')
				.append('<td><p>20%</p></td>')  // 이 값은 정적으로 지정한 것입니다. 동적으로 값을 받아오는 경우 수정 필요
				.append('<td><p>' + quantity + '개</p></td>');

			// 생성된 행을 테이블에 추가
			tableBody.append(row);
		});
	} else {
		var noProductRow = $('<tr>')
			.append('<td colspan="3" class="no-product-message"><img src="images/yello_1.png" alt="로고"><p>현재 판매 중인 상품이 없습니다.</p></td>');
		tableBody.append(noProductRow);
	}
}


// 판매 신청하기
function submitSellForm() {

	var pseq = selectedProductPseq;
	var sellingprice = $('.PD_detail_selling_price input').val();
	var barcode = $('.barcode-input').map(function() {
		return $(this).val();
	}).get();
	var barcodeValue = barcode.join('');
	var valid = $('.PD_validity input').val();
	var barcode_image = $('#imageInput')[0].files[0];
	var sellid = document.getElementById('sellerId').value;

	console.log('Product Seq:', pseq);
	console.log('Selling Price:', sellingprice);
	console.log('Barcode:', barcodeValue);
	console.log('Validity Date:', valid);
	console.log('Barcode Image:', barcode_image);
	console.log('sellerId:', sellid);

	if (!pseq || !sellingprice || !barcodeValue || !valid || !barcode_image || !sellid) {
		alert('입력 정보를 모두 채워주세요.');
	}

	var formData = new FormData();
	formData.append('pseq', pseq);
	formData.append('sellingprice', sellingprice);
	formData.append('barcode', barcodeValue);
	formData.append('valid', valid);
	formData.append('barcode_image', barcode_image);
	formData.append('sellerId', sellid);

	console.log(formData);

	$.ajax({
		type: 'POST',
		url: '/submitSellForm',
		data: formData,
		processData: false,
		contentType: false,
		dataType: 'json',
		success: function(response) {
			console.log(response);
			console.log('판매 신청 성공');
			alert('판매 신청이 완료되었습니다.');
			window.location.href = '/mypage';
		},
		error: function(error) {
			console.error(error);
		}
	});

}

// 날짜제한
var currentDate = new Date();

var minDate = new Date(currentDate);
minDate.setDate(currentDate.getDate() + 2);

$('.PD_detail_validity').attr('min', minDate.toISOString().split('T')[0]);
$('.PD_detail_validity').val(minDate.toISOString().split('T')[0]);

// 판매가격 제한
$('.PD_product_selling_price input').on('blur', function() {

	var sellingPrice = parseFloat($(this).val());
	var productPrice = parseFloat($('#productPrice').text().replace(/[^\d]/g, ''));
	var marginTop = $('#header_wrap').outerHeight() + 50;

	$('html, body').animate({
		scrollTop: $('#cont1').offset().top - marginTop
	}, 500);

	if (sellingPrice < 1 || sellingPrice > productPrice) {
		alert('올바른 판매가를 입력해주세요.');
		$(this).val('');
	}

});

// 바코드 제한
$('.barcode-input').on('input', function() {
	var position = $(this).data('position');
	var currentValue = $(this).val().replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거

	if (currentValue.length > 4) {
		// 입력된 숫자가 4자리를 넘어갈 경우 자르기
		currentValue = currentValue.slice(0, 4);
	}

	$(this).val(currentValue);

	if (currentValue.length === 4) {
		// 입력된 숫자가 4자리일 때만 다음 입력란으로 포커스 이동
		var nextInput = $('.barcode-input[data-position="' + (position + 1) + '"]');
		if (nextInput.length > 0) {
			nextInput.focus();
		}
	}
});



