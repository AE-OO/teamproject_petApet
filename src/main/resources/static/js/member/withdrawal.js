memberPw = () => $("#input-memberPw");
mPwFeedback = () => $("#feedback-memberPw");

$(document).ready(function () {
    let memberPwResult = false
    memberPw().blur(function () {
        if (memberPw().val() == "") { //PW란이 비어있다면
            mPwFeedback().text("비밀번호를 입력해주세요.");
            return memberPwResult = false;
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
                        mPwFeedback().text("");
                        return memberPwResult = true;
                    } else {
                        mPwFeedback().text("비밀번호가 일치하지 않습니다.");
                        return memberPwResult = false;
                    }
                },
                error: function () {
                    alert("통신 오류")
                }
            });
        }
    });
    $("#withdrawalBtn").click(function () {
        if (memberPw().val() == "") {
            mPwFeedback().text("비밀번호를 입력해주세요.");
            memberPw().focus();
        }

        if (memberPwResult) {
            $("#staticBackdropLabel").text("정말로 탈퇴하시겠습니까?");
            $("#staticBackdrop").modal('show');
        }
    });

    $("#modalBtn").click(function () {
        $("form").submit();
    });

    $("#cancelBtn").click(function () {
        window.location = "/member/checkInfo"
    })
});
