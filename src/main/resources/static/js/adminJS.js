$(document).ready(function () {
    reportColor();
    moveSideBar();
    movePage();
    setOutOfStock()

    //faq 삭제 버튼 클릭
    $(".deleteFAQ").click(function () {
        var id = $(this).attr("id");

        var returnConfirm = confirm("삭제하시겠습니까?");
        if (returnConfirm) {
            $.ajax({
                url: "/admin/deleteFAQ/" + id,
                type: "get",
                success() {
                    location.href = "/admin/adminPage";
                }
            })
        }
    });

    //커뮤니티 관리 부분의 버튼 클릭 시 모달 뜨게
    $(".communityModal").click(function () {
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

    //게시글 삭제 버튼 클릭
    $(".confirmCommunityDelete").click(function () {
        var communityId = $(this).attr("id");
        console.log(communityId);

        $.ajax({
            url: "/admin/deleteCommunity/" + communityId,
            type: "get",
            success() {
                $("#" + communityId).empty();
            }, error() {
                $("#" + communityId).empty();
            }
        })
    });

    //회원관리 부분의 버튼 클릭시 모달 뜨게
    $(".memberModal").click(function () {
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

    //회원 정지 버튼 클릭
    $(".confirmDisabledMember").click(function () {
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

    //회원 강제탈퇴 버튼 클릭
    $(".confirmMemberDelete").click(function () {
        var memberId = $(this).attr("id");
        console.log(memberId);

        $.ajax({
            url: "/admin/deleteMember/" + memberId,
            type: "get",
            success() {
                location.reload();
            }, error() {
                location.reload();
            }
        })
    });

    $(".setProductStatus").click(function () {
        var productId = $(this).attr("value");
        var selectedStatus = $(this).parent().parent().find("select[name=productStatus]").val();
        var productStock = $(this).parent().parent().find("input[name=productStock]").val();

        $.ajax({
            url: "/admin/updateProductStatus/" + productId,
            type: "get",
            data: {status: selectedStatus, stock: productStock},
            success() {
                location.href = "/admin/adminPage";
            }
        })
    });

    //신고 승인 버튼 클릭
    $("#acceptReportBtn").click(function () {
        console.log($("#modalTargetId").val());

        if ($("#modalTargetType").val() === "community") {
            $.ajax({
                url: "/admin/acceptCommunityReport/" + $("#modalReportId").val(),
                data: {communityId: $("#modalTargetId").val()},
                type: "get",
                success() {
                    location.reload();
                }
            })
        } else if ($("#modalTargetType").val() === "product") {
            $.ajax({
                url: "/admin/acceptProductReport/" + $("#modalReportId").val(),
                data: {productId: $("#modalTargetId").val()},
                type: "get",
                success() {
                    location.reload();
                }
            })
        } else {
            $.ajax({
                url: "/admin/acceptMemberReport/" + $("#modalReportId").val(),
                data: {memberId: $("#modalTargetId").val()},
                type: "get",
                success() {
                    location.reload();
                }
            })
        }
    });

    //신고 반려 버튼 클릭
    $("#cancelReportBtn").click(function () {
        $.ajax({
            url: "/admin/refuseReport/" + $("#modalReportId").val(),
            type: "post",
            success() {
                location.reload();
            }
        })
    });

    $("#acceptCompanyJoinBtn").click(function(){
       $.ajax({
           url: "/acceptJoinCompany/" + $("#modalCompanyId").val(),
           type: "post",
           success(){
                location.reload();
           }
       })
    });
})

//신고수가 3 이상이 되면 글씨 빨갛게 바꾸기 - 동작안함
function reportColor() {
    if ($("#report").text() >= 3) {
        $("#report").attr("class", "text-danger");
    }
}


function setOutOfStock() {
    var size = $("input[name=productStock]").length;
    var productIdList = [];

    if (size > 0) {
        for (i = 0; i < size; i++) {
            if ($("input[name=productStock]").eq(i).val() === '0') {
                productIdList.push(i);
            }
        }

        $.ajax({
            url: "/admin/setOutOfStock",
            type: "get",
            data: {productIdList: productIdList},
            success: function () {
            }
        })
    }
}

//스크롤 위치를 따라 이동하는 사이드바 구현
function moveSideBar() {
    $('.float_sidebar').css('position', 'relative').css('z-index', '1');

    $(window).scroll(function () {
        yPosition = $(window).scrollTop();  //스크롤의 현재 위치
        if (yPosition < 0) {
            yPosition = 0;
        }
        $('.float_sidebar').animate({"top": yPosition}, {duration: 700, easing: 'linear', queue: false});
    });
}

//페이지 내 이동 구현
function movePage() {
    $('#goInquiryManage').click(function () {
        var offset = $('#inquiryManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });

    $('#goReportManage').click(function () {
        var offset = $('#reportManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });

    $('#goProductManage').click(function () {
        var offset = $('#productManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });

    $('#goCommunityManage').click(function () {
        var offset = $('#communityManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });

    $('#goMemberManage').click(function () {
        var offset = $('#memberManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });
}

//신고사유 모달
function getReportReason(targetId, type) {
    $.getJSON("/admin/getReportReason/" + targetId + "/" + type, function (data) {
        //클릭한 승인버튼에 맞는 신고 사유를 모달에 출력함 (이하 2줄)
        $("#reportReason").val(data.report.reportReason);
        $("#reportDetailReason").text(data.report.reportReasonDetail);

        //히든 값으로 리포트아이디, 타입, 신고대상의 아이디를 모달에 넘겨줌 (이하 8줄)
        $("#modalReportId").val(data.report.reportId);
        $("#modalTargetType").val(type);

        if (type === "product" || type === "community") {
            $("#modalTargetId").val(data.report.targetLongId);
        } else {
            $("#modalTargetId").val(data.report.targetStringId);
        }
    });
}

//사업자 정보 불러오는 모달
function getCompanyInfo(companyId){
    $.getJSON("/getCompanyInfo/" + companyId, function(data){
        $("#modalCompanyName").val(data.companyName);
        $("#modalCompanyId").val(data.companyId);
        $("#modalCompanyPhoneNum").val(data.companyPhoneNum);
        $("#modalCompanyEmail").val(data.companyEmail);
        $("#modalCompanyNumber").val(data.companyNumber);
    });
}