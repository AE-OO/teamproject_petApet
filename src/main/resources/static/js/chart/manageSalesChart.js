$(function() {
    /* ChartJS
     * -------
     * Data and config for chartjs
     */
    'use strict';
    var date = new Date();

    var data = {
        labels: [
            //오늘 날짜에서 1년 전까지의 데이터를 표시함
            getFormatDate(date, 11),
            getFormatDate(date, 10),
            getFormatDate(date, 9),
            getFormatDate(date, 8),
            getFormatDate(date, 7),
            getFormatDate(date, 6),
            getFormatDate(date, 5),
            getFormatDate(date, 4),
            getFormatDate(date, 3),
            getFormatDate(date, 2),
            getFormatDate(date, 1),
            getFormatDate(date, 0)
        ],
        datasets: [{
            label: '매출 (원) ',
            data: [
                getMonthlySales()[11].val,
                getMonthlySales()[10].val,
                getMonthlySales()[9].val,
                getMonthlySales()[8].val,
                getMonthlySales()[7].val,
                getMonthlySales()[6].val,
                getMonthlySales()[5].val,
                getMonthlySales()[4].val,
                getMonthlySales()[3].val,
                getMonthlySales()[2].val,
                getMonthlySales()[1].val,
                getMonthlySales()[0].val,
            ],
            borderColor: [
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
                'rgba(249, 24, 24, 0.8)',
            ],
            borderWidth: 4,
            fill: false,
            lineTension: 0
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
        },
        hover: {
            mode: 'nearest',
            intersect: true
        },
        tooltips: {
            mode: 'index',
            intersect: false,
        }

    };

    if ($("#monthlySalesChart").length) {
        var lineChartCanvas = $("#monthlySalesChart").get(0).getContext("2d");
        var lineChart = new Chart(lineChartCanvas, {
            type: 'line',
            data: data,
            options: options
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

//DB에서 월별 매출 데이터 가져오기
function getMonthlySales(){
    var salesJson = {};
    var salesArray = [];

    $.ajax({
        url: "/buy/getMonthlySales",
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