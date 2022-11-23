companyPw = () => $("#input-companyPw");
cPwFeedback = () => $("#feedback-companyPw");

$(document).ready(function () {
    companyPw().keyup(function () {
        if (companyPw().val() == "") { //PW란이 비어있다면
            cPwFeedback().text("비밀번호를 입력해주세요.");
        } else {
            cPwFeedback().text("");
        }
    });
    $("#confirmBtn").click(function () {
        if (companyPw().val() == "") {
            $("#staticBackdropLabel").text("비밀번호를 입력해주세요");
            $("#staticBackdrop").modal('show');
        } else {
            $.ajax({
                type: "POST",
                url: "/checkCompanyPw",
                data: {
                    companyPw: companyPw().val()
                },
                dataType: "json",
                success: function (check) {
                    if (check) {
                        $("#sendCompanyId").submit();
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