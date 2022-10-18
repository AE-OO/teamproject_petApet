$('.bScTTg').click(function(){
    $(this).parents().eq(6).children().find('.cOIBfe').css('display', 'flex');
});
$('.gAMxLy').click(function(){
    $(this).parents().eq(6).children().find('.cOIBfe').css('display', 'none');
});

$('.delete-order').click(function(){
    $(this).parents().eq(11).remove();
});

// 날짜 버튼 
// $('.sc-py7v18-1').click(function(){
//     if($(this).className == $('.sc-py7v18-1 krUtDN')){
//         $(this).attr('class', 'sc-py7v18-1 dLgQrg');
//     }
//     console.log("gg");
// });