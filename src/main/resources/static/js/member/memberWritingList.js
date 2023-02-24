$(function () {
    getLoginMemberProfile();
    getLoginMemberWritingList(nowPage, nowPageSize, "community");
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
        getLoginMemberWritingList(nowPage, $(this).find('span').text(), nowSort)
    });
    //작성글, 댓글 선택
    $("#type").children().click(function () {
        getLoginMemberWritingList(nowPage, nowPageSize, $(this).val())
    });
    //이전 페이지
    $(document).on("click", "#prevPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        getLoginMemberWritingList(nowPage - 1, nowPageSize, nowSort);

    });
    //다음 페이지
    $(document).on("click", "#nextPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        getLoginMemberWritingList(nowPage + 1, nowPageSize, nowSort);

    });
    //댓글 페이지선택
    $(document).on("click", ".selectPage", function () {
        if ($(this).parent().hasClass('active')) {
            return;
        }
        getLoginMemberWritingList($(this).text() - 1, nowPageSize, nowSort);
    });
    $("#allCheck").click(function () {
        if ($("input[name='deleteCheck']:checked").length === $(".communityTitle").length) {
            $("input[name='deleteCheck']").prop("checked", false)
        } else {

            $("input[name='deleteCheck']").prop("checked", true)
        }
    })
    $("#deleteBtn").click(function () {
        if ($("input[name='deleteCheck']:checked").length === 0) {
            alert("삭제할 게시물을 선택해 주세요.")
            return;
        }
        if (confirm("정말로 삭제하시겠습니까?") == true) {
            myWritingDelete(nowSort)
        } else {
            return;
        }
    })

    //댓글 이미지 확대
    $(document).on("click", ".zoom-in", function () {
        $(this).attr("width", "");
        $(this).attr("height", "");
        $(this).css("max-width", $(".commentImgDiv").width());
        $(this).toggleClass("zoom-in");
        $(this).toggleClass("zoom-out");
    });
    //댓글 이미지 축소
    $(document).on("click", ".zoom-out", function () {
        $(this).attr("width", "70");
        $(this).attr("height", "70");
        $(this).toggleClass("zoom-in");
        $(this).toggleClass("zoom-out");
    });
})
getLoginMemberProfile = function () {
    $.ajax({
        url: "/member/getLoginMemberProfile",
        type: "post",
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
myWritingDelete = function (type) {
    let id = [];
    let img = [];
    if (type === "community") {
        let communityImg = [];
        $("input[name='deleteCheck']:checked").each(function () {
            let idx = $(this).closest(".communityTitle").next().find("a").attr("data-value")
            id.push($(this).val());
            if (communityContentParam[idx].length > 0) {
                communityImg.push(communityContentParam[idx])
            }
        });
        img = communityImg.join().split(",")
    } else {
        $("input[name='deleteCheck']:checked").each(function () {
            id.push($(this).val());
            if ($(this).closest(".communityTitle").find(".commentImg").attr("src") === undefined) {
                return;
            }
            img.push($(this).closest(".communityTitle").find(".commentImg").attr("src").split("/")[2])
        });
    }
    $.ajax({
        url: "/"+type+"/myWritingDelete",
        type: "post",
        data: {
            deleteId: id,
            deleteImg: img
        },
        // contentType: "application/json",
        dateType: "text",
        success: function () {
            getLoginMemberWritingList(nowPage, nowPageSize, type);
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}