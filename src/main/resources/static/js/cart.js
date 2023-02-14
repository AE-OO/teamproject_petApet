const card = $('.store_container--382-BRTlye');  // 상품 박스
const cards = $('#cards'); // 상품 박스 부모
const removeCard = $('.product-removal > button'); // 상품 박스 >  삭제 버튼
const checkbox = $("button[role='checkbox']");
const checkboxAll = $('button#check-all');
const checkboxAllTarget = 'btn_check--31ufZfaM8S';
const checkboxAllTargeted = 'btn_check--31ufZfaM8S checked--2A5fgfRoYJ';
const checkBoxEach = $('div.check--2EZEnyir5V > button');
const buy = $('div.link_area--14aJI6oflh>a');
const onBuy = "link_buy--KOU5pZhUVG";
const offBuy = "link_buy--KOU5pZhUVG disabled--1wqXh5SEyX";
const btnMinus = $('.number .minus_btn');
const btnPlus = $('.number .plus_btn');
const input = $('.number > input');

const icoCheck = $('.checked--2A5fgfRoYJ');

const selectedPrice = $('.num--37aOyGmdW1.dd'); // <- 작업해야함
const productPrice = $('.price--2Uv-07hf78'); // 상품 개별 단가
const totalProductPrice = $('.product-price>span'); // 상품 개별 총금액
const selectDeleteCards = $('.btn_delete--3P5eHI2eDa'); // 상품 삭제 선택

/* 상품 */
const productQuntity = $('em.quan > .num--37aOyGmdW1'); // 상품 주문 수량
const productTotalFn = $('em.totalFn > .num--37aOyGmdW1'); // 상품 총 금액

//fade time 값을 넣으면 지연 동작함. 일단 0으로 세팅
let fadeTime = 0;
$(document).ready(function (){
    totalFnAll();
    totalProductCount();
});

$(checkboxAll).click(function () {
    if (this.className == checkboxAllTargeted) {
        $(this).attr('class', checkboxAllTarget);
        $(checkbox).attr('class', checkboxAllTarget);
        $(checkbox).attr('aria-checked', false);
        $(buy).attr('class', offBuy);
    } else {
        $(this).attr('class', checkboxAllTargeted);
        $(checkbox).attr('class', checkboxAllTargeted);
        $(checkbox).attr('aria-checked', true);
        $(buy).attr('class', onBuy);
    }
    totalProductCount();
    totalFnAll();
});

$(checkBoxEach).click(function () {
    if (this.className == checkboxAllTargeted) {
        $(this).attr('class', checkboxAllTarget);
        $(this).parents().eq(11).children().find(buy).attr('class', offBuy);
        $(this).attr('aria-checked', false);

        $(selectDeleteCards).click(function () {
            $(this).parents().eq(4).children().find(cards).remove();
            $(this).parents().eq(4).children().find(input).val(0);
        });
    } else {
        $(this).attr('class', checkboxAllTargeted);
        $(this).attr('aria-checked', true);
        $(this).parents().eq(11).children().find(buy).attr('class', onBuy);
    }
    totalProductCount()
    totalFnAll();
})
function updateQuan(productId, count) {
    let vo = {};
    vo.product = productId
    vo.quantity = count
    fetch('/cart/add', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(vo),
    })
        .then(response => {
        })
        .then(data => {
        })
}
// 수량
$(btnMinus).click(function () {
    let productId = $(this).parents().eq(12).children(2).find($('.addBuy')).val();
    var $input = $(this).parent().find('input');
    var count = parseInt($input.val()) - 1;
    count = count < 1 ? 1 : count;
    $input.val(count);
    $input.change();
    updateQuan(productId,count);
});

$(btnPlus).click(function () {
    let productId = $(this).parents().eq(12).children(2).find($('.addBuy')).val();
    var $input = $(this).parent().find('input');
    let count = parseInt($input.val()) + 1;
    $input.val(count);
    $input.change();
    updateQuan(productId,count);
});


$(input).change(function () {
    updateQuantity(this);
    totalFnAll();
});


/* Update quantity */
function updateQuantity(quantityInput) {
    const productRow = $(quantityInput).parents().eq(13);
    let eachPrice = productRow.children().find(productPrice).text();
    let price = parseInt(eachPrice.replaceAll('원', '').replaceAll(',', ''));

    const quantity = $(quantityInput).val();
    const linePrice = (price * quantity).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');

    /* 선택상품금액 */
    productRow.children().find(selectedPrice).each(function () {
        $(this).fadeOut(fadeTime, function () {
            $(this).text(linePrice);
            totalFnAll();
            $(this).fadeIn(fadeTime);
        });
    });


    /* 주문금액 */
    productRow.children().find(totalProductPrice).each(function () {
        $(this).fadeOut(fadeTime, function () {
            $(this).text(linePrice);
            totalFnAll();
            $(this).fadeIn(fadeTime);
        });
    });

    /* 수량 */
    productRow.children().find(productQuntity).each(function () {
        $(this).fadeOut(fadeTime, function () {
            $(this).text(quantity);
            $(this).fadeIn(fadeTime);
        });
    });

    productRow.children().find(productTotalFn).each(function () {
        $(this).fadeOut(fadeTime, function () {
            $(this).text(linePrice);
            $(this).fadeIn(fadeTime);
        });
    });
}

// 선택 삭제
$(removeCard).on("click", card, function (e) {
    const cartId = $(this).val();
    let deleteOne = {"cartId": cartId};
    $.ajax({
        url: "/cart/removeOne",
        type: "POST",
        data: JSON.stringify(deleteOne),
        dataType: "text",
        contentType: "application/json",
        charset: "UTF-8",
        success: function (data) {
            alert("삭제 되었음!! ");
            location.replace("/cart");
        },
        error: function (jqXHR, status, errorThrown) {
            alert("에러");
        }
    });

    $(e.target).closest(card).remove();
});

// 전체 삭제
$(selectDeleteCards).click(function () {
    const memberId = $('#memberName').val();
    let deleteAll = {"memberId": memberId};
    $.ajax({
        url: "/cart/removeAll",
        type: "POST",
        data: JSON.stringify(deleteAll),
        dataType: "text",
        contentType: "application/json",
        charset: "UTF-8",
        success: function (data) {
            alert("전체 삭제 되었음!! ")
            location.replace("/cart");
        },
        error: function (jqXHR, status, errorThrown) {
            alert("에러");
        }
    });

    $(this).parents().eq(4).children().find(cards).remove();
});

function totalFnAll() {
    let subtotal = 0

    $(icoCheck).parents().find(totalProductPrice).each(function () {
        let checked = $(this).parents().eq(5).children(5).children().children().children().children().eq(0).attr('aria-checked');
        if (checked === 'true') {
            subtotal += parseInt($(this).text().replaceAll(',', ''));
        }
    });

    $('.num--2CYvhIm-m6').fadeOut(fadeTime, function () {
        let replaceTotalPrice = subtotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).text(replaceTotalPrice);
        $('.num--2CYvhIm-m6').fadeIn(fadeTime);
    });
}

$('.link_buy--jXvxZ8Agr-').click(function () {
    let buyArray = [];
    $('.check--2EZEnyir5V').children(0).each(function () {
        if ($(this).attr('aria-checked') === 'true') {
            let cartId = $(this).parents().eq(1).children().find($('.remove-product')).val();
            buyArray.push(cartId);
        }
    })
    location.href = '/cart/checkout?carts=' + encodeURIComponent(JSON.stringify(buyArray));
})
$('.link_buy--KOU5pZhUVG').click(function () {
    let buyArray = [];
    let cartId = $(this).attr('data-id');
    buyArray.push(cartId);
    location.href = '/cart/checkout?carts=' + encodeURIComponent(JSON.stringify(buyArray));
})

function totalProductCount() {
    let count = 0;
    checkBoxEach.each(function () {
        if ($(this).attr('aria-checked') === 'true') {
            count += 1;
        }
    });
    $('.badge--ltxr8Ih722').text(count);
}