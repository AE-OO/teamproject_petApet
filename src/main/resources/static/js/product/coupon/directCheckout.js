let urlSearchParams = new URLSearchParams(location.search);

let productId = urlSearchParams.get('productId');
let quantity = urlSearchParams.get('quantity');
productIdList = [productId];
getAvailableCoupons('/checkout/getAvailableCoupons', productIdList).then((coupons) => {
    let couponList = ``;
    if (coupons.length === 0) {
        couponList = `<div><h6 class="text-center">사용 가능한 쿠폰이 없습니다</h6></div>`;
        $('.coupons').html(couponList);
    }
    $.each(coupons, function (idx, coupon) {
        let coupons = `        <li class="discount-coupon">
                                        <input type="radio" id="${coupon.couponId}" name="discountCoupons">
                                        <label class="explain enabled" for="${coupon.couponId}">
                                            <strong class="price">
                                                <span class="couponDiscRate" data-price="${coupon.couponDiscRate}">${coupon.couponDiscRate}</span><span class="unit">원</span>
                                            </strong>
                                            <strong class="discount blind">
                                                <span class="value" data-price="${coupon.couponDiscRate}">${coupon.couponDiscRate}</span><span class="unit">원</span>
                                            </strong>
                                            <strong class="couponTitle" style="color: #3a863a">${coupon.couponName}</strong>
                                            <em class="condition">
                                                <span class="acceptType">${coupon.couponAcceptType}</span>
                                                <span class="required">${coupon.couponAcceptPrice}원 이상 구매 시</span>
                                            </em>
                                        </label>
                                    </li>`

        couponList += coupons;
    })
    $('.coupons').html(couponList);

    let $couponDiscRate = $('span[class=couponDiscRate]');
    let $required = $('span[class=required]');

    priceToReplace($couponDiscRate, $required);

    let totalPrice = $('.num--2CYvhIm-m6').text().replaceAll(',', '');

    $('input:radio[name=discountCoupons]').change(function () {
        let amount = $(this).next().children($('strong[class=blind]')).children().eq(0).text();
        let totalPriceResult = totalPrice - amount.replaceAll(',', '');
        $('.num--2CYvhIm-m6').text(totalPriceResult.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
        $('#totalPayPriceDisp').text(totalPriceResult.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
        $('#couponDisp').text('-' + amount);
        $(this).attr('id');
    });


    $('button[class=link_buy--jXvxZ8Agr-]').click(function () {
        let couponId = $('input:radio[name=discountCoupons]:checked').attr('id');
        let param = {
            productId: productId,
            quantity: quantity,
            couponId: couponId
        };
        requestPayment("/direct/request/checkout", param)
            .then((data) => {

                var IMP = window.IMP; // 생략가능
                IMP.init(data.importId); // <-- 본인 가맹점 식별코드 삽입

                let uid = "order_" + new Date().getTime(); // <- 주문번호 생성

                // 결제api에 전달할 파라미터값
                let kakaoApiParam = {
                    pg: 'kakaopay',
                    pay_method: 'card', //생략 가능
                    merchant_uid: uid,
                    name: data.productName,
                    amount: data.totalPrice,
                    buyer_name: data.memberName,
                    buyer_tel: data.memberPhoneNum,
                    buyerEmail: data.memberEmail,
                    buyAddress: data.memberAddress,
                    buyQuantity: data.totalQuantity,
                    buyMember: data.memberId,
                };
                // DB에 전달할 파라미터
                let saveBuyParam = {
                    "uid": uid,
                    "buyAddress": data.memberAddress,
                    "buyQuantity": data.totalQuantity,
                    "couponId": data.couponId,
                    "buyProducts": data.productIds,
                    "buyerEmail": data.memberEmail,
                    "buyCartIds": data.cartIds,
                    "totalPrice": data.totalPrice,
                }

                requestPay();

                function requestPay() {
                    IMP.request_pay(kakaoApiParam, function (rsp) { // callback
                        if (rsp.success) { // 결제 성공 시: 결제 승인 또는 가상계좌 발급에 성공한 경우
                            saveBuy('/direct/checkout/success', saveBuyParam).then((data) => {
                                console.log(data)
                                window.location.replace("/buy")
                            });
                        } else {
                            alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
                        }
                    });
                }


            })
    })
})

async function saveBuy(url = '', data = {}) {
    const response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
        body: JSON.stringify(data),
    });
    return response.text();
}

async function getAvailableCoupons(url = '', data = {}) {
    const response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
        body: JSON.stringify(data),
    });
    return response.json();
}

async function requestPayment(url = '', data = {}) {
    const response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json',
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
        body: JSON.stringify(data),
    });
    return response.json();
}

function priceToReplace(text, text2) {
    $.each(text, function () {
        let replace = $(this).text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).text(replace)
    })
    $.each(text2, function () {
        let replace = $(this).text().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).text(replace)
    })
}

$('.select-coupon-toggle').click(function () {
    if ($(this).attr('aria-checked') === 'false') {
        $(this).next().removeClass('blind');
        $(this).attr('aria-checked', 'true')
    } else {
        $(this).next().addClass('blind');
        $(this).attr('aria-checked', 'false')
    }
})