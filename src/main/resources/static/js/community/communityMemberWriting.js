$(function () {
    getMemberProfile()
    getMemberWritingList(nowPage, nowPageSize, "community")
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
        getMemberWritingList(nowPage, $(this).find('span').text(), nowSort)
    });
    //작성글,댓글단 글 선택
    $("#type").children().click(function () {
        getMemberWritingList(nowPage, nowPageSize, $(this).val())
    });
    //이전 페이지
    $(document).on("click", "#prevPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        getMemberWritingList(nowPage - 1, nowPageSize, nowSort);

    });
    //다음 페이지
    $(document).on("click", "#nextPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        getMemberWritingList(nowPage + 1, nowPageSize, nowSort);

    });
    //댓글 페이지선택
    $(document).on("click", ".selectPage", function () {
        if ($(this).parent().hasClass('active')) {
            return;
        }
        getMemberWritingList($(this).text() - 1, nowPageSize, nowSort);
    });
})
getMemberProfile = function () {
    $.ajax({
        url: "/getMemberProfile",
        type: "post",
        data: {
            memberId: $(location).attr('pathname').split("/")[3],
        },
        dateType: "json",
        success: function (data) {
            showProfile(data);
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}
showProfile = function (data) {
    if (data.memberImg !== "0") {
        $("#memberImg").attr("/image/" + data.memberImg);
    }
    $("#memberId").text(data.memberId);
    $("#communityCount").text(data.communityCount);
    $("#commentCount").text(data.commentCount);
}