$(document).ready(function () {
    reportColor();
    moveSideBar();
    movePage();
    setOutOfStock()

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
        var productStock = $(this).parent().parent().find("input[name=productStock]").val();

        $.ajax({
            url: "/admin/updateProductStatus/" + productId,
            type: "get",
            data: {status : selectedStatus, stock : productStock},
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

    $(".acceptProductReportBtn").click(function(){
        var reportId = $(this).parent().parent().find("td[id=productReportId]").text();
        var productId = $(this).parent().parent().find("td[id=productId]").text();

        $.ajax({
            url: "/admin/acceptProductReport/" + reportId,
            data: {productId : productId},
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

function setOutOfStock(){
    var size = $("input[name=productStock]").length;
    var productIdList = [];

    for(i = 0; i < size; i++){
        if($("input[name=productStock]").eq(i).val() === '0'){
            productIdList.push(i);
        }
    }
    
    $.ajax({
        url: "/admin/setOutOfStock",
        type: "get",
        data: {productIdList : productIdList},
        success: function () {
        }
    })
}

//스크롤 위치를 따라 이동하는 사이드바 구현
function moveSideBar(){
    $('.float_sidebar').css('position', 'relative').css('z-index', '1');

    $(window).scroll(function(){
        yPosition = $(window).scrollTop();  //스크롤의 현재 위치
        if (yPosition < 0) {
            yPosition = 0;
        }
        $('.float_sidebar').animate({"top":yPosition }, {duration: 700, easing: 'linear', queue:false});
    });
}

//페이지 내 이동 구현
function movePage(){
    $('#goInquiryManage').click(function(){
        var offset = $('#inquiryManage').offset();
        $('html').animate({scrollTop : offset.top}, 400);
    });

    $('#goReportManage').click(function(){
        var offset = $('#reportManage').offset();
        $('html').animate({scrollTop : offset.top}, 400);
    });

    $('#goProductManage').click(function(){
        var offset = $('#productManage').offset();
        $('html').animate({scrollTop : offset.top}, 400);
    });

    $('#goCommunityManage').click(function(){
        var offset = $('#communityManage').offset();
        $('html').animate({scrollTop : offset.top}, 400);
    });

    $('#goMemberManage').click(function(){
        var offset = $('#memberManage').offset();
        $('html').animate({scrollTop : offset.top}, 400);
    });
}