$(function () {
    getSearchList(nowPage, nowPageSize, "communityId")
    $("#type").val(type);
    $("#searchContent").val(searchContent);
    $("#typeRes").text("(" + typeRes(type) + ")");
    $("#searchContentRes").text("'" + searchContent + "'");
    $("input[name=radioCheck]").click(function () {
        if ($("input[name=radioCheck]:checked").val() === "title") {
            $(".communityContent").hide();
            $(".communityTitle").removeClass("border-white")
        } else {
            $(".communityContent").show();
            $(".communityTitle").addClass("border-white")
        }
    })
    //목록 개수 선택
    $("#pageSizeList").children().click(function () {
        getSearchList(nowPage, $(this).find('span').text(), nowSort)
    });
    //최신순,조회순,댓글순  선택
    $("#sort").children().click(function () {
        getSearchList(nowPage, nowPageSize, $(this).val())
    });
    //댓글 보기
    $(document).on("click", ".badge2", function () {
        window.open("/comment/" + $(this).parent().prev().text().trim(), "_blank", "width=900,height=800,left=0,top=0,location=no");
    });
    //이전 페이지
    $(document).on("click", "#prevPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        getSearchList(nowPage - 1, nowPageSize, nowSort);

    });
    //다음 페이지
    $(document).on("click", "#nextPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        getSearchList(nowPage + 1, nowPageSize, nowSort);

    });
    //댓글 페이지선택
    $(document).on("click", ".selectPage", function () {
        if ($(this).parent().hasClass('active')) {
            return;
        }
        getSearchList($(this).text() - 1, nowPageSize, nowSort);
    });
})