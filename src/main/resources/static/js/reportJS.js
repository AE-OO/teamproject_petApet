$(document).ready(function () {
    $("#reportProductBTN").click(function(){
        var reportProductDTO = {
            reportReason : $("#reportProductReason option:selected").val(),
            reportReasonDetail : $("textarea[name=reportProductReasonDetail]").val(),
            productId : $("#productId").val()
        }

        $.ajax({
            url: "/report/addProductReport",
            type: "post",
            data: JSON.stringify(reportProductDTO),
            contentType: 'application/json; charset=utf-8',
            success: function (result) {
                if(result === 'success'){
                    alert("상품을 신고하였습니다.");
                }
            }
        })
    });

    $("#reportMemberBTN").click(function(){
        var reportMemberDTO = {
            reportReason : $("#reportMemberReason option:selected").val(),
            reportReasonDetail : $("textarea[name=reportMemberReasonDetail]").val(),
            memberId : $("#modalMemberId").val()
        }

        $.ajax({
            url: "/report/addMemberReport",
            type: "post",
            data: JSON.stringify(reportMemberDTO),
            contentType: 'application/json; charset=utf-8',
            success: function (result) {
                if(result === 'success'){

                    alert("회원을 신고하였습니다.");
                }
            }
        })
    });

    $("#reportCommunityBTN").click(function(){
        var reportMemberDTO = {
            reportReason : $("#reportCommunityReason option:selected").val(),
            reportReasonDetail : $("textarea[name=reportCommunityReasonDetail]").val(),
            communityId : $("input[name=communityId]").val()
        }

        $.ajax({
            url: "/report/addCommunityReport",
            type: "post",
            data: JSON.stringify(reportMemberDTO),
            contentType: 'application/json; charset=utf-8',
            success: function (result) {
                if(result === 'success'){
                    alert("상품을 신고하였습니다.");
                }
            }
        })
    });
})

function sendMemberId(memberId){
    $("#modalMemberId").val(memberId);
}