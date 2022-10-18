$(document).ready(function () {
    reportColor();

    $(".deleteFAQ").click(function () {
        var id = $(this).attr("id");

        var returnConfirm = confirm("삭제하시겠습니까?");
        if (returnConfirm) {
            $.ajax({
                url: "/admin/deleteFAQ/" + id,
                type: "get",
                success() {
                    location.href="/admin/adminPage";
                }
            })
        }
    });

    $(".communityModal").click(function(){
        var communityId = $(this).attr("id");

        $(".communityDelete").empty();

        var modalBody = '';
        modalBody += '게시글<br/>';
        modalBody += '<div style="color: red">' + communityId + '</div> 번';
        modalBody += '을(를) 삭제하시겠습니까?<br/>삭제 버튼을 누르면 삭제됩니다.<br/>';
        $(".communityDelete").append(modalBody);
        $(".confirmCommunityDelete").attr("id", communityId);
        $("#confirmCommunityDeleteModal").modal('show');
    });

    $(".confirmCommunityDelete").click(function(){
       var communityId = $(this).attr("id");
       console.log(communityId);

       $.ajax({
           url: "/admin/deleteCommunity/" + communityId,
           type: "get",
           success(){
                $("#" + communityId).empty();
               // location.href="/admin/adminPage";
           }, error(){
               $("#" + communityId).empty();
               // location.href="/admin/adminPage";
           }
       })
    });

    $(".memberModal").click(function(){
        var memberId = $(this).attr("id");

        $(".memberDelete").empty();

        var modalBody = '';
        modalBody += '아이디<br/>';
        modalBody += '<div style="color: red">' + memberId + '</div>';
        modalBody += '을(를) 삭제하겠습니까?<br/>확인을 누르면 삭제됩니다.<br/>';
        $(".memberDelete").append(modalBody);
        $(".confirmDisabledMember").attr("id", memberId);
        $(".confirmMemberDelete").attr("id", memberId);
        $("#confirmMemberDeleteModal").modal('show');
    });

    $(".confirmDisabledMember").click(function(){
       var memberId = $(this).attr("id");
       console.log(memberId);

       $.ajax({
           url: "/admin/disabledMember/" + memberId,
           type: "get",
           success() {
               location.reload();
           }
       })
    });

    $(".confirmMemberDelete").click(function(){
       var memberId = $(this).attr("id");
       console.log(memberId);

       $.ajax({
           url: "/admin/deleteMember/" + memberId,
           type: "get",
           success() {
               location.reload();
           }, error(){
               location.reload();
           }
       })
    });

    $(".setProductStatus").click(function(){
        var productId = $(this).attr("value");
        var selectedStatus = $(this).parent().parent().find("select[name=productStatus]").val();

        $.ajax({
            url: "/admin/updateProductStatus/" + productId,
            type: "get",
            data: {status : selectedStatus},
            success(){
                location.href="/admin/adminPage";
            }
        })
    });

    $(".acceptCommunityReportBtn").click(function(){
        var reportId = $(this).parent().parent().find("td[id=communityReportId]").text();
        var communityId = $(this).parent().parent().find("td[id=communityId]").text();

        $.ajax({
            url: "/admin/acceptCommunityReport/" + reportId,
            data: {communityId : communityId},
            type: "get",
            success(){
                location.reload();
            }
        })
    });

    $(".acceptMemberReportBtn").click(function(){
       var reportId = $(this).parent().parent().find("td[id=memberReportId]").text();
       var memberId = $(this).parent().parent().find("td[id=memberId]").text();

       $.ajax({
           url: "/admin/acceptMemberReport/" + reportId,
           data: {memberId : memberId},
           type: "get",
           success(){
               location.reload();
           }
       })
    });


})

function reportColor(){
    if($("#report").text() >= 3)
        $("#report").attr("class", "text-danger");
}