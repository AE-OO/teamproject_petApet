$(function () {
    getMemberProfile();
    $("#closeBtn").click(function () {
        window.close();
    });
    $("#addProductReport").click(function () {
        window.resizeTo(650, 700);
    });
    $("#sendMessage").click(function () {
        goMessagePage($("#memberId").text());
        window.close();
    });
    $(".cancleBtn").click(function () {
        window.resizeTo(400, 350);
    });
    $("#reportCancleBTN").click(function () {
        window.resizeTo(400, 350);
    });
    if (loginId === $(location).attr('pathname').split("/")[3] || authorities !== "[ROLE_MEMBER]") {
        $("#sendMessage").remove();
        $("#addProductReport").remove();
    }
});
getMemberProfile = function () {
    $.ajax({
        url: "/getMemberProfile",
        type: "post",
        data: {memberId: $(location).attr('pathname').split("/")[3]},
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
    $("#joinDate").text(data.memberJoinDate);
    $("#communityCount").text(data.communityCount);
    $("#commentCount").text(data.commentCount);
}