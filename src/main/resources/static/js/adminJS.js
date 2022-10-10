$(document).ready(function () {
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

    $(".memberModal").click(function(){
        var memberId = $(this).attr("id");

        $(".memberDelete").empty();

        var modalBody = '';
        modalBody += '아이디 ' + memberId;
        modalBody += '를 삭제하겠습니까?<br/>확인을 누르면 삭제됩니다.<br/>';
        $(".memberDelete").append(modalBody);
        $(".confirmMemberDelete").attr("id",memberId);
        $("#confirmDelete").modal('show');
    });

    $(".confirmMemberDelete").click(function(){
       var memberId = $(this).attr("id");
       console.log(memberId);

       $.ajax({
           url: "/admin/deleteMember/" + memberId,
           type: "get",
           success(){
               location.href="/admin/adminPage";
           }
       })
    });
})