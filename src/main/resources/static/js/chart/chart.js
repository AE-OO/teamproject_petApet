$(function() {
    /* ChartJS
     * -------
     * Data and config for chartjs
     */
    'use strict';

    var data = {
        labels: ["10대", "20대", "30대", "40대", "50대", "60대이상"],
        datasets: [{
            label: '# of Votes',
            data: [
                getAgeList()[1].val,
                getAgeList()[2].val,
                getAgeList()[3].val,
                getAgeList()[4].val,
                getAgeList()[5].val,
                getAgeList()[6].val+getAgeList()[7].val+getAgeList()[8].val+getAgeList()[9].val
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

    var multiLineData = {
        labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
        datasets: [{
            label: 'Dataset 1',
            data: [12, 19, 3, 5, 2, 3],
            borderColor: [
                '#587ce4'
            ],
            borderWidth: 2,
            fill: false
        },
            {
                label: 'Dataset 2',
                data: [5, 23, 7, 12, 42, 23],
                borderColor: [
                    '#ede190'
                ],
                borderWidth: 2,
                fill: false
            },
            {
                label: 'Dataset 3',
                data: [15, 10, 21, 32, 12, 33],
                borderColor: [
                    '#f44252'
                ],
                borderWidth: 2,
                fill: false
            }
        ]
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
    var doughnutPieData = {
        datasets: [{
            data: [getGenderList()[0].val,getGenderList()[1].val,getGenderList()[2].val],
            backgroundColor: [
                'rgba(54, 162, 235, 0.5)',
                'rgba(255, 99, 132, 0.5)',
                'rgba(255, 206, 86, 0.5)'
            ],
            borderColor: [
                'rgba(54, 162, 235, 1)',
                'rgba(255,99,132,1)',
                'rgba(255, 206, 86, 1)'
            ],
        }],
        // These labels appear in the legend and in the tooltips when hovering different arcs
        labels: [
            '남자',
            '여자',
            '응답안함'
        ]
    };

    var doughnutPieOptions = {
        responsive: true,
        animation: {
            animateScale: true,
            animateRotate: true
        }
    };
    var areaData = {
        labels: ["2013", "2014", "2015", "2016", "2017"],
        datasets: [{
            label: '# of Votes',
            data: [12, 19, 3, 5, 2, 3],
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
            fill: true, // 3: no fill
        }]
    };

    var areaOptions = {
        plugins: {
            filler: {
                propagate: true
            }
        }
    }

    var multiAreaData = {
        labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
        datasets: [{
            label: 'Facebook',
            data: [8, 11, 13, 15, 12, 13, 16, 15, 13, 19, 11, 14],
            borderColor: ['rgba(255, 99, 132, 0.5)'],
            backgroundColor: ['rgba(255, 99, 132, 0.5)'],
            borderWidth: 1,
            fill: true
        },
            {
                label: 'Twitter',
                data: [7, 17, 12, 16, 14, 18, 16, 12, 15, 11, 13, 9],
                borderColor: ['rgba(54, 162, 235, 0.5)'],
                backgroundColor: ['rgba(54, 162, 235, 0.5)'],
                borderWidth: 1,
                fill: true
            },
            {
                label: 'Linkedin',
                data: [6, 14, 16, 20, 12, 18, 15, 12, 17, 19, 15, 11],
                borderColor: ['rgba(255, 206, 86, 0.5)'],
                backgroundColor: ['rgba(255, 206, 86, 0.5)'],
                borderWidth: 1,
                fill: true
            }
        ]
    };

    var multiAreaOptions = {
        plugins: {
            filler: {
                propagate: true
            }
        },
        elements: {
            point: {
                radius: 0
            }
        },
        scales: {
            xAxes: [{
                gridLines: {
                    display: false
                }
            }],
            yAxes: [{
                gridLines: {
                    display: false
                }
            }]
        }
    }

    var scatterChartData = {
        datasets: [{
            label: 'First Dataset',
            data: [{
                x: -10,
                y: 0
            },
                {
                    x: 0,
                    y: 3
                },
                {
                    x: -25,
                    y: 5
                },
                {
                    x: 40,
                    y: 5
                }
            ],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)'
            ],
            borderWidth: 1
        },
            {
                label: 'Second Dataset',
                data: [{
                    x: 10,
                    y: 5
                },
                    {
                        x: 20,
                        y: -30
                    },
                    {
                        x: -25,
                        y: 15
                    },
                    {
                        x: -10,
                        y: 5
                    }
                ],
                backgroundColor: [
                    'rgba(54, 162, 235, 0.2)',
                ],
                borderColor: [
                    'rgba(54, 162, 235, 1)',
                ],
                borderWidth: 1
            }
        ]
    }

    var scatterChartOptions = {
        scales: {
            xAxes: [{
                type: 'linear',
                position: 'bottom'
            }]
        }
    }
    // 관리자 페이지의 나잇대별 통계 차트
    if ($("#barChart").length) {
        var barChartCanvas = $("#barChart").get(0).getContext("2d");
        // This will get the first returned node in the jQuery collection.
        var barChart = new Chart(barChartCanvas, {
            type: 'bar',
            data: data,
            options: options
        });
    }

    if ($("#lineChart").length) {
        var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
        var lineChart = new Chart(lineChartCanvas, {
            type: 'line',
            data: data,
            options: options
        });
    }

    if ($("#linechart-multi").length) {
        var multiLineCanvas = $("#linechart-multi").get(0).getContext("2d");
        var lineChart = new Chart(multiLineCanvas, {
            type: 'line',
            data: multiLineData,
            options: options
        });
    }

    if ($("#areachart-multi").length) {
        var multiAreaCanvas = $("#areachart-multi").get(0).getContext("2d");
        var multiAreaChart = new Chart(multiAreaCanvas, {
            type: 'line',
            data: multiAreaData,
            options: multiAreaOptions
        });
    }

    if ($("#doughnutChart").length) {
        var doughnutChartCanvas = $("#doughnutChart").get(0).getContext("2d");
        var doughnutChart = new Chart(doughnutChartCanvas, {
            type: 'doughnut',
            data: doughnutPieData,
            options: doughnutPieOptions
        });
    }

    if ($("#pieChart").length) {
        var pieChartCanvas = $("#pieChart").get(0).getContext("2d");
        var pieChart = new Chart(pieChartCanvas, {
            type: 'pie',
            data: doughnutPieData,
            options: doughnutPieOptions
        });
    }

    if ($("#areaChart").length) {
        var areaChartCanvas = $("#areaChart").get(0).getContext("2d");
        var areaChart = new Chart(areaChartCanvas, {
            type: 'line',
            data: areaData,
            options: areaOptions
        });
    }

    if ($("#scatterChart").length) {
        var scatterChartCanvas = $("#scatterChart").get(0).getContext("2d");
        var scatterChart = new Chart(scatterChartCanvas, {
            type: 'scatter',
            data: scatterChartData,
            options: scatterChartOptions
        });
    }

    if ($("#browserTrafficChart").length) {
        var doughnutChartCanvas = $("#browserTrafficChart").get(0).getContext("2d");
        var doughnutChart = new Chart(doughnutChartCanvas, {
            type: 'doughnut',
            data: browserTrafficData,
            options: doughnutPieOptions
        });
    }
});

//DB에서 성별데이터 가져오기
function getGenderList(){
    var genderJson = {};
    var genderArray = [];

    $.ajax({
        url: "/admin/getGenderList",
        type: "get",
        async: false,
        dataType: "json",
        success(data) {
            $.each(data, function(key, value){
                genderJson.id = key;
                genderJson.val = value;
                genderArray.push({...genderJson});
            });
        }
    })
    return genderArray;
}

//DB에서 나잇대 데이터 가져오기
function getAgeList(){
    var ageJson = {};
    var ageArray = [];

    $.ajax({
        url: "/admin/getAgeList",
        type: "get",
        async: false,
        dataType: "json",
        success(data){
            $.each(data, function(key, value){
                ageJson.id = key;
                ageJson.val = value;
                ageArray.push({...ageJson});
            });
        }
    })
    return ageArray;
}