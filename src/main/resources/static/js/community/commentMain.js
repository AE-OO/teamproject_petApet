$(function () {
    getCommunityTitle();
});
getCommunityTitle = function () {
    $.ajax({
        url: "/community/getCommunityTitle",
        type: "post",
        data: {communityId: $(location).attr('pathname').split("/")[2]},
        dateType: "json",
        success: function (data) {
            $("#communityTitle").text(data.communityTitle);
            postsMemberId = data.memberId;
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}