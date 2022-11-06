$(document).ready(function () {
    $("#reportProductBTN").click(function(){
        var target = $("input[name=productId]").val();
        var reason = $("#reportProductReason option:selected").val();
        var detailReason = $("textarea[name=reportProductReasonDetail]").val();

        console.log(target);
        console.log(reason);
        console.log(detailReason);

        var reportProductDTO = {
            reportReason : reason,
            reportReasonDetail : detailReason,
            productId : target
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
    })
})