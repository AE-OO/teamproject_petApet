$(document).ready(function () {
    $(document).on("click", ".productInquiry", function() {
        $("#modalTargetProductId").val($("#productId").val());
    })

    $(document).on("click", "#registerInquiryBTN", function() {
        var inquiryRequestDTO = {
            inquiredTitle : $("#inquiryTitle").val(),
            inquiredContent : $("textarea[name=inquiryContent]").val(),
            productId : $("#modalTargetProductId").val(),
            companyId : $("#companyId").val(),
            memberId : "member010"                     //여기 세션에서 값 가져오는 걸로 수정하기
        }
        console.log(inquiryRequestDTO);
        $.ajax({
            url: "/inquiry/registerProductInquiry",
            type: "post",
            data: JSON.stringify(inquiryRequestDTO),
            contentType: "application/json; charset=utf-8",
            success: function(result) {
                if(result === 'success'){
                    alert("문의가 등록되었습니다.")
                    modalClear();
                }
            }
        })
    })
})

// 모달 초기화 메소드
function modalClear(){
    $("#inquiryTitle").val("");
    $("#inquiryContent").val("");
}