let urlSearchParams = new URLSearchParams(location.search);

$("input[name=couponType]:radio").change(function () {
    let couponType = this.value;
    let $inputDiscRate = $('input[id=couponDiscRate]');
    let $validMsg = $('#validMsg');
    $validMsg.empty();
    $inputDiscRate.val('');
    if (couponType === 'regularDisc') {
        $("#couponDiscRate").on("propertychange change paste input", function () {
            let rate = $inputDiscRate.val();
            const regEx = /^\d{4,5}$/;
            let boolean = regEx.test(rate);
            if (boolean === false) {
                $validMsg.text('천~만 단위의 금액을 입력하세요');
            } else {
                $validMsg.empty();
            }
        })
    }
    if (couponType === 'percentDisc') {
        $("#couponDiscRate").on("propertychange change paste input", function () {
            $('.field-error[id=valid_couponDiscRate]').empty();
            let rate = $inputDiscRate.val();
            const regEx = /^\d{1,2}$/;
            let boolean = regEx.test(rate);
            if (boolean === false || 5 > rate || 50 < rate) {
                $validMsg.text('5 - 50까지 허용됩니다.');
            } else {
                $validMsg.empty();
            }
        })
    }
});
// $(requestCouponList());
// async function requestCouponList() {
// fetch('/admin/coupon/list')
//     .then((response) => response.json())
//     .then((data) => console.log(data));
// }

$('#updateCouponBtn').click(function () {
    $('.field-error').empty();
    let couponId = $(this).attr('value');
    let formData = new FormData(document.getElementById('updateCoupon'));
    formData.set('couponId', couponId);
    fetch('/admin/coupon/update', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(Object.fromEntries(formData)),
    })
        .then(response => {
            if (response.status === 303) {
                location.href = '?' + urlSearchParams.toString();
            }
            return response.json();
        })
        .then(data => {
            $.each(data, function (key, value) {
                $('.field-error[id=' + key + ']').text(value);
            })
        })
})

$('.modalBtn').click(function () {
    let couponEndDate = $(this).parents('div').parents('.pricing-card-body').children('.couponEndDate').text()
    let couponName = $(this).parents('div').parents('.pricing-card-body').children('.couponName').text()
    let couponAcceptType = $(this).parents('div').parents('.pricing-card-body').children().children().children('.couponAcceptType').attr('value')
    let couponDiscRate = $(this).parents('div').parents('.pricing-card-body').children().children().children('.couponDiscRate').text()
    let couponActive = $(this).parents('div').parents('.pricing-card-body').children().children().children('.couponActive').attr('value')
    let couponStock = $(this).parents('div').parents('.pricing-card-body').children().children().children('.couponStock').text()

    if (couponDiscRate.includes('%')) {
        let discLastIndexOf = couponDiscRate.lastIndexOf('%');
        let substring = couponDiscRate.substring(0, discLastIndexOf);
        $("input[name=couponType][value=percentDisc]").prop('checked', true)
        $('#couponDiscRate').val(substring)
    } else {
        let discLastIndexOf = couponDiscRate.lastIndexOf('원');
        let substring = couponDiscRate.substring(0, discLastIndexOf);
        let replaceAll = substring.replaceAll(',', '');
        $("input[name=couponType][value=regularDisc]").prop('checked', true)
        $('#couponDiscRate').val(replaceAll)

    }
    let StockLastIndexOf = couponStock.lastIndexOf('개');
    let StockSubstr = couponStock.substring(0, StockLastIndexOf);

    let EndDateSubstr = couponEndDate.substring(0, 10);

    $('#couponName').val(couponName)
    $('#couponStock').val(StockSubstr)
    $('#couponEndDate').val(EndDateSubstr);
    $('#couponAcceptType').val(couponAcceptType)
    $('#couponActive').val(couponActive)
    let couponId = $(this).attr('id');
    $('#updateCouponBtn').attr('value', couponId);
})

$(function () {
    const pageNumber = urlSearchParams.get('page');
    if (pageNumber === null) {
        $("ul[id =paginationBox] li[value='1']").attr("class", 'page-item active')
    }
    $("ul[id = 'paginationBox'] li[value=" + pageNumber + ']').attr("class", 'page-item active')
})
$('.acceptType li a').click(function () {
    let acceptType = this.id;
    urlSearchParams.set('acceptType', acceptType);
    urlSearchParams.set('page', '1');
    location.href = '?' + urlSearchParams.toString();
})
$('.page-link').click(function () {
    let page = $(this).parent().attr('value')
    urlSearchParams.set('page', page);
    location.href = '?' + urlSearchParams.toString();
})
$('.activeParam li button').click(function () {
    let isActive = $(this).val();
    urlSearchParams.set('isActive', isActive);
    urlSearchParams.set('page', '1');
    location.href = '?' + urlSearchParams.toString();
})
$('#sortByRecent').click(function () {
    let hasParam = urlSearchParams.has('sort');
    let sort = urlSearchParams.get('sort');
    if (!hasParam || sort !== 'couponIdDesc') {
        urlSearchParams.set('sort', 'couponIdDesc');
        urlSearchParams.set('page', '1');
        location.href = '?' + urlSearchParams.toString();
    } else if (hasParam) {
        if (sort === 'couponIdDesc') {
            urlSearchParams.set('sort', 'couponIdAsc');
            urlSearchParams.set('page', '1');
            location.href = '?' + urlSearchParams.toString();
        }
    }
})
$('#sortByExpiration').click(function () {
    urlSearchParams.set('sort', 'endDateDesc');
    urlSearchParams.set('page', '1');
    location.href = '?' + urlSearchParams.toString();
})

$('#sortByStock').click(function () {
    let hasParam = urlSearchParams.has('sort');
    let sort = urlSearchParams.get('sort');
    if (!hasParam || sort !== 'stockDesc') {
        urlSearchParams.set('sort', 'stockDesc');
        urlSearchParams.set('page', '1');
        location.href = '?' + urlSearchParams.toString();
    } else if (hasParam) {
        if (sort === 'stockDesc') {
            urlSearchParams.set('sort', 'stockAsc');
            urlSearchParams.set('page', '1');
            location.href = '?' + urlSearchParams.toString();
        }
    }
})

$(function () {
    let byRecent = $('#sortByRecent');
    let byExpiration = $('#sortByExpiration');
    let byStock = $('#sortByStock');
    let sort = urlSearchParams.get('sort');

    if (sort === 'endDateDesc') {
        byExpiration.attr('class', 'btn btn-primary')
    } else {
        byExpiration.attr('class', 'btn btn-outline-primary')
    }

    if (sort === 'stockDesc') {
        byStock.html('재고순↓')
        byStock.attr('class', 'btn btn-primary')
    } else if (sort === 'stockAsc') {
        byStock.html('재고순↑')
        byStock.attr('class', 'btn btn-primary')
    } else {
        byStock.html('재고순')
        byStock.attr('class', 'btn btn-outline-primary')
    }

    if (sort === 'couponIdDesc') {
        byRecent.attr('class', 'btn btn-primary')
        byRecent.html('과거순');
    } else if (sort === 'couponIdAsc') {
        byRecent.attr('class', 'btn btn-primary')
        byRecent.html('최신순');
    } else {
        byRecent.attr('class', 'btn btn-outline-primary')
    }
})


$(isActive);
$(isAcceptType);
$(isSearchContent);

function isActive() {
    if (urlSearchParams.has('isActive')) {
        let active = urlSearchParams.get('isActive');
        if (active === 'any') {
            return null;
        }
        let string = (active === 'active') ? '활성화' : '비활성화';
        const append = `<button class="btn btn-sm" id="isActiveBtn">${string}<i class="bi bi-x-square ms-1"></button>`;
        $('.appliedSearch').append(append);
    }
    $('#isActiveBtn').click(function () {
        urlSearchParams.delete('isActive');
        urlSearchParams.set('page', '1');
        location.href = '?' + urlSearchParams.toString();
    })
}

function isAcceptType() {
    if (urlSearchParams.has('acceptType')) {
        let active = urlSearchParams.get('acceptType');
        let string;
        if (active === 'total') {
            return null;
        }
        if (active === 'all') {
            string = '전체상품'
        }
        if (active === 'food') {
            string = '사료'
        }
        if (active === 'snack') {
            string = '간식'
        }
        if (active === 'walking') {
            string = '산책용품'
        }
        if (active === 'toy') {
            string = '장난감'
        }
        if (active === 'fashion') {
            string = '패션/잡화'
        }
        if (active === 'stroller') {
            string = '유모차'
        }
        const append = `<button class="btn btn-sm" id="isAcceptTypeBtn">${string}<i class="bi bi-x-square ms-1"></button>`;
        $('.appliedSearch').append(append);
    }
    $('#isAcceptTypeBtn').click(function () {
        urlSearchParams.delete('acceptType');
        urlSearchParams.set('page', '1');
        location.href = '?' + urlSearchParams.toString();
    })
}

function isSearchContent() {
    if (urlSearchParams.has('searchContent')) {
        let active = urlSearchParams.get('searchContent');
        const append = `<button class="btn btn-sm" id="isSearchContentBtn">'${active}'검색 결과<i class="bi bi-x-square ms-1"></button>`;
        $('.appliedSearch').append(append);
    }
    $('#isSearchContentBtn').click(function () {
        urlSearchParams.delete('searchContent');
        urlSearchParams.set('page', '1');
        location.href = '?' + urlSearchParams.toString();
    })
}

$('#searchBtn').click(function () {
    let searchContent = $(this).prev().val();
    urlSearchParams.set('searchContent', searchContent);
    urlSearchParams.set('page', '1');
    location.href = '?' + urlSearchParams.toString();
})
