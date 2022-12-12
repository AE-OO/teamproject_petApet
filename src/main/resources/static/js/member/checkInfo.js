memberPw = () => $("#input-memberPw");
mPwFeedback = () => $("#feedback-memberPw");

$(document).ready(function () {
    memberPw().keyup(function () {
        if (memberPw().val() == "") { //PW란이 비어있다면
            mPwFeedback().text("비밀번호를 입력해주세요.");
        } else {
            mPwFeedback().text("");
        }
    });
    $("#confirmBtn").click(function () {
        if (memberPw().val() == "") {
            $("#staticBackdropLabel").text("비밀번호를 입력해주세요");
            $("#staticBackdrop").modal('show');
        } else {
            $.ajax({
                type: "POST",
                url: "/checkPw",
                data: {
                    memberPw: memberPw().val()
                },
                dataType: "json",
                success: function (check) {
                    if (check) {
                        $("#sendMemberId").submit();
                    } else {
                        $("#staticBackdropLabel").text("비밀번호가 일치하지 않습니다.");
                        $("#staticBackdrop").modal('show');
                    }
                },
                error: function () {
                    alert("통신 오류")
                }
            });
        }
    });
});