function deleteOrder(cseq) {
	$(".prdInfo").each(function() {
		var findCseq = $(this).find("[name='cseq']").val();
		if (findCseq == cseq) {
			$(this).remove();
			var updatedSize = $("[name='cseq']").length;
			$("h3 > span").text(updatedSize);
			alert("해당 상품을 삭제했습니다.");
			
			updateTotalSummary();
		}
	});
}

function updateTotalSummary(){
	var sellingPrices = $('.sell_price');
    var prices = $('.price');
    var totalPrice = 0;
    var finalPrice = 0;

    sellingPrices.each(function(index) {
        var sellPrice = parseInt(prices.eq(index).text().replace(/\D/g, ''));
        var price = parseInt($(this).text().replace(/\D/g, ''));

        totalPrice += sellPrice;
        finalPrice += price;
    });

    var totalDiscountPrice = finalPrice - totalPrice;

    var totalProductPrice = $('.totalProductPrice');
    var totalDiscount = $('.totalDiscount');
    var finalPayment = $('.finalPayment');

    totalProductPrice.text(totalPrice.toLocaleString('ko-KR'));
    totalDiscount.text(totalDiscountPrice.toLocaleString('ko-KR'));
    finalPayment.text(finalPrice.toLocaleString('ko-KR'));
}