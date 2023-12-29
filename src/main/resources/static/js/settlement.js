// 정산 상태에 따른 정렬 처리
$(document).ready(function() {
    $('#stynY, #stynN, #stynR').on('click', function(e) {
        e.preventDefault();
        var styn = $(this).data('styn');

        $.ajax({
            type: 'GET',
            url: '/settlementlist',
            data: { styn: styn },
            success: function() {
                window.location.href = '/settlementlist?styn=' + styn;
            },
            error: function(error) {
                console.error('에러 발생:', error);
            }
        });
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

// 입력된 값에 따라 수수료와 정산금액을 업데이트
function updateTableCell(value) {
	// 화면 상의 값만 즉각적으로 업데이트
	var rate = parseFloat(value.replace('%', ''));
	$('.updateRate').text(rate + '%');
	$('.sellPrice').each(function() {
		var sellPrice = parseInt($(this).text().replace(/\D/g, ''));
		var settlementAmount = Math.round(sellPrice * (1 - rate / 100));
		$(this).closest('tr').find('.settlementAmount').text(settlementAmount.toLocaleString('ko-KR'));
	});
}

// 정산처리
function selectUpdateSettlement() {
    var selectedValues = [];
    var isRejected = false;

    $('.selectCheckbox').each(function() {
        var stseq = $(this).data('stseq');
        var row = $(this).closest('tr');
        var styn = row.find('.styn').text();

        if ($(this).is(':checked')) {
            if (styn === 'R') {
                alert('반려 처리된 정산건입니다.');
                isRejected = true;
                return false;
            }

            if (stseq) {
                var rate = parseFloat(row.find('.updateRate').text().replace('%', ''));
                var settlementAmount = parseInt(row.find('.settlementAmount').text().replace(/\D/g, ''));
                selectedValues.push({ stseq: stseq, rate: rate, settle_amount: settlementAmount });

                console.log('stseq=' + stseq);
            }
        }
    });

    if (!isRejected) {
        if (selectedValues.length === 0) {
            $('.selectCheckbox').each(function() {
                var stseq = $(this).data('stseq');
                var styn = $(this).closest('tr').find('.styn').text();

                if (styn === 'R') {
                    alert('반려 처리된 정산건이 포함되어 있습니다. 다시 선택해주세요.');
                    isRejected = true;
                    return false;
                } else if (styn === 'Y') {
		            alert('완료 처리된 정산건입니다.');
		            isRejected = true;
		            return false;
		        }

                if (stseq) {
                    var row = $(this).closest('tr');
                    var rate = parseFloat(row.find('.updateRate').text().replace('%', ''));
                    var settlementAmount = parseInt(row.find('.settlementAmount').text().replace(/\D/g, ''));

                    selectedValues.push({ stseq: stseq, rate: rate, settle_amount: settlementAmount });
                }
            });
        }
    }

    if (!isRejected) {
        var settlementForm = $('#settlementForm');
        selectedValues.forEach(function(value) {
            settlementForm.append('<input type="hidden" name="stseq" value="' + value.stseq + '">');
            settlementForm.append('<input type="hidden" name="rate" value="' + value.rate + '">');
            settlementForm.append('<input type="hidden" name="settle_amount" value="' + value.settle_amount + '">');
        });

        settlementForm.submit();
    }
}

// 반려 처리
function returnSettlement(stseq) {
	console.log("stseq=" + stseq);

	var data = {
		stseq: stseq
	};

	$.ajax({
		url: 'returnSettlement?stseq=' + stseq,
		method: "Post",
		contentType: 'applcation/json',
		data: JSON.stringify(data),
		success: function(response) {
			if (response) {
				alert("반려하였습니다.")
				location.reload();
			} else {
				console.log("반려 처리 중 오류가 발생했습니다.")
			}
		}

	});
}

// 반려 취소
function returnReverseSettlement(stseq) {
	console.log("stseq=" + stseq);

	var data = {
		stseq: stseq
	};

	$.ajax({
		url: 'returnReverSesettlement?stseq=' + stseq,
		method: "Post",
		contentType: 'applcation/json',
		data: JSON.stringify(data),
		success: function(response) {
			if (response) {
				alert("반려 취소하였습니다.")
				location.reload();
			} else {
				console.log("반려 취소 처리 중 오류가 발생했습니다.")
			}
		}

	});
}