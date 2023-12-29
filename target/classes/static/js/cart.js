function showSuccessModal() {
  $('#myModal').css('display', 'block');
}

function addCart(sseq) {
    console.log("sseq=" + sseq);

    var data = {
        sseq: sseq
    };

    $.ajax({
        url: '/addToCart',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        error: function(xhr, status, error) {
            if (xhr.status === 401) {
                window.location.href = '/login';
            } else {
                console.error('상품 추가 중 오류가 발생했습니다.');
            }
        },
        success: function(response) {
            if (response.duplicate) {
                alert("이미 장바구니에 담긴 상품입니다.");
            } else {
                showSuccessModal();
            }
        }
    });
}

// 모달 닫기
$(document).ready(function() {
  $('#continueShoppingBtn').on('click', function() {
    $('#myModal').css('display', 'none');
  });

  $('.close').on('click', function() {
    $('#myModal').css('display', 'none');
  });
});

function toggleAllCheckBoxes(selectAll) {
    var isChecked = selectAll.checked;
    $('.selectCheckbox').prop('checked', isChecked);
}

function toggleIndividualCheckbox(selectCheckbox) {
    var selectAll = $('#selectAll').prop('checked');
    if (!selectCheckbox.checked) {
        $('#selectAll').prop('checked', false);
    }
}

function selectDeleteCart() {
    var selectedSseqs = [];

    $('.selectCheckbox:checked').each(function() {
        var sseq = $(this).data('sseq');
        if (sseq) {
            selectedSseqs.push(sseq);
            console.log('sseq=' + sseq);
        }
    });

    if (selectedSseqs.length === 0) {
        alert("삭제할 상품을 선택해주세요.");
        return;
    }

    $.ajax({
        url: '/selectDeleteCarts',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ sseqs: selectedSseqs }),
        success: function(response) {
            if (response) {
                alert("선택한 상품을 삭제했습니다.");
                location.reload(true);
            } else {
                console.error('상품 삭제 중 오류가 발생했습니다.');
            }
        },
        error: function(xhr, status, error) {
            console.error('요청 중 오류가 발생했습니다.', error);
        }
    });
}

function deleteCart(sseq) {
    console.log("sseq=" + sseq);

    var data = {
        sseq: sseq
    };

    $.ajax({
        url: '/deleteCart?sseq=' + sseq,
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(response) {
            if (response) {
                alert("장바구니 상품을 삭제했습니다.");
                location.reload();
            } else {
                console.error('장바구니 삭제 중 오류가 발생했습니다.');
            }
        },
        error: function(xhr, status, error) {
            console.error('요청 중 오류가 발생했습니다.', error);
        }
    });
}

function selectOrder() {
	var cartItemCount = parseInt($('#size').text());
	
	if (cartItemCount === 0) {
        alert('장바구니가 비어 있습니다!');
        return;
    }
    
    var selectedCseqs = [];
    
    $('.selectCheckbox:checked').each(function() {
        var cseq = $(this).data('cseq');
        if (cseq) {
            selectedCseqs.push(cseq);
            console.log('cseq=' + cseq);
        }
    });

    var ordersForm = $('#ordersForm');
    
    if(selectedCseqs.length == 0) {
		ordersForm.submit();
		
		return;
	}
    
    ordersForm.empty();

    selectedCseqs.forEach(function(cseq) {
        ordersForm.append('<input type="hidden" name="cseq" value="' + cseq + '">');
    });

    ordersForm.submit();
}

// totalsummary 영역 계산
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
