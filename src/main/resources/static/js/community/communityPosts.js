$(function () {
    //되돌아가기, 글목록 버튼
    $("#communityBtn").click(function () {
        // history.go(-1);
        window.location = "/community";
    });
    //로그인 버튼
    $("#loginBtn").click(function () {
        window.location = "/login";
    });
    //회원가입 버튼
    $("#joinBtn").click(function () {
        window.location = "/join";
    });
    //게시물 삭제버튼
    $("#postsDelete").click(function () {
        let arr = [];
        if (confirm("정말로 삭제하시겠습니까?") == true) {
            $("#postsContent").find("img").each(function () {
                arr.push($(this).attr('src').substring(16))
            });
            $.ajax({
                type: "POST",
                url: "/community/delete",
                data: {
                    communityId: $(location).attr('pathname').split("/")[2],
                    deleteImg: arr,
                },
                dataType: "json",
                success: function () {
                    alert("삭제가 완료되었습니다.");
                    window.location = "/community";
                },
                // error: function () {
                //     alert("오류가 발생했습니다.");
                // },
                complete: function (data) {
                    alert("삭제가 완료되었습니다.");
                    window.location = "/community";
                    //  실패했어도 완료가 되었을 때 처리
                }
            });
        } else {
            return;
        }

    });
    //게시물 수정버튼
    $("#postsUpdate").click(function () {
        let form = document.createElement('form');
        let communityId;
        communityId = document.createElement('input');
        communityId.setAttribute('type', 'hidden');
        communityId.setAttribute('name', 'communityId');
        communityId.setAttribute('value', $(location).attr('pathname').split("/")[2]);
        form.appendChild(communityId);
        form.setAttribute('method', 'post');
        form.setAttribute('action', '/community/update');
        document.body.appendChild(form);
        form.submit();
    });
});
