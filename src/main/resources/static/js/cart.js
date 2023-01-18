
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

const selectedPrice =$('.num--37aOyGmdW1.dd'); // <- 작업해야함
const productPrice = $('.price--2Uv-07hf78'); // 상품 개별 단가
const totalProductPrice = $('.product-price>span'); // 상품 개별 총금액
const selectDeleteCards = $('.btn_delete--3P5eHI2eDa'); // 상품 삭제 선택

/* 상품 */
const productQuntity = $('em.quan > .num--37aOyGmdW1'); // 상품 주문 수량
const productTotalFn = $('em.totalFn > .num--37aOyGmdW1'); // 상품 총 금액

/* 주문 */
const addBuy = $('.addBuy');

//fade time 값을 넣으면 지연 동작함. 일단 0으로 세팅
let fadeTime = 0;

$(totalFnAll);

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
            $(checkbox).attr('aria-checked', false);
            $(buy).attr('class', offBuy);
        } else{
            $(this).attr('class', checkboxAllTargeted);
            $(checkbox).attr('class', checkboxAllTargeted);
            $(checkbox).attr('aria-checked', true);
            $(buy).attr('class', onBuy);
        }
            totalFnAll();
    });

    // 개별 선택
    $(checkBoxEach).click(function(){
        if (this.className == checkboxAllTargeted){
            $(this).attr('class', checkboxAllTarget);
            $(this).attr('aria-checked', false);
            $(this).parents().eq(11).children().find(buy).attr('class', offBuy);
            // $(this).parents().eq(11).children().find(input).val(0);
            // console.log("val", $(input).val());

            // 전체 삭제
            $(selectDeleteCards).click(function(){
                $(this).parents().eq(4).children().find(cards).remove();
                $(this).parents().eq(4).children().find(input).val(0);
                // console.log("val", $(input).val());
            });
        } else{
            // const quan = [[${cart.quantity}]];
            $(this).attr('class', checkboxAllTargeted);
            $(this).attr('aria-checked', true);
            $(this).parents().eq(11).children().find(buy).attr('class', onBuy);
        }
            totalFnAll();
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
        // console.log($input.change());
    });


    $(input).change( function () {
        updateQuantity(this);
        totalFnAll();
        // console.log("click:!", $(this).val());

    });


    /* Update quantity */
    function updateQuantity(quantityInput)
    {
        const productRow = $(quantityInput).parents().eq(13);
        let eachPrice = productRow.children().find(productPrice).text();
        let price = parseInt(eachPrice.replaceAll('원','').replaceAll(',',''));
        // const price = parseInt(productRow.children().find(productPrice).text());

        const quantity = $(quantityInput).val();
        const linePrice = (price * quantity).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        //
        // console.log('가격', price);
        // console.log('수량', quantity);
        // console.log('총가격', linePrice)

        // totalFnAll();

        /* 선택상품금액 */
       productRow.children().find(selectedPrice).each(function () {
            // $(this).text(linePrice);
            $(this).fadeOut(fadeTime, function() {
                $(this).text(linePrice);
                // totalFnAll();
                $(this).fadeIn(fadeTime);
            });
        });


        /* 주문금액 */
        productRow.children().find(totalProductPrice).each(function () {
            // $(this).text(linePrice);
            $(this).fadeOut(fadeTime, function() {
                $(this).text(linePrice);
                // totalFnAll();
                $(this).fadeIn(fadeTime);
            });
        });

        /* 수량 */
        productRow.children().find(productQuntity).each(function () {
            // console.log('vv',this);
            // $(this).text(quantity);
            // totalFnAll();
            $(this).fadeOut(fadeTime, function() {
                $(this).text(quantity);
                // recalculateCart();
                $(this).fadeIn(fadeTime);
            });
        });

        productRow.children().find(productTotalFn).each(function () {
            // console.log('vv',this);
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



    /* Recalculate cart */


    // 선택 삭제
    $(removeCard).on("click", card, function(e){
        console.log("제거실행");
        const cartId = $(this).val();
        console.log(">> : " + cartId);
        var deleteOne = {"cartId": cartId};
        $.ajax({
            url: "/cart/removeOne",
            type: "POST",
            data: JSON.stringify(deleteOne),
            dataType: "text",
            contentType : "application/json",
            charset : "UTF-8",
            success: function (data) {
                alert("삭제 되었음!! ")
            },
            error: function (jqXHR, status, errorThrown) {
                alert("에러");
            }
        });

        $(e.target).closest(card).remove();
    });

    // 전체 삭제
    $(selectDeleteCards).click(function(){
        var memberId = $('#memberName').val();
        // btn_delete--3P5eHI2eDa
        console.log("전체 삭제 실행");
        console.log(">> " + memberId);
        var deleteAll = {"memberId": memberId};
        $.ajax({
            url: "/cart/removeAll",
            type: "POST",
            data: JSON.stringify(deleteAll),
            dataType: "text",
            contentType : "application/json",
            charset : "UTF-8",
            success: function (data) {
                alert("전체 삭제 되었음!! ")
            },
            error: function (jqXHR, status, errorThrown) {
                alert("에러");
            }
        });

        $(this).parents().eq(4).children().find(cards).remove();
    });

    $(addBuy).click(function(){
        // var productId = $(this).parents().eq(4).find().children(".productId").val();
        var productId = $(this).val();
        var quantity = $(this).parents().eq(2).find().children('.qtt').text();
        var urlBuy =  "/buy/add";

        var param = {"product": productId, "quantity":quantity};
        console.log("product : " + productId);
        console.log("quantity : " + quantity);
        // $.ajax({
        //     url: urlBuy,
        //     type: "POST",
        //     data: JSON.stringify(param),
        //     dataType: "text",
        //     contentType : "application/json",
        //     charset : "UTF-8",
        //     success: function (data) {
        //         alert("장바구니 추가되었음")
        //     },
        //     error: function (jqXHR, status, errorThrown) {
        //         alert("에러");
        //     }
        // });
    });

function totalFnAll()
{
    let subtotal = 0

        $(icoCheck).parents().find(totalProductPrice).each(function () {
            let checked = $(this).parents().eq(5).children(5).children().children().children().children().eq(0).attr('aria-checked');
            if (checked === 'true') {
            subtotal += parseInt($(this).text().replaceAll(',',''));
                // console.log('asd')
            }
        });

    // $(card).children().find(totalProductPrice).each(function () {
    //     subtotal += parseInt($(this).text().replaceAll(',',''));
        // subtotal += parseInt($(this).children().find(totalProductPrice).text());
    // });
    // console.log('>>subtotal:type:',typeof subtotal);
    // console.log('>>subtotal',subtotal);

    $('.num--2CYvhIm-m6').fadeOut(fadeTime, function() {
        let replaceTotalPrice = subtotal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        $(this).text(replaceTotalPrice);
        // $('#cart-total').html(subtotal);
        // if(subtotal == 0){
        //     $(card).fadeOut(fadeTime);
        // }else{
        //     $(card).fadeIn(fadeTime);
        // }
        $('.num--2CYvhIm-m6').fadeIn(fadeTime);
    });
}

$('.link_buy--jXvxZ8Agr-').click(function () {
    let buyArray = new Array() ;
    $('.check--2EZEnyir5V').children(0).each(function () {
    if ($(this).attr('aria-checked') === 'true') {
        let cartId = $(this).parents().eq(1).children().find($('.remove-product')).val();
        let productName = $(this).parent().next().children().find($('.ico_npay_plus--37zciz9t8T')).text();
        let productPrice = $(this).parents().eq(10).children().find($('.totalFn')).children(0).text().replace(',','');
        let quantity = $(this).parents().eq(10).children().find($('.quan')).children(0).text();

        let buy = {} ;

        buy.cartId = cartId ;
        buy.productName = productName ;
        buy.productPrice = productPrice ;
        buy.quantity = quantity ;

        buyArray.push(buy) ;

    }})
    let buyList = JSON.stringify(buyArray);

    let xmlHttp =new XMLHttpRequest();
    let url = "/cart/checkout?str=" + encodeURI(JSON.stringify(buyArray));
    console.log(url)
    xmlHttp.open("GET", url, true);
    xmlHttp.setRequestHeader('Content-Type', 'text/html;charset=utf-8');
    xmlHttp.send(null);
    // $.ajax({
    //     url: "/cart/checkout?str="+ encodeURI(JSON.stringify(buyArray)),
    //     type: "GET",
        // contentType : "text/plain",
        // contentType : "application/json",
        // dataType:'application/json',
        // dataType:'text/plain',
        // body: JSON.stringify(buyArray),
        // data: JSON.stringify(buyArray),
        // success: function(data){
        //     alert("삭제되었습니다.");
        // },
        // error: function (error){
        //     alert('삭제 실패했습니다.');
        // }
    // });

    console.log(buyList)

    // location.href = '/cart/checkout';
})
// function deleteNotices(){
//     var deleteList = [];
//     $("input[name='']:checked").each(function(){
//         deleteList.push($(this).val());
//     })
//     console.log(deleteList)
//     $.ajax({
//         url: "/board/deleteNotices.do",
//         type: "POST",
//         data: {deleteList : deleteList},
//         success: function(data){
//             alert("삭제되었습니다.");
//             window.location.reload();
//         },
//         error: function (error){
//             alert('삭제 실패했습니다.');
//         }
//     });


// 전체 삭제
// $(selectDeleteCards).click(function(){
//     var memberId = $('#memberName').val();
//     // btn_delete--3P5eHI2eDa
//     console.log("전체 삭제 실행");
//     console.log(">> " + memberId);
//     var deleteAll = {"memberId": memberId};
//     $.ajax({
//         url: "/cart/removeAll",
//         type: "POST",
//         data: JSON.stringify(deleteAll),
//         dataType: "text",
//         contentType : "application/json",
//         charset : "UTF-8",
//         success: function (data) {
//             alert("전체 삭제 되었음!! ")
//         },
//         error: function (jqXHR, status, errorThrown) {
//             alert("에러");
//         }
//     });
//
//     $(this).parents().eq(4).children().find(cards).remove();
// });
//
// });
//
