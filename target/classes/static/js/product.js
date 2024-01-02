
$(document).ready(function() {

	localStorage.setItem("baseSort", "lowprice");
	var saveSort = localStorage.getItem("baseSort");
	$(".pd_sort a[data-sort='" + saveSort + "']").addClass("active");

	var urlParams = new URLSearchParams(window.location.search);
	var brandSeqFromURL = urlParams.get('brand');
	$(".product_find_brand a[data-brand='" + brandSeqFromURL + "']").addClass("activebrand").parent().addClass("activebrand");
	var categoryKingFromURL = urlParams.get('category')
	if (categoryKingFromURL !== null) {
		localStorage.setItem("selectedCategory", categoryKingFromURL);
	}

	// 카테고리 클릭
	var selectedCategory = localStorage.getItem("selectedCategory");
	
	if (selectedCategory === null) {
		$(".product_sub ul li:first-child").addClass("selected");
	} else {
		$(".product_sub ul li").removeClass("selected");
		$('.product_sub ul li a[data-category="' + selectedCategory + '"]').parent().addClass("selected");
	}

	$(".product_sub ul li a").on("click", function(e) {
		e.preventDefault();

		console.log("A link clicked!");

		var selectedCategory = $(this).data("category");
		localStorage.setItem("selectedCategory", selectedCategory);

		$(".product_sub ul li").removeClass("selected");
		$(this).parent().addClass("selected");

		window.location.href = $(this).attr("href");
	});

	// 브랜드 클릭
	$(".product_find_brand a").on("click", function(e) {
		e.preventDefault();

		var categoryKind = $(this).data("brandcategory");
		var brandSeq = $(this).data("brand");

		console.log("Category Kind: " + categoryKind);
		console.log("Brand Seq: " + brandSeq);

		$("#selected_brand_category").val(categoryKind);
		$("#selected_brand").val(brandSeq);

		$.ajax({
			url: "/updateProductList",
			method: "GET",
			data: {
				category: categoryKind,
				brand: brandSeq
			},
			dataType: 'html',
			success: function(data) {
				$(".product_list_elements").html(data);

				$(".product_find_brand a[data-brand='" + brandSeq + "']").addClass("activebrand").parent().addClass("activebrand");
				$(".product_find_brand a:not([data-brand='" + brandSeq + "'])").removeClass("activebrand").parent().removeClass("activebrand");
				$(".pd_sort a[data-sort='" + saveSort + "']").addClass("active");
			},
			error: function(error) {
				console.log(error);
			}
		});
	});

	// 상품정렬 클릭
	$(document).on("click", ".pd_sort a", function(e) {
		e.preventDefault();

		var selectedCategory = localStorage.getItem("selectedCategory");
		var categoryKind = $("#selected_brand_category").val();
		var brandSeq = $("#selected_brand").val();
		var sort = $(this).data("sort");

		console.log("Selected Category from localStorage: " + selectedCategory);
		console.log("Category Kind: " + categoryKind);
		console.log("Brand Seq: " + brandSeq);
		console.log("Sort: " + sort);

		$('#selected_sort').val(sort);

		$.ajax({
			url: "/updateProductList",
			method: "GET",
			data: {
				sort: sort,
				category: selectedCategory,
				brand: brandSeq
			},
			dataType: 'html',
			success: function(data) {
				$(".product_list_elements").html(data);

				$(".pd_sort a[data-sort='" + sort + "']").addClass("active");
				$(".pd_sort a:not([data-sort='" + sort + "'])").removeClass("active");
			},
			error: function(error) {
				console.log(error);
			}
		});
	});

	// 페이지 클릭
	$(document).on("click", '.product_paginate_container a[data-page]', function(e) {
		e.preventDefault();

		var totalPages = 0;
		var page = parseInt($(this).data("page"));
		var selectedCategory = localStorage.getItem("selectedCategory");
		var categoryKind = $("#selected_brand_category").val();
		var brandSeq = $("#selected_brand").val();
		var selectedSort = $('#selected_sort').val() || "lowprice";;

		console.log("Selected Category from localStorage: " + selectedCategory);
		console.log("Category Kind: " + categoryKind);
		console.log("Brand Seq: " + brandSeq);
		console.log("Sort: " + selectedSort);
		console.log("Page Number: " + page);

		$.ajax({
			url: "/updateProductList",
			method: "GET",
			data: {
				page: page,
				category: selectedCategory,
				brand: brandSeq,
				sort: selectedSort
			},
			dataType: 'html',
			success: function(data) {
				$(".product_list_elements").html(data);

				$(".pd_sort a[data-sort='" + selectedSort + "']").addClass("active");

				$('li').removeClass('paginate_on');
				$('li:has(a[data-page="' + page + '"])').addClass('paginate_on');

				if (page > 0) {
					$(".pagenate_prev").attr("th:data-page", page - 1);
				} else {
					$(".pagenate_prev").attr("th:data-page", 0);
				}

				if (page < totalPages - 1) {
					$(".pagenate_next").attr("th:data-page", page + 1);
				} else {
					$(".pagenate_next").attr("th:data-page", totalPages - 1);
				}

				var productListPosition = $('.product_section2').offset().top + $('.product_section2').outerHeight();
				$('html, body').scrollTop(productListPosition);

			},
			error: function() {
				console.log("페이지 로드 실패");
			}
		});
	});

});