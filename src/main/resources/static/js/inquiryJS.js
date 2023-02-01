$(document).ready(function () {
    getInquiryList();

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

// 등록된 문의 리스트 가져오는 메소드
function getInquiryList() {
    $.getJSON('/inquiry/getProductInquiry/' + $("#productId").val(), function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, inquiry) {
                list += `<em class="prod-inquiry-item__label">질문</em>
                                <div class="prod-inquiry-item__wrap">
                                    <strong class="prod-inquiry-item__author">${inquiry.memberId}</strong>
                                    <div class="prod-inquiry-item__title">${inquiry.inquiredTitle}</div>
                                    <div class="prod-inquiry-item__content">${inquiry.inquiredContent}</div>
                                    <div class="prod-inquiry-item__time">${inquiry.inquiredDate}</div>
                                </div>

                                <div class="prod-inquiry-item__reply">
                                    <i class="prod_inquiry-item__reply__icon"></i>
                                    <em class="prod-inquiry-item__reply__label">답변</em>
                                    <div class="prod-inquiry-item__reply__wrap">
                                        <div class="prod-inquiry-item__reply__content">`;

                if(inquiry.checked === false){
                    list += `답변이 아직 등록되지 않았습니다.`;
                }else{
                    list += `${inquiry.answer}`;
                }
                    list += `</div>
                                    </div>
                                </div>
                                <hr />`
            })
        }
        $(".prod-inquiry-item").html(list);
    })
}