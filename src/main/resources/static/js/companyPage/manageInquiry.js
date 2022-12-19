$(document).ready(function() {
    getInquiredList();
});

//회사 마이페이지에 문의 내역 리스트 출력
function getInquiredList(){
    $.getJSON('/inquiry/manageInquiry', function(result){
        var list = '';
        if(result.length > 0){
            $.each(result, function(idx, inquiry) {
                console.log(result);
                list += `<tr id="inquiryData`+ idx +`" onclick="showProductDetailInfo()">
                                    <td class="pl-0" id="inquiryId">`+ inquiry.inquiredId +`</td>
                                    <td>` + inquiry.inquiredCategory + `</td>
                                    <td>` + inquiry.inquiredTitle + `</td>
                                    <td>` + inquiry.memberId + `</td>
                                    <td>` + inquiry.inquiredDate + `</td>
                                    <td><label class="btn btn-secondary btn-sm" th:if="${inquiry.checked === 'false'}">미답</label>
                                    <label class="btn btn-primary btn-sm" th:unless="${inquiry.checked  === 'false'}">답변완료</label></td>
                                </tr>`;
            })
        }else{
            $("#noData").text("문의 내역이 없습니다.");
        }

        $(".inquiryData").html(list);
    })
}

function showProductDetailInfo(){
    $("#inquiryDetailInfo").css("display","");
}