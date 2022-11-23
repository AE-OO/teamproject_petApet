//form input value 값 ,,, 전역으로 사용하기위해 function 선언함
originalCompanyPw = () => $("#input-originalCompanyPw").val();
newCompanyPw = () => $("#input-newCompanyPw").val();
newCompanyPw2 = () => $("#input-newCompanyPw2").val();
companyName = () => $("#input-companyName").val();
companyNumber = () => $("#input-companyNumber").val();
companyEmail = () => $("#input-companyEmail").val();
companyPhoneNum = () => $("#input-companyPhoneNum").val();
smsConfirmNum = () => $("#input-smsConfirmNum");

//error 메세지 (input 밑에 있는 p태그) 전역으로 사용하기위해 function 선언함
oPwFeedback = () => $("#feedback-originalCompanyPw");
cNewPwFeedback = () => $("#feedback-newCompanyPw");
cNewPwFeedback2 = () => $("#feedback-newCompanyPw2");
cNameFeedback = () => $("#feedback-companyName");
cNumberFeedback = () => $("#feedback-companyNumber");
cEmailFeedback = () => $("#feedback-companyEmail");
cPhoneNumFeedback = () => $("#feedback-companyPhoneNum");
smsConfirmNumFeedback = () => $("#feedback-smsConfirmNum");
smsConfirmNumFeedback2 = () => $("#feedback-smsConfirmNum2");

//비밀번호 정규식 (영어 대소문자,특수문자,숫자 필수입력, 8-16글자)
const mPwRegExp = /^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
//휴대전화 정규식 (-빼고 입력 01로 시작, 총 10-11글자)
const mPhoneumRegExp = /^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$/;
//숫자 정규식 (길이 상관 없이 숫자만 입력)
const numRegExp = /^[0-9]+$/;

//현재 비밀번호 체크
function checkOriginalCompanyPw() {
    if (originalCompanyPw() === null || originalCompanyPw() === "") {
        oPwFeedback().text("필수 정보입니다.");
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: "/checkCompanyPw",
            data: {
                companyPw: originalCompanyPw()
            },
            dataType: "json",
            async: false,
            success: function (check) {
                if (check) {
                    oPwFeedback().text("");
                    $("#input-newCompanyPw").attr("disabled", false);
                    $("#input-newCompanyPw2").attr("disabled", false);
                    return isSuccess = true;
                } else {
                    oPwFeedback().text("현재 비밀번호와 일치하지 않습니다. ");
                    return isSuccess = false;
                }
            },
            error: function () {
                alert("통신 오류");
                window.location = "/company/info";
            }
        });
        return isSuccess;
    }
}

//새 비밀번호 체크
function checkNewCompanyPw() {
    if (newCompanyPw() === null || newCompanyPw() === "") { //값이 없을 때
        cNewPwFeedback().text("필수 정보입니다.");
        return false;
    } else if (newCompanyPw() === originalCompanyPw()) {
        cNewPwFeedback().text("현재 비밀번호와 동일합니다.");
        return false;
    } else if (!(mPwRegExp.test(newCompanyPw()))) { //정규식에 맞지 않을 때
        cNewPwFeedback().text("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
        return false;
    } else {
        cNewPwFeedback().text("");
        return true;
    }

    if (!(newCompanyPw() === newCompanyPw2())) {
        cNewPwFeedback2().text("비밀번호가 일치하지 않습니다.");
        return false;
    }
}

//새 비밀번호 확인 체크
function checkNewCompanyPw2() {
    if (newCompanyPw2() == null || newCompanyPw2() === "") { //값 없을 때
        cNewPwFeedback2().text("필수 정보입니다.");
        return false;
    } else if (!(newCompanyPw2() === newCompanyPw())) { //값이 같지 않을 때
        cNewPwFeedback2().text("비밀번호가 일치하지 않습니다.");
        return false;
    } else if (newCompanyPw2() === newCompanyPw2()) {
        cNewPwFeedback2().text("");
        return true;
    }
}

//휴대전화 체크
function checkCompanyPhoneNum() {
    if (companyPhoneNum() === null || companyPhoneNum() === "") {
        $("#smsBtn").attr("disabled", true);
        cPhoneNumFeedback().text("휴대폰 번호를 입력해주세요.");
        return false;
    }
    if (!(mPhoneumRegExp.test(companyPhoneNum()))) {
        cPhoneNumFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
        $("#smsBtn").attr("disabled", true);
        return false;
    }
    cPhoneNumFeedback().text("");
    $("#smsBtn").attr("disabled", false);
    return true;

}

//인증시간 변수
let timer = null;
let certificationNum = null;
const leftSec = 180; // 제한시간(초)
// const leftSec = 10; // 제한시간(초)

//인증시간 타이머 함수
function startTimer(count) {
    smsConfirmNumFeedback().text("");
    smsConfirmNumFeedback2().text("");
    var minutes, seconds;
    timer = setInterval(function () {
        // $("#smsBtn").text("인증번호 받기");
        minutes = parseInt(count / 60, 10);
        seconds = parseInt(count % 60, 10);
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        smsConfirmNumFeedback().text(minutes + ":" + seconds + " 안에 인증번호를 입력해주세요.");
        if (--count < 0) {// 타이머 끝
            // alert("인증시간이 초과되었습니다. 인증번호를 다시 전송해주세요.");
            smsConfirmNumFeedback().text("인증시간이 초과되었습니다. 인증번호를 다시 전송해주세요.");
            smsConfirmNumFeedback2().text("");
            smsConfirmNum().attr("disabled", true);
            $("#input-companyPhoneNum").attr("readonly", false);
            $("#smsBtn").attr("disabled", false);
            clearInterval(timer);

            return;
        }
        if (checkSmsConfirmNum()) {
            smsConfirmNum().attr("disabled", true);
            smsConfirmNumFeedback().text("인증이 완료되었습니다.");
            $("#input-companyPhoneNum").attr("readonly", true);
            smsConfirmNumFeedback2().text("");
            $("#smsBtnName").text("다른번호 인증");
            clearInterval(timer);
            timer == null;
            return;
        }
        $('#staticBackdrop').on('hidden.bs.modal', function (e) {
            clearInterval(timer);
            timer == null;

        });
    }, 1000);
}

//인증번호 발송용
function sendBtnClick() {
    //////////////////////////////////////////////////////////////////////////////////////////////
    // 휴대전화 체크 (인증번호 문자포함) - 문자서비스 막아놓음 사용하려면 /////////ajax 주석 살리기 + 테스트 주석처리//////
    //////////////////////////////////////////////////////////////////////////////////////////////
    // $.ajax({
    //     type: "POST",
    //     url: "/sms/send",
    //     data: {
    //         to: companyPhoneNum(),
    //     },
    //     dataType: "json",
    //     success: function (response) {
    //         $("#staticBackdrop").modal('show');
    //         alert("인증번호를 발송했습니다. 인증번호가 오지 않으면 입력하신 번호가 맞는지 확인해 주세요.");
    //         startTimer(leftSec); // 타이머 시작
    //         alert(response);
    //         return certificationNum = response;
    //     },
    //     error: function () {
    //         alert("인증번호 발송 실패");
    //         location.href="/join"
    //     }
    // });

    //////////////////////////테스트용////////////////////////////
    $.ajax({
        type: "POST",
        url: "/sms/test",
        data: {
            to: companyPhoneNum(),
        },
        dataType: "json",
        success: function (response) {
            $("#staticBackdrop").modal('show');
            alert("인증번호를 발송했습니다. 인증번호가 오지 않으면 입력하신 번호가 맞는지 확인해 주세요.");
            startTimer(leftSec); // 타이머 시작
            alert(response);
            return certificationNum = response;
        },
        error: function () {
            alert("인증번호 발송 실패");
            window.location = "/company/info"
        }
    });
    //////////////////////////테스트용////////////////////////////
}

//인증번호 체크
function checkSmsConfirmNum() {
    if (smsConfirmNum().val() === null || smsConfirmNum().val() === "") {
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: "/checkSmsConfirmNum",
            data: {
                smsConfirmNum: smsConfirmNum().val()
            },
            dataType: "json",
            success: function (check) {
                if (check) {
                    checkResult = true;
                } else {
                    smsConfirmNumFeedback2().text("인증번호가 일치하지 않습니다. 인증번호를 다시 확인해주세요.");
                    checkResult = false;
                }
            }, error: function () {
                alert("통신오류");
                window.location = "/company/info"
            }
        });
        return checkResult;
    }
}

$(document).ready(function () {
    $("#updateCompanyPwBtn").click(function () {
        $("#updateCompanyPw").modal('show');
    })
    $("#sendNewCompanyPwBtn").click(function () {
        if (checkOriginalCompanyPw() && checkNewCompanyPw() && checkNewMemberPw2()) {
            $.ajax({
                type: "POST",
                url: "/updateMemberPw",
                data: JSON.stringify({
                    originalMemberPw: originalMemberPw(),
                    newMemberPw: newMemberPw(),
                    newMemberPw2: newMemberPw2()
                }),
                contentType: 'application/json',
                dataType: "json",
                success: function (check) {
                    if (1) {
                        alert("비밀번호 변경이 완료되었습니다");
                    } else {
                        alert("비밀번호 변경에 실패했습니다.");
                    }
                    $("#input-originalMemberPw").val("");
                    $("#input-newMemberPw").val("");
                    $("#input-newMemberPw2").val("");
                    oPwFeedback().text("");
                    mNewPwFeedback().text("");
                    mNewPwFeedback2().text("");
                    $("#input-newMemberPw").attr("disabled", true);
                    $("#input-newMemberPw2").attr("disabled", true);
                    $("#updateMemberPw").modal('hide');
                },
                error: function () {
                    alert("통신 오류");
                    window.location = "/member/checkInfo";
                }
            });
        }
    });
    $("#closeBtn").click(function () {
        $("#input-originalMemberPw").val("");
        $("#input-newMemberPw").val("");
        $("#input-newMemberPw2").val("");
        oPwFeedback().text("");
        mNewPwFeedback().text("");
        mNewPwFeedback2().text("");
        $("#input-newMemberPw").attr("disabled", true);
        $("#input-newMemberPw2").attr("disabled", true);
    });

    //인증번호 버튼
    $("#smsBtn").click(function () {
        if (checkMemberPhoneNum() && ($("#smsBtnName").text() == "인증번호 받기")) {
            smsConfirmNum().attr("disabled", false);
            smsConfirmNum().val("");
            sendBtnClick();
        }

        if ($("#smsBtnName").text() == "다른번호 인증") {
            $("#smsBtnName").text("인증번호 받기");
            $("#input-memberPhoneNum").val("");
            $("#smsBtn").attr("disabled", true);
            $("#input-memberPhoneNum").attr("readonly", false);
        }
    });

    // 회원정보 수정버튼 - 모든 조건이 만족할 때 submit됨
    $("#modifyBtn").click(function () {
        if (memberPostCode() === null || memberPostCode() === "") { //값이 없을 때
            mAddrFeedback().text("필수 정보입니다.");

        } else if (memberDetailAddress() === "") {
            mAddrFeedback().text("상세 주소를 입력해주세요.");
        }

        if (memberPhoneNum() === null || memberPhoneNum() === "") { //값이 없을 때
            mPhoneNumFeedback().text("인증이 필요합니다.");
        }
        if (!checkMemberBirthday()) {
            mBirthFeedback().text("필수 정보입니다.");
        }

        // alert(checkMemberBirthday()+"///"+checkMemberAddress()+"///"+checkMemberPhoneNum()+"///")
        if (checkMemberBirthday() && checkMemberAddress() && checkMemberPhoneNum()) {
            alert("수정이 완료되었습니다.");
            $("#modifyBtn").attr("type", "submit");
        }
        if (smsConfirmNum().val().length > 0) {
            if (checkMemberBirthday() && checkMemberAddress() && checkMemberPhoneNum() && checkSmsConfirmNum()) {
                alert("수정이 완료되었습니다.");
                $("#modifyBtn").attr("type", "submit");
            }
        }

    });

    // 회원탈퇴 버튼
    $("#withdrawalBtn").click(function () {
        window.location = "/member/withdrawal"
    });
});
