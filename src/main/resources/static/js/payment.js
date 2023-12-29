var IMP = window.IMP;
IMP.init("imp45262242");

function requestPayWithKakaoPay() {
	var productNames = $('.pName');
	var numberOfProducts = productNames.length;
	var finalPaymentValue = $('.finalPayment').text();

	if (isOrderEmpty()) {
		return;
	}


	if (numberOfProducts > 1) {
		// 상품명이 여러 개인 경우
		var firstProductName = productNames.eq(0).text();
		var productName = firstProductName + " 외 " + (numberOfProducts - 1) + "건 추가";
	} else {
		// 상품명이 하나인 경우
		var productName = productNames.text();
	}

	IMP.request_pay({
		pg: 'kakaopay',
		merchant_uid: "sellcon" + new Date().getTime(),
		name: productName,
		amount: finalPaymentValue,
		buyer_email: 'sellcon@email.com',
		buyer_name: '셀콘 기술지원팀',
		buyer_tel: '010-1234-5678',
		buyer_addr: '서울특별시 강남구 삼성동',
		buyer_postcode: '123-456'

	},
		function(rsp) {
			if (rsp.success) {
				var msg = '결제가 완료되었습니다.';
				console.log(rsp);

				var form = $('#addOrdersForm');
				$('#addOrdersForm').submit();
			} else {
				var msg = '결제에 실패하였습니다.';
				msg += rsp.error_msg;
				console.log(rsp);
			}
			alert(msg);
		});
}

var IMP = window.IMP;
IMP.init("imp45262242");

function requestPayWithInicis() {
	var productNames = $('.pName');
	var numberOfProducts = productNames.length;
	
	if (isOrderEmpty()) {
		return;
	}

	var finalPaymentValue = $('.finalPayment').text();
	finalPaymentValue = parseInt(finalPaymentValue.replace(',', ''));

	if (numberOfProducts > 1) {
		// 상품명이 여러 개인 경우
		var firstProductName = productNames.eq(0).text();
		var productName = firstProductName + " 외 " + (numberOfProducts - 1) + "건 추가";
	} else {
		// 상품명이 하나인 경우
		var productName = productNames.text();
	}

	IMP.request_pay({
		pg: 'html5_inicis.INIpayTest',
		pay_method: 'card',
		merchant_uid: "sellcon" + new Date().getTime(),
		name: productName,
		amount: finalPaymentValue,
		buyer_email: 'sellcon@email.com',
		buyer_name: '셀콘 기술지원팀',
		buyer_tel: '010-1234-5678',
		buyer_addr: '서울특별시 강남구 삼성동',
		buyer_postcode: '123-456'
	}, function(rsp) { // callback
		if (rsp.success) {
			var form = $('#addOrdersForm');
			$('#addOrdersForm').submit();
			console.log(rsp);
		} else {
			console.log(rsp);
		}
	});
}


function isOrderEmpty() {
    var ordersItemCount = parseInt($('#size').text());
    if (ordersItemCount === 0) {
        alert('주문 상품이 비어 있습니다.');
        return true;
    }
    return false;
}
