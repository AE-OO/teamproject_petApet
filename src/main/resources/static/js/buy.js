$('.bScTTg').click(function(){
    $(this).parents().eq(6).children().find('.cOIBfe').css('display', 'flex');
});
$('.gAMxLy').click(function(){
    $(this).parents().eq(6).children().find('.cOIBfe').css('display', 'none');
});

$('.delete-order').click(function(){
    $(this).parents().eq(12).remove();
});

// 날짜 버튼
// $('.sc-py7v18-1').click(function(){
//     if($(this).className == $('.sc-py7v18-1 krUtDN')){
//         $(this).attr('class', 'sc-py7v18-1 dLgQrg');
//     }
//     console.log("gg");
// });

$('.addCart').click(function () {
    let productId = $(this).val();
    let quantity = 1;
    let param = {"product": productId, "quantity": quantity};
    let isExist = checkCart(productId);
    if (isExist === true) {
        alert('이미 장바구니에 상품이 있습니다.')
        if (confirm("장바구니로 이동 하시겠습니까?") == true) {
            location.href = postUrl
        }
    } else {
        $.ajax({
            url: "/cart/add",
            type: "POST",
            data: JSON.stringify(param),
            dataType: "text",
            async: false,
            contentType: "application/json",
            charset: "UTF-8",
            success: function (data) {
                if (confirm("장바구니로 이동 하시겠습니까?") == true) {
                    location.href = postUrl
                } else {

                }
            },
            error: function (jqXHR, status, errorThrown) {
                let result = JSON.parse(jqXHR.responseText);
                alert(result.message);
            }
        });
    }
});

function checkCart(productId) {
    let isExist = '';
    $.ajax({
        url: "/cart/isExist?productId=" + productId,
        type: "GET",
        async: false,
        success: function (data) {
            isExist = data;
        },
        error: function (jqXHR, status, errorThrown) {
        }
    })
    return isExist;
}
