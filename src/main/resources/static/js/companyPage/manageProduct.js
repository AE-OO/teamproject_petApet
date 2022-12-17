$(document).ready(function(){
    getProductList();

    //재고반영 버튼 클릭 시
    $("#detailStockBTN").click(function(){
        $.ajax({
            url: "/product/updateStock/" + $("#detailProductId").val(),
            method: "post",
            data : {productStock : $("#detailStock").val()},
            success: function(data){
                getProductList();
            }
        });
    });
    
    //판매상황반영 버튼 클릭 시
    $("#detailStatusBTN").click(function(){
        $.ajax({
            url: "/product/updateStatus/" + $("#detailProductId").val(),
            method: "post",
            data : {productStatus : $("#detailStatus").val()},
            success: function(data){
                getProductList();
            }
        });
    });

})

//회사가 등록한 상품 리스트 출력
function getProductList(){
    $.getJSON('/product/manageProduct', function(result){
        var list = '';
        if(result.length > 0){
            $.each(result, function(idx, product) {
                list += `<tr id="productData`+ idx +`" onclick="showProductDetailInfo(`+ product.productId + `,'` + product.productName + `',` + product.productStock + `,'` + product.productStatus + `')">
                                    <td class="pl-0" id="productId">`+ product.productId +`</td>
                                    <td><a th:href="#">` + product.productName + `</a></td>
                                    <td>` + product.productDiv + `</td>
                                    <td>` + product.productPrice + `</td>
                                    <td>` + product.productStock + `</td>
                                    <td>` + product.productStatus + `</td>
                                    <td>0</td>
                                    <td>` + product.productReport + `</td>
                                </tr>`;
            })
        }else{
            $("#noData").text("등록된 상품이 없습니다.");
        }

        $(".productData").html(list);
    })
}

//선택한 상품의 세부사항 표시
function showProductDetailInfo(productId, productName, productStock, productStatus){
    //숨겨놨던 세부사항 표시 부분을 보이게 함
    $("#productDetailInfo").css("display","");
    //선택한 상품의 PK 넘김(수정과 삭제를 위해서)
    $("#detailProductId").val(productId);
    //선택한 상품의 이름 표시
    $("#detailName").text("[" + productName + "] 상품 세부사항");
    //선택한 상품의 재고 표시
    $("#detailStock").val(productStock);
    //선택한 상품의 판매상황 표시
    $("#detailStatus").val(productStatus).attr("selected","selected");

    //오늘 날짜
    var date = new Date();

    var detailSalesPerMonth = {
        labels: [
            //오늘 날짜에서 6개월 전까지의 데이터를 표시함
            getFormatDate(date, 5),
            getFormatDate(date, 4),
            getFormatDate(date, 3),
            getFormatDate(date, 2),
            getFormatDate(date, 1),
            getFormatDate(date, 0)
        ],
        datasets: [{
            label: '# of Votes',
            data: [
                getDetailSalesPerMonth()[0].val,
                getDetailSalesPerMonth()[1].val,
                getDetailSalesPerMonth()[2].val,
                getDetailSalesPerMonth()[3].val,
                getDetailSalesPerMonth()[4].val,
                getDetailSalesPerMonth()[5].val
            ],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1,
            fill: false
        }]
    };

    var options = {
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        legend: {
            display: false
        },
        elements: {
            point: {
                radius: 0
            }
        }

    };

    // 사업자 마이페이지의 세부사항 월별 판매량 차트
    if ($("#detailSalesPerMonthChart").length) {
        var barChartCanvas = $("#detailSalesPerMonthChart").get(0).getContext("2d");
        var barChart = new Chart(barChartCanvas, {
            type: 'bar',
            data: detailSalesPerMonth,
            options: options
        });
    }
}

//월별 판매량 차트의 x축 날짜를 표시하기 위함
function getFormatDate(date , subNum){
    var year = date.getFullYear();
    var month = (1 + date.getMonth());

    if(month - subNum <= 0){
        year = year - 1;
        month = month - 1;
    }else{
        month = month - subNum;
    }

    month = month >= 10 ? month : '0' + month;
    return year + '-' + month;
}

//DB에서 세부사항 월별 판매량 데이터 가져오기
function getDetailSalesPerMonth(){
    var salesJson = {};
    var salesArray = [];

    $.ajax({
        url: "/buy/getDetailSalesPerMonth/" + $("#detailProductId").val(),
        type: "get",
        async: false,
        dataType: "json",
        success(data) {
            $.each(data, function(key, value){
                salesJson.id = key;
                salesJson.val = value;
                salesArray.push({...salesJson});
            });
        }
    })

    return salesArray;
}