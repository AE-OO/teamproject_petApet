$(document).ready(function () {
    //회원 신고 버튼 클릭시 모달 구성
    $(document).on("click", ".productReport", function () {
        $("#reportModalTitle").text("상품신고");
        
        var str = '';
        str = `<select class="form-select" aria-label="Default select example" id="reportProductReason"
                        name="reportProductReason">
                    <option value="">신고사유</option>
                    <option value="불량품 다수">불량품 다수</option>
                    <option value="유해물질">유해물질</option>
                    <option value="기타 사유">기타 사유</option></select>`;

        $(".reportModalReason").html(str);
        $("#reportBTN").attr("id","reportProductBTN");
    });

    //회원 신고 버튼 클릭시 모달 구성
    $(document).on("click", ".memberReport", function () {
        let reportMemberId = $(this).closest(".memberDiv").find(".memberId").text();
        console.log(reportMemberId);

        $("#reportModalTitle").text("회원신고");
        $("#modalTargetId").val(reportMemberId);
        var str = '';
        str = `<select class="form-select" aria-label="Default select example" id="reportMemberReason">
                    <option value="">신고사유</option>
                    <option value="회원비난/비하">회원비난/비하</option>
                    <option value="욕설/비속어">욕설/비속어</option>
                    <option value="불쾌감 조성">불쾌감 조성</option>
                    <option value="이용방해 행위">이용방해 행위</option>
                    <option value="타인권리 침해">타인권리 침해</option>
                    <option value="특정집단 차별">특정집단 차별</option>
                    <option value="기타 사유">기타 사유</option></select>`;

        $(".reportModalReason").html(str);
        $("#reportBTN").attr("id","reportMemberBTN");
        // alert("회원 신고 테스트, 회원 아이디 : " + reportMemberId);
    });
    
    //게시글 신고 버튼 클릭시 모달 구성
    $(document).on("click", ".communityReport", function () {
        let reportCommunityId = $(location).attr('pathname').substring(11);
        
        if ($("#postsMemberId").text() === loginId) {
            alert("본인이 작성한 게시물은 신고할 수 없습니다.");
            // 알림창뜨고 빈 모달 뜨는 문제 발생 -----------------------------------------------수정하기
        }else {
            $("#reportModalTitle").text("게시글신고");
            $("#modalTargetId").val(reportCommunityId);
            var str = '';
            str = `<select class="form-select" aria-label="Default select example" id="reportCommunityReason">
                    <option value="">신고사유</option>
                    <option value="예의에 어긋난 게시물">예의에 어긋난 게시물</option>
                    <option value="무단광고/홍보">무단광고/홍보</option>
                    <option value="허위사실 유포">허위사실 유포</option>
                    <option value="불법행위 관련/소개">불법행위 관련/소개</option>
                    <option value="타인권리 침해">타인권리 침해</option>
                    <option value="게시판 용도위반/부적절">게시판 용도위반/부적절</option>
                    <option value="특정집단 차별">특정집단 차별</option>
                    <option value="외설적 표현물">외설적 표현물</option>
                    <option value="기타 사유">기타 사유</option></select>`;
            $(".reportModalReason").html(str);
            $("#reportBTN").attr("id","reportCommunityBTN");
        }
    });
    
    //상품신고모달의 신고하기 버튼을 눌렀을 떄 서버와 통신
    $(document).on("click", "#reportProductBTN", function(){
        var reportProductDTO = {
            reportReason : $("#reportProductReason option:selected").val(),
            reportReasonDetail : $("textarea[name=reportReasonDetail]").val(),
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
                    modalClear();
                }
            }
        })
    });

    //회원신고모달의 신고하기 버튼을 눌렀을 떄 서버와 통신
    $(document).on("click", "#reportMemberBTN", function(){
        var reportMemberDTO = {
            reportReason : $("#reportMemberReason option:selected").val(),
            reportReasonDetail : $("textarea[name=reportReasonDetail]").val(),
            memberId : $("#modalTargetId").val()
        }
        
        $.ajax({
            url: "/report/addMemberReport",
            type: "post",
            data: JSON.stringify(reportMemberDTO),
            contentType: 'application/json; charset=utf-8',
            success: function (result) {
                if(result === 'success'){
                    alert("회원을 신고하였습니다.");
                    modalClear();
                }
            }
        })
    });

    //게시글신고모달의 신고하기 버튼을 눌렀을 떄 서버와 통신
    $(document).on("click", "#reportCommunityBTN", function(){
        var reportCommunityDTO = {
            reportReason : $("#reportCommunityReason option:selected").val(),
            reportReasonDetail : $("textarea[name=reportReasonDetail]").val(),
            communityId : $("#modalTargetId").val()
        }

        $.ajax({
            url: "/report/addCommunityReport",
            type: "post",
            data: JSON.stringify(reportCommunityDTO),
            contentType: 'application/json; charset=utf-8',
            success: function (result) {
                if(result === 'success'){
                    alert("게시글을 신고하였습니다.");
                    modalClear();
                }
            }
        })
    });

    $(document).on("click", "#reportCancleBTN", function (){
        modalClear();
    });
})

function modalClear(){
    $("#reportReasonDetail").val("");
}