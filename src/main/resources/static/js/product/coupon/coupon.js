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
    let couponId = $('.modalBtn').attr('id');
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
                location.href = '/admin/coupon'
            }
            return response.json();
        })
        .then(data => {
            console.log(data.status)
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

})
let urlSearchParams = new URLSearchParams(location.search);
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
$('#byRecent').click(function () {
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
$('#byExpiration').click(function () {
    urlSearchParams.set('sort', 'endDateDesc');
    urlSearchParams.set('page', '1');
    location.href = '?' + urlSearchParams.toString();
})

$(function () {
    let byRecent = $('#byRecent');
    let sort = urlSearchParams.get('sort');
    if (sort === 'couponIdDesc') {
        byRecent.html('과거순');
    } else {
        byRecent.html('최신순');
    }
})


$(isActiveBtn);
$(isAcceptTypeBtn);

function isActiveBtn() {
    if (urlSearchParams.has('isActive')) {
        let active = urlSearchParams.get('isActive');
        if (active === 'any') {
            return null;
        }
        let string = (active === 'active') ? '활성화' : '비활성화';
        const append = `<button class="btn btn-sm" id="isActive">${string}<i class="bi bi-x-square ms-1"></button>`;
        $('.appliedSearch').append(append);
    }
    $('#isActive').click(function () {
        urlSearchParams.delete('isActive');
        urlSearchParams.set('page', '1');
        location.href = '?' + urlSearchParams.toString();
    })
}

function isAcceptTypeBtn() {
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
        const append = `<button class="btn btn-sm" id="isAcceptType">${string}<i class="bi bi-x-square ms-1"></button>`;
        $('.appliedSearch').append(append);
    }
    $('#isAcceptType').click(function () {
        urlSearchParams.delete('acceptType');
        urlSearchParams.set('page', '1');
        location.href = '?' + urlSearchParams.toString();
    })
}
