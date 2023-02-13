$(document).ready(function () {
    let page = 0;
    getInquiryList(page);

    $(document).on("click", ".productInquiry", function() {
        $("#modalTargetProductId").val($('.productTitle').attr('data-id'));
    })

    $(document).on("click", "#registerInquiryBTN", function() {
        var inquiryRequestDTO = {
            inquiredTitle : $("#inquiryTitle").val(),
            inquiredContent : $("textarea[name=inquiryContent]").val(),
            productId : $("#modalTargetProductId").val(),
            companyId : $('.productSeller').attr('data-id'),
            memberId : "member010"                     //여기 세션에서 값 가져오는 걸로 수정하기
        }
        $.ajax({
            url: "/inquiry/registerProductInquiry",
            type: "post",
            data: JSON.stringify(inquiryRequestDTO),
            contentType: "application/json; charset=utf-8",
            success: function(result) {
                if(result === 'success'){
                    alert("문의가 등록되었습니다.")
                    modalClear();
                    getInquiryList();
                }
            }
        })
    })

    $(document).on("click", "#inquiryData", function(){
        var tr = $(this);
        var showId = tr.attr("showId");
        
        // 문의 제목 클릭했을 때 문의 내용과 답변 내용 띄움 (토글)
        if(tr.attr("clicked") === "false"){
            tr.attr("clicked", "true");
            $("#content" + showId).css({"display": "", "background-color":"#f7f8fa"});
            $("#answer" + showId).css({"display":"", "background-color":"#f7f8fa"});
        }else{
            tr.attr("clicked", "false");
            $("#content" + showId).css("display", "none");
            $("#answer" + showId).css("display", "none");
        }
    })

    //이전 페이지
    $(document).on("click", "#prevPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        getInquiryList(nowPage - 1);
    });
    //다음 페이지
    $(document).on("click", "#nextPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        getInquiryList(nowPage + 1);
    });
    //댓글 페이지선택
    $(document).on("click", ".selectPage", function () {
        if ($(this).parent().hasClass('active')) {
            return;
        }
        getInquiryList($(this).text() - 1)
    });
})

// 모달 초기화 메소드
function modalClear(){
    $("#inquiryTitle").val("");
    $("#inquiryContent").val("");
}

// 등록된 문의 리스트 가져오는 메소드
function getInquiryList(page) {
    $.getJSON('/inquiry/getProductInquiry/' + $('.productTitle').attr('data-id') + '/' + page, function (result) {
        var list = '';
        nowPage = result.number;
        if (result.content.length > 0) {
            $.each(result.content, function (idx, inquiry) {
                list += `<tr id="inquiryData" clicked="false" showId="`+idx+`">
                            <td class="text-center">${inquiry.checked ? "답변완료" : "답변예정"}</td>
                            <td style="cursor: pointer">${inquiry.inquiredTitle}</td>
                            <td class="text-center">${inquiry.memberId}</td>
                            <td class="text-center">${inquiry.inquiredDate}</td>
                        </tr>
                        <tr id="content`+idx+`" style="display: none">
                            <td style="border-bottom: none"></td>
                            <td>${inquiry.inquiredContent}</td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr id="answer`+idx+`" style="display: none">
                            <td></td>
                            <td>${inquiry.answer === null ? "└ 아직 답변이 등록되지 않았습니다." : "└ " + inquiry.answer}</td>
                            <td class="text-center">${inquiry.checked ? "판매자" : ""}</td>
                            <td></td>
                        </tr>`;
            })
        }
        $(".inquiryData").html(list);
        showPage(result);
    })
}