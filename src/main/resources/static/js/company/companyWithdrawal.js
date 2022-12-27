companyPw = () => $("#input-companyPw");
cPwFeedback = () => $("#feedback-companyPw");

$(document).ready(function () {
    let companyPwResult = false
    companyPw().blur(function () {
        if (companyPw().val() == "") { //PW란이 비어있다면
            cPwFeedback().text("비밀번호를 입력해주세요.");
            return companyPwResult = false;
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
                        cPwFeedback().text("");
                        return companyPwResult = true;
                    } else {
                        cPwFeedback().text("비밀번호가 일치하지 않습니다.");
                        return companyPwResult = false;
                    }
                },
                error: function () {
                    alert("통신 오류")
                }
            });
        }
    });
    $("#withdrawalBtn").click(function () {
        if (companyPw().val() == "") {
            cPwFeedback().text("비밀번호를 입력해주세요.");
            companyPw().focus();
        }

        if (companyPwResult) {
            $("#staticBackdropLabel").text("정말로 탈퇴하시겠습니까?");
            $("#staticBackdrop").modal('show');
        }
    });

    $("#modalBtn").click(function () {
        $("form").submit();
    });

    $("#cancelBtn").click(function () {
        window.location = "/company/info"
    })
});
