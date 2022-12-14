$(document).ready(function(){
    getSalesList();
})

//회사 마이페이지에 판매 내역 리스트 출력
function getSalesList(){
    $.getJSON('/buy/manageSales', function(result){
        var list = '';
        if(result.length > 0){
            $.each(result, function(idx, sales) {
                list += `<tr id="salesData`+ idx +`">
                                    <td class="pl-0" id="inquiryId">${idx + 1}</td>
                                    <td class="merchantUID">${sales.merchantUID}</td>
                                    <td>${sales.productName}</td>
                                    <td>${sales.memberId}</td>
                                    <td>${sales.buyDate}</td>
                                    <td>${sales.buyDetail}</td>
                                    <td class="totalPrice">${sales.totalPrice}</td>
                                    <td><button onclick="cancelPay()" type="button" class="btn btn-sm btn-secondary">결제취소</button></td>
                                    <td>
                                    </td> </tr>`;
            })
        }else{
            $("#noData").text("판매 내역이 없습니다.");
        }

        $(".salesData").html(list);
    })
}

var merchantUID = $('.merchantUID').text();
var totalPrice = $('.totalPrice').val();

var param = {
    "merchant_uid": merchantUID,
    "cancel_request_amount": totalPrice
}

//결제 환불
function cancelPay(){
    console.log("click!");
    jQuery.ajax({
        "url": "/",
        "type": "POST",
        "contentType": "application/json",
        "data": JSON.stringify({param}),
        "dataType": "json"
    });
}