
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

const selectedPrice =$('.num--37aOyGmdW1.dd'); // <- 작업해야함
const productPrice = $('.price--2Uv-07hf78'); // 상품 개별 단가
const totalProductPrice = $('.product-price>span'); // 상품 개별 총금액
const selectDeleteCards = $('.btn_delete--3P5eHI2eDa'); // 상품 삭제 선택
/* 상품 */
const productQuntity = $('em.quan > .num--37aOyGmdW1'); // 상품 주문 수량
const productTotalFn = $('em.totalFn > .num--37aOyGmdW1'); // 상품 총 금액
const fadeTime = 300;


$(document).ready(function() {
    // function cal_fn(){
    //     const quantity = $(card).children().find(productQuntity).get().val();
    //     const price = parseInt($(card).children().find(productPrice).get.text());
    //     const totalFn = $(card).children().find(productTotalFn);
    //     const number = quantity * price;
    //     const dd = toString(number);
    //     console.log("cal_fn >>>>>", dd);
    //     return totalFn.text(dd);
    // }


    // 전체 선택
    $(checkboxAll).click(function () {
        if (this.className == checkboxAllTargeted){
            $(this).attr('class', checkboxAllTarget);
            $(checkbox).attr('class', checkboxAllTarget);
            $(buy).attr('class', offBuy);
        } else{
            $(this).attr('class', checkboxAllTargeted);
            $(checkbox).attr('class', checkboxAllTargeted);
            $(buy).attr('class', onBuy);
        }
    });

    // 개별 선택
    $(checkBoxEach).click(function(){
        if (this.className == checkboxAllTargeted){
            $(this).attr('class', checkboxAllTarget);
            $(this).parents().eq(11).children().find(buy).attr('class', offBuy);
            $(this).parents().eq(11).children().find(input).val(0);
            console.log("val", $(input).val());

            // 전체 삭제
            $(selectDeleteCards).click(function(){
                $(this).parents().eq(4).children().find(cards).remove();
                $(this).parents().eq(4).children().find(input).val(0);
                console.log("val", $(input).val());
            });
        } else{
            $(this).attr('class', checkboxAllTargeted);
            $(this).parents().eq(11).children().find(buy).attr('class', onBuy);
        }
    })

    // 수량
    $(btnMinus).click(function () {
        var $input = $(this).parent().find('input');
        var count = parseInt($input.val()) - 1;
        count = count < 1 ? 1 : count;
        $input.val(count);
        $input.change();
    });

    $(btnPlus).click(function () {
        var $input = $(this).parent().find('input');
        $input.val(parseInt($input.val()) + 1);
        $input.change();
    });


    $(input).change( function() {
        updateQuantity(this);
        // totalFnAll();
        console.log("click:!", $(this).val());

    });


    /* Update quantity */
    function updateQuantity(quantityInput)
    {
        const productRow = $(quantityInput).parents().eq(13);
        const price = parseInt(productRow.children().find(productPrice).text());
        const quantity = $(quantityInput).val();
        const linePrice = price * quantity;
        //
        console.log('가격', price);
        console.log('수량', quantity);
        console.log('총가격', linePrice)

        // totalFnAll();

        /* 선택상품금액 */
       productRow.children().find(selectedPrice).each(function () {
            // $(this).text(linePrice);
            $(this).fadeOut(fadeTime, function() {
                $(this).text(linePrice);
                totalFnAll();
                $(this).fadeIn(fadeTime);
            });
        });


        /* 주문금액 */
        productRow.children().find(totalProductPrice).each(function () {
            // $(this).text(linePrice);
            $(this).fadeOut(fadeTime, function() {
                $(this).text(linePrice);
                totalFnAll();
                $(this).fadeIn(fadeTime);
            });
        });

        /* 수량 */
        productRow.children().find(productQuntity).each(function () {
            console.log('vv',this);
            // $(this).text(quantity);
            // totalFnAll();
            $(this).fadeOut(fadeTime, function() {
                $(this).text(quantity);
                // recalculateCart();
                $(this).fadeIn(fadeTime);
            });
        });

        productRow.children().find(productTotalFn).each(function () {
            console.log('vv',this);
            // $(this).text(linePrice);
            // totalFnAll();
            $(this).fadeOut(fadeTime, function() {
                $(this).text(linePrice);
                // recalculateCart();
                $(this).fadeIn(fadeTime);
            });
        });
    }

    /* Set rates + misc */
    var fadeTime = 300;


    /* Recalculate cart */
    function totalFnAll()
    {
        let subtotal = 0

        $(card).each(function () {
            subtotal += parseInt($(this).children().find(totalProductPrice).text());
        });
        console.log('>>subtotal:type:',typeof subtotal);
        console.log('>>subtotal',subtotal);

        $('.num--2CYvhIm-m6').fadeOut(fadeTime, function() {
            $('#cart-total').text(subtotal);
            // $('#cart-total').html(subtotal);
            // if(subtotal == 0){
            //     $(card).fadeOut(fadeTime);
            // }else{
            //     $(card).fadeIn(fadeTime);
            // }
            $('.num--2CYvhIm-m6').fadeIn(fadeTime);
        });
    }

    // 선택 삭제
    $(removeCard).on("click", card, function(e){
        $(e.target).closest(card).remove();
    });

    // 전체 삭제
    $(selectDeleteCards).click(function(){
        $(this).parents().eq(4).children().find(cards).remove();
    });
});

