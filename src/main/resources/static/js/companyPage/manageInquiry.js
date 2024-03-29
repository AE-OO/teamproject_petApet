$(document).ready(function() {
    getInquiredList();

    // 문의 내역의 한 행을 클릭하면 해당 내용이 표 하단에 보여짐 (onclick 안쓰려고 이렇게 함)
    $("#inquiryTable").on("click", "tr", function(){
        //세부사항 틀이 화면에 보여짐
        $("#inquiryDetailInfo").css("display","");
        //클릭한 행의 데이터 가져옴
        var tr = $(this);
        //가져온 데이터를 input 태그에 각각 넣음
        $("#detailNum").val(tr.children().eq(0).text());
        $("#detailCategory").val(tr.children().eq(1).text());
        $("#detailTitle").val(tr.children().eq(2).text());
        $("#detailWriter").val(tr.children().eq(3).text());
        $("#detailDate").val(tr.children().eq(4).text());
        $("#detailContent").val(tr.children().eq(5).text());
        $("#answerContent").val(tr.children().eq(6).text());

        // 이미 답변한 문의면 답변 textarea는 readonly로 바꾸고 답변등록 버튼도 안보이게 함
        if(tr.children().eq(7).text() === '답변완료'){
            $("#answerContent").attr("readonly",true);
            $("#insertAnswerBTN").css("display","none");
        }else{
            $("#answerContent").attr("readonly",false);
            $("#insertAnswerBTN").css("display","");
        }
    })

    //문의 세부사항의 X 버튼 클릭 시
    $("#closeDetailInfoBTN").click(function(){
        //세부사항 정보를 다시 안보이게 함
        $("#inquiryDetailInfo").css("display","none");
    })

    // 답변등록 버튼 클릭 시
    $("#insertAnswerBTN").click(function(){
        if($("#answerContent").val() === ''){
            alert("답변 입력 후 버튼을 클릭해 주세요");
        }else {
            var inquiredList = {
                answer : $("#answerContent").val()
            }
            if(confirm("답변을 등록하시겠습니까? 등록된 답변은 수정할 수 없습니다.")){
                $.ajax({
                    url: "/inquiry/addAnswer/" + $("#detailNum").val(),
                    data: inquiredList,
                    type: "POST",
                    dataType: "text",
                }).done(function(){
                    alert("답변을 등록하였습니다.");
                    $("#inquiryDetailInfo").css("display","none");
                    getInquiredList();
                })
            }
        }
    });
});

//회사 마이페이지에 문의 내역 리스트 출력
function getInquiredList(){
    $.getJSON('/inquiry/manageInquiry', function(result){
        var list = '';
        if(result.length > 0){
            $.each(result, function(idx, inquiry) {
                list += `<tr id="inquiryData`+ idx +`">
                            <td class="pl-0" id="inquiryId">${inquiry.inquiredId}</td>
                            <td>${inquiry.inquiredCategory}</td>
                            <td>${inquiry.inquiredTitle}</td>
                            <td>${inquiry.memberId}</td>
                            <td>${inquiry.inquiredDate}</td>
                            <td style="display: none">${inquiry.inquiredContent}</td>
                            <td style="display: none">${inquiry.answer}</td>
                            <td>`;
                
                if(inquiry.checked == 0){
                    list += `<label class="btn btn-secondary btn-sm checked">답변대기</label>`;
                }else if(inquiry.checked == 1){
                    list += `<label class="btn btn-primary btn-sm checked">답변완료</label>`;
                }
                list += `</td> </tr>`;
            })
        }else{
            $("#noData").text("문의 내역이 없습니다.");
        }
        $(".inquiryData").html(list);
    })
}
