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
            label: '# of Votes',
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
                getProductSales()[0].val,
                getProductSales()[1].val,
                getProductSales()[2].val,
                getProductSales()[3].val,
                getProductSales()[4].val
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
        // These labels appear in the legend and in the tooltips when hovering different arcs
        labels: [
            'test1',
            'test2',
            'test3',
            'test4',
            'test5'
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

});

//월별 판매량 차트의 x축 표시하기 위함
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

//DB에서 전체상품 월별 판매량 데이터 가져오기
function getTotalSalesPerMonth(){
    var salesJson = {};
    var salesArray = [];

    $.ajax({
        url: "/buy/getTotalSalesPerMonth", //buy 테이블에서 회사아이디 검색해서 월별 판매량 가져오기
        type: "get",
        async: false,
        dataType: "json",
        success(data){
            $.each(data, function(key, value){
                salesJson.id = key;
                salesJson.val = value;
                salesArray.push({...salesJson});
            });
        }
    })
    return salesArray;
}

//DB에서 상품별 판매량 데이터 가져오기
function getProductSales(){
    var salesJson = {};
    var salesArray = [];

    $.ajax({
        url: "/buy/getProductSales",
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
