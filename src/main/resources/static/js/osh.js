$(document).ready(function () {

    $('.review_comment').each(function () {
        // var content = $(this).children('.content');
        var content = $(this).find('.contentStr');

        var content_txt = content.text();
        var content_html = content.html();
        var content_txt_short = content_txt.substring(0, 500) + "...";
        var btn_more = $('<a href="javascript:void(0)" class="more">더보기</a>');


        $(this).append(btn_more);

        if (content_txt.length >= 500) {
            content.html(content_txt_short)

        } else {
            btn_more.hide()
        }

        btn_more.click(toggle_content);

        function toggle_content() {
            if ($(this).hasClass('short')) {
                // 접기 상태
                $(this).html('더보기');
                content.html(content_txt_short)
                $(this).removeClass('short');
            } else {
                // 더보기 상태
                $(this).html('접기');
                content.html(content_html);
                $(this).addClass('short');

            }
        }
    });
});

const putValue = (target) => {
    let value = $('#inputGroupSelect01').val();
    $('input[name=productDiv]').attr('value',value);

}

