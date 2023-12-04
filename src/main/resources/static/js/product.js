// 할인율 계산
$('.discount').each(function(index) {
    var price = $('.price').eq(index).text().replace(/\D/g, '');
    var sellingPrice = $('.sell_price').eq(index).text().replace(/\D/g, '');

    var productDiscount = ((price - sellingPrice) / price) * 100;
    $(this).text(Math.floor(productDiscount) + '%');
});

$(document).ready(function() {

	$(".product_find_brand a").on("click", function(e) {
		e.preventDefault();

		var categoryKind = $(this).data("brandcategory");
		var brandSeq = $(this).data("brand");

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
			},
			error: function(error) {
				console.log(error);
			}
		});
	});

	$("#Product_ListMenu a.lowprice, #Product_ListMenu a.highPrice").on("click", function(e) {
		e.preventDefault();

		var categoryKind = $(this).data("category");
		var brandSeq = $(".product_find_brand a.active").data("brand");
		var sortType = $(this).data("sort");

		$.ajax({
			url: "/updateProductList",
			method: "GET",
			data: {
				category: categoryKind,
				brand: brandSeq,
				sort: sortType
			},
			dataType: 'html',
			success: function(data) {
				$(".product_list_elements").html(data);
			},
			error: function(error) {
				console.log(error);
			}
		});
	});

});