$(function() {
    /* ChartJS
     * -------
     * Data and config for chartjs
     */
    'use strict';
    var date = new Date();

    var totalSalesPerMonth = {
        labels: [
            getFormatDate(date, 5),
            getFormatDate(date, 4),
            getFormatDate(date, 3),
            getFormatDate(date, 2),
            getFormatDate(date, 1),
            getFormatDate(date, 0)
        ],
        datasets: [{
            label: '월판매량 (개) ',
            data: [
                getTotalSalesPerMonth()[0].val,
                getTotalSalesPerMonth()[1].val,
                getTotalSalesPerMonth()[2].val,
                getTotalSalesPerMonth()[3].val,
                getTotalSalesPerMonth()[4].val,
                getTotalSalesPerMonth()[5].val
            ],
            backgroundColor: [
                'rgba(75, 192, 192, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(75, 192, 192, 0.2)'
            ],
            borderColor: [
                'rgba(75, 192, 192, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(75, 192, 192, 1)'
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

    // 사업자 마이페이지의 전체상품 월별 판매량 차트
    if ($("#totalSalesPerMonthChart").length) {
        var barChartCanvas = $("#totalSalesPerMonthChart").get(0).getContext("2d");
        var barChart = new Chart(barChartCanvas, {
            type: 'bar',
            data: totalSalesPerMonth,
            options: options
        });
    }

    var productSales = {
        datasets: [{
            data: [
                getProductSales()[0].val[1],
                getProductSales()[1].val[1],
                getProductSales()[2].val[1],
                getProductSales()[3].val[1],
                getProductSales()[4].val[1]
            ],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)'
            ],
        }],
        labels: [
            getProductSales()[0].val[0],
            getProductSales()[1].val[0],
            getProductSales()[2].val[0],
            getProductSales()[3].val[0],
            getProductSales()[4].val[0]
        ]
    };

    var doughnutPieOptions = {
        responsive: true,
        animation: {
            animateScale: true,
            animateRotate: true
        }
    };

    //사업자 마이페이지의 상품별 판매량 차트
    if ($("#productSalesChart").length) {
        var pieChartCanvas = $("#productSalesChart").get(0).getContext("2d");
        var doughnutChart = new Chart(pieChartCanvas, {
            type: 'pie',
            data: productSales,
            options: doughnutPieOptions
        });
    }
    
    //테이블의 행 클릭했을 때 나오는 세부사항 안의 그래프
    $("#productTable").on("click", "tr", function(){
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
                label: '월판매량 (개) ',
                data: [
                    getDetailSalesPerMonth()[0].val,
                    getDetailSalesPerMonth()[1].val,
                    getDetailSalesPerMonth()[2].val,
                    getDetailSalesPerMonth()[3].val,
                    getDetailSalesPerMonth()[4].val,
                    getDetailSalesPerMonth()[5].val
                ],
                backgroundColor: [
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(75, 192, 192, 0.2)',
                    'rgba(75, 192, 192, 0.2)'
                ],
                borderColor: [
                    'rgba(75, 192, 192, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(75, 192, 192, 1)',
                    'rgba(75, 192, 192, 1)'
                ],
                borderWidth: 1,
                fill: false
            }]
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
    })
});

//월별 판매량 차트의 x축 표시하기 위함
function getFormatDate(date , subNum){
    var year = date.getFullYear();
    var month = (1 + date.getMonth());

    if(month - subNum <= 0){
        year = year - 1;
        month = month - subNum + 12;
    }else{
        month = month - subNum;
    }

    month = month >= 10 ? month : '0' + month;
    return year + '-' + month;
}

//DB에서 전체상품 월별 판매량 데이터 가져오기
function getTotalSalesPerMonth(){
    var salesJson = {};
    var salesArray = [];
    console.log("실행");

    $.ajax({
        url: "/buy/getTotalSalesVolPerMonth", //buy 테이블에서 회사아이디 검색해서 월별 판매량 가져오기
        type: "get",
        async: false,
        dataType: "json",
        success(data){
            console.log("성공");
            $.each(data, function(key, value){
                salesJson.id = key;
                salesJson.val = value;
                salesArray.push({...salesJson});
            });
        },
        error(){
            alert("데이터를 가져올 수 없습니다.");
        }
    })
    return salesArray;
}

//DB에서 상품별 판매량 데이터 가져오기
function getProductSales(){
    var salesJson = {};
    var salesArray = [];

    $.ajax({
        url: "/buy/getSalesVolbyProduct",
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

//DB에서 세부사항 월별 판매량 데이터 가져오기
function getDetailSalesPerMonth(){
    var salesJson = {};
    var salesArray = [];

    $.ajax({
        url: "/buy/getSalesVolbyProductPerMonth/" + $("#detailProductId").val(),
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
