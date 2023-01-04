$((async () => {
    await getCouponList('/couponBox/getCouponList?isActive=active');
})());

async function getCouponList(url) {
    const postResponse = await fetch(url);
    const coupons = await postResponse.json();
    let couponIds = [];
    for (const coupon of coupons.content) {
        couponIds.push(coupon.couponId)
    }
    for (const idx of couponIds) {
        await checked(idx);
    }

    async function checked(idx) {
        let response = await fetch('/couponBox/duplicateCheck?couponId=' + idx);
        return await response.json();
    }

    let couponList = $.each(coupons.content, async function (idx, coupon) {
        return coupon;
    });

    let list = '';
    if (couponList.length > 0) {
        for (const coupon of couponList) {
            let duplicateCheck = await checked(coupon.couponId);
            if (duplicateCheck === false) {
                if (coupon.couponDiscRate.toString().length > 2) {
                    let s = coupon.couponDiscRate.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                    coupon.couponDiscRate = s + '원 할인';
                } else if (coupon.couponDiscRate.toString().length <= 2) {
                    coupon.couponDiscRate += '% 할인';
                }
                list += `<div class="border p-lg-3 rounded m-1" style="flex: 0 0 32.33333%;  max-width: 32.33333%;">
                                <div>
                                    <div>
                                        <h6 class="card-text couponEndDate">${coupon.couponEndDate.substring(0, 16)} 까지</h6>
                                        <h5 class="card-text py-2 couponName">${coupon.couponName}</h5>
                                        <div class="row mb-2">
                                            <div class="col-6">
                                                <h6 class="couponAcceptType">${coupon.couponAcceptType}</h6>
                                            </div>
                                            <div class="text-center col-6">
                                                <h6 class="couponDiscRate text-danger">${coupon.couponDiscRate}</h6>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-column">
                                            <button class="btn btn-sm btn-primary getCouponBtn" id="${coupon.couponId}">쿠폰 받기</button>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
            }
        }
    } else {
        list += `<div>쿠폰이 없습니다.</div>`;
    }
    $('.couponContent').html(list);
}

$(document).ready(function () {
    $('.couponTab button').click(function () {
        let dataId = $(this).attr('data-id');
        $('.couponTab').children('button').removeClass('active');
        $(this).addClass('active');
        if (dataId === '1') {
            $('.couponContent').empty();
            (async () => {
                await getCouponList('/couponBox/getCouponList?isActive=active');
            })();
        }
        if (dataId === '2') {
            $('.couponContent').empty();
            (async () => {
                await broughtCouponList('/couponBox/broughtCouponList?isImminent=true&isUsed=false');
            })();
        }
        if (dataId === '3') {
            $('.couponContent').empty();
            (async () => {
                await broughtCouponList('/couponBox/broughtCouponList?isUsed=false');
            })();
        }
        if (dataId === '4') {
            $('.couponContent').empty();
            (async () => {
                await broughtCouponList('/couponBox/broughtCouponList');
            })();
        }
    })
})

$(document).on("click", ".getCouponBtn", function () {
    let couponId = $(this).attr('id');

    async function downloadCoupon(url = '', data = {}) {
        const response = await fetch(url, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow',
            referrerPolicy: 'no-referrer',
            body: JSON.stringify(data)
        });
        return response.json();
    }

    downloadCoupon('/couponBox/download', couponId)
        .then((data) => {
            if (data === true) {
                $(this).text('받기 완료');
                $(this).text('받기 완료').attr('disabled', true);
            } else {
                alert('이미 받은 쿠폰입니다.')
            }
        }).finally(() => {
        }
    );
});

async function broughtCouponList(url) {
    let response = await fetch(url);
    await response.json().then((data) => {
        $.each(data.content, function (idx, coupon) {
            if (coupon.couponDiscRate.toString().length > 2) {
                let s = coupon.couponDiscRate.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                coupon.couponDiscRate = s + '원 할인';
            } else if (coupon.couponDiscRate.toString().length <= 2) {
                coupon.couponDiscRate += '% 할인';
            }
            let button ;
            if (coupon.used === true) {
                button = `<button class="btn btn-sm btn-outline-primary productDirectBtn" id="${coupon.couponAcceptType}" disabled>사용 완료</button>`
            } else {
                button = `<button class="btn btn-sm btn-primary productDirectBtn" id="${coupon.couponAcceptType}">상품 둘러보기</button>`
            }
            const html = `<div class="border p-lg-3 rounded m-1" style="flex: 0 0 32.33333%;  max-width: 32.33333%;">
                                <div>
                                    <div>
                                        <h6 class="card-text couponEndDate">${coupon.expirationDate.substring(0, 16).replace('T', ' ')} 까지</h6>
                                        <h5 class="card-text py-2 couponName">${coupon.couponName}</h5>
                                        <div class="row mb-2">
                                            <div class="col-6">
                                                <h6 class="couponAcceptType">${coupon.couponAcceptType}</h6>
                                            </div>
                                            <div class="text-center col-6">
                                                <h6 class="couponDiscRate text-danger">${coupon.couponDiscRate}</h6>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-column">
                                        ${button}
                                        </div>
                                    </div>
                                </div>
                            </div>`
            $('.couponContent').append(html);
        })
        if (data.content.length === 0) {
            $('.couponContent').html(`<div>쿠폰이 없습니다.</div>`);
        }
    });
}

$(document).on('click', '.productDirectBtn', function () {
    let id = $(this).attr('id');
    switch (id) {
        case "전체상품":
            id = "all";
            break;
        case "사료":
            id = "food";
            break;
        case "간식":
            id = "snack";
            break;
        case "장난감":
            id = "toy";
            break;
        case "산책용품":
            id = "walking";
            break;
        case "패션/잡화":
            id = "fashion";
            break;
        case "유모차":
            id = "stroller";
            break;
    }
    location.href = '/product?category=' + id;
})
