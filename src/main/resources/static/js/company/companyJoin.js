//form input value 값 ,,, 전역으로 사용하기위해 function 선언함
companyId = () => $("#input-companyId").val();
companyPw = () => $("#input-companyPw").val();
companyPw2 = () => $("#input-companyPw2").val();
companyName = () => $("#input-companyName").val();
companyEmail = () => $("#input-companyEmail").val();
companyNumber = () => $("#input-companyNumber").val();
companyPhoneNum = () => $("#input-companyPhoneNum").val();
smsConfirmNum = () => $("#input-smsConfirmNum");

//error 메세지 (input 밑에 있는 p태그) 전역으로 사용하기위해 function 선언함
cIdFeedback = () => $("#feedback-companyId");
cPwFeedback = () => $("#feedback-companyPw");
cPwFeedback2 = () => $("#feedback-companyPw2");
cNameFeedback = () => $("#feedback-companyName");
cEmailFeedback = () => $("#feedback-companyEmail");
cNumberFeedback = () => $("#feedback-companyNumber");
cPhoneNumFeedback = () => $("#feedback-companyPhoneNum");
smsConfirmNumFeedback = () => $("#feedback-smsConfirmPhoneNum");

//error메세지 text 색상 변경용
function textInfo(value) {
    if ((value.hasClass("text-info"))) {
        return;
    } else {
        value.removeClass("text-danger").addClass("text-info");
    }
}

function textDanger(value) {
    if (value.hasClass("text-danger")) {
        return;
    } else {
        value.removeClass("text-info").addClass("text-danger");
    }
}

//인증시간 변수
let timer = null;
let certificationNum = null;
const leftSec = 180; // 제한시간(초)
//인증시간 타이머 함수
function startTimer(count) {
    $("#smsBtn").attr("disabled", true);
    smsConfirmNum().attr("disabled", false);
    $("#input-companyPhoneNum").attr("readonly", true);
    var minutes, seconds;
    timer = setInterval(function () {
        minutes = parseInt(count / 60, 10);
        seconds = parseInt(count % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        cPhoneNumFeedback().text(minutes + ":" + seconds + " 안에 인증번호를 입력해주세요.");
        // 타이머 끝
        if (--count < 0) {
            clearInterval(timer);
            alert("인증시간이 초과되었습니다. 인증번호를 다시 전송해주세요.");
            $("#input-companyPhoneNum").attr("readonly", false);
            cPhoneNumFeedback().text("");
            $("#smsBtn").attr("disabled", false);
            smsConfirmNum().val("");
            smsConfirmNumFeedback().text("");
            smsConfirmNum().attr("disabled", true);
            clearInterval(timer);
            return;
        }
        // 인증번호 맞으면 종료
        if (checkSmsConfirmNum()) {
            alert("인증이 완료되었습니다.");
            cPhoneNumFeedback().text("");
            textInfo(smsConfirmNumFeedback());
            smsConfirmNumFeedback().text("OK");
            smsConfirmNum().attr("readonly", true);
            clearInterval(timer);
            timer == null;
        }
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
    //         alert("인증번호를 발송했습니다. 인증번호가 오지 않으면 입력하신 번호가 맞는지 확인해 주세요.");
    //         startTimer(leftSec); // 타이머 시작
    //         alert(response);
    //         return certificationNum = response;
    //     },
    //     error: function () {
    //         alert("인증번호 발송 실패");
    //         location.href="/companyJoin"
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
            alert("인증번호를 발송했습니다. 인증번호가 오지 않으면 입력하신 번호가 맞는지 확인해 주세요.");
            startTimer(leftSec); // 타이머 시작
            alert(response);
            return certificationNum = response;
        },
        error: function () {
            alert("인증번호 발송 실패");
            window.location = "/companyJoin"
        }
    });
    //////////////////////////테스트용////////////////////////////
}

//인증번호 체크
function checkSmsConfirmNum() {
    if (smsConfirmNum().val() === null || smsConfirmNum().val() === "") {
        smsConfirmNumFeedback().text("");
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
                    // alert("인증이 완료되었습니다.");
                    // $("#input-smsConfirmNum").attr("readonly", true);
                    checkResult = true;
                } else {
                    smsConfirmNumFeedback().text("인증번호가 일치하지 않습니다. 인증번호를 다시 확인해주세요.");
                    checkResult = false;
                }
            }, error: function () {
                alert("통신오류");
                window.location = "/companyJoin"
            }
        });
        return checkResult;
    }
}

$(document).ready(function () {
    let companyIdResult = false;
    let companyPwResult = false;
    let companyPwResult2 = false;
    let companyNameResult = false;
    let companyNumberResult = false;
    let companyEmailResult = false;
    let companyPhoneNumResult = false;

    // 아이디 체크
    $("#input-companyId").blur(function () {
        if (companyId() === null || companyId() === "") { //값이 없을 때
            textDanger(cIdFeedback());
            cIdFeedback().text("필수 정보입니다.");
            return companyIdResult = false;
        } else if (!(idRegExp.test(companyId()))) { //정규식에 맞지 않을 때
            textDanger(cIdFeedback());
            cIdFeedback().text("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
            return companyIdResult = false;
        } else {
            //ajax 아이디 중복 확인
            $.ajax({
                type: "POST",
                url: "/checkCompanyId",
                data: {
                    companyId: companyId(),
                },
                dataType: "json",
                success: function (check) { // 통신 성공 시 "true" 혹은 "false" 반환
                    if (check) { // 아이디 이미 존재
                        textDanger(cIdFeedback());
                        cIdFeedback().text("중복 아이디입니다.");
                        return companyIdResult = false;
                    } else {//조건에 맞을 때
                        textInfo(cIdFeedback());
                        cIdFeedback().text("OK");
                        return companyIdResult = true;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/companyJoin";
                }
            });
        }
    });
    // 비밀번호 체크
    $("#input-companyPw").blur(function () {
        textDanger(cPwFeedback());
        if (companyPw() === null || companyPw() === "") { //값이 없을 때
            cPwFeedback().text("필수 정보입니다.");
            companyPwResult = false;
        } else if (!(pwRegExp.test(companyPw()))) { //정규식에 맞지 않을 때
            cPwFeedback().text("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
            companyPwResult = false;
        } else { // 조건에 맞을 때
            textInfo(cPwFeedback());
            cPwFeedback().text("OK");
            companyPwResult = true;
        }
        if (companyPwResult2) {
            if (!(companyPw() === companyPw2())) {
                textDanger(cPwFeedback2());
                cPwFeedback2().text("비밀번호가 일치하지 않습니다.");
                companyPwResult2 = false;
            }
        }
    });
    // 비밀번호 확인 체크
    $("#input-companyPw2").blur(function () {
        textDanger(cPwFeedback2());
        if (companyPw2() == null || companyPw2() === "") { //값 없을 때
            cPwFeedback2().text("필수 정보입니다.");
            return companyPwResult2 = false;
        } else if (!(companyPw2() === companyPw())) { //값이 같지 않을 때
            cPwFeedback2().text("비밀번호가 일치하지 않습니다.");
            return companyPwResult2 = false;
        } else { //일치할 때
            textInfo(cPwFeedback2());
            cPwFeedback2().text("OK");
            return companyPwResult2 = true;
        }
    });
    // 상호명 체크
    $("#input-companyName").blur(function () {
        textDanger(cNameFeedback());
        if (companyName() === null || companyName() === "") { //값이 없을 때
            cNameFeedback().text("필수 정보입니다.");
            return companyNameResult = false;
        } else if (!(cNameRegExp.test(companyName()))) { //정규식에 맞지 않을 때
            cNameFeedback().text("한글 또는 영문만 20자까지 입력 가능합니다.");
            return companyNameResult = false;
        } else { // 조건에 맞을 때
            textInfo(cNameFeedback());
            cNameFeedback().text("OK");
            return companyNameResult = true;
        }
    });

    // 사업자 번호 체크
    $("#input-companyNumber").blur(function () {
        textDanger(cNumberFeedback());
        if (companyNumber() === null || companyNumber() === "") { //값이 없을 때
            cNumberFeedback().text("필수 정보입니다.");
            return companyNumberResult = false;
        } else if (!(cNumberRegExp.test(companyNumber()))) { //정규식에 맞지 않을 때
            cNumberFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
            return companyNumberResult = false;
        } else {
            $.ajax({
                type: "POST",
                url: "/checkCompanyNumber",
                data: {
                    companyNumber: companyNumber(),
                },
                dataType: "json",
                success: function (check) {
                    if (check) { // 이미 존재
                        textDanger(cNumberFeedback());
                        cNumberFeedback().text("이미 가입된 사업자번호입니다.");
                        return companyNumberResult = false;
                    } else {//조건에 맞을 때
                        textInfo(cNumberFeedback());
                        cNumberFeedback().text("OK");
                        return companyNumberResult = true;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/companyJoin";
                }
            });
        }
    });

    $("#input-companyEmail").blur(function () {
        if (companyEmail() === null || companyEmail() === "") { //값이 없을 때
            textDanger(cEmailFeedback());
            cEmailFeedback().text("필수 정보입니다.");
            return companyEmailResult = false;
        } else if (!(emailRegExp.test(companyEmail()))) { //정규식에 맞지 않을 때
            textDanger(cEmailFeedback());
            cEmailFeedback().text("이메일을 올바르게 입력해주세요. (예: petapet@pet.com)");
            return companyEmailResult = false;
        } else {
            //ajax 아이디 중복 확인
            $.ajax({
                type: "POST",
                url: "/checkCompanyEmail",
                data: {
                    companyEmail: companyEmail(),
                },
                dataType: "json",
                success: function (check) { // 통신 성공 시 "true" 혹은 "false" 반환
                    if (check) { // 아이디 이미 존재
                        textDanger(cEmailFeedback());
                        cEmailFeedback().text("이미 가입된 이메일 주소입니다. 다른 이메일을 입력하여 주세요.");
                        return companyEmailResult = false;
                    } else {//조건에 맞을 때
                        textInfo(cEmailFeedback());
                        cEmailFeedback().text("OK");
                        return companyEmailResult = true;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/companyJoin";
                }
            });
        }
    });

    //휴대전화 체크
    $("#input-companyPhoneNum").blur(function () {
        textDanger(cPhoneNumFeedback());
        if (companyPhoneNum() === null || companyPhoneNum() === "") {
            cPhoneNumFeedback().text("인증이 필요합니다.");
            return companyPhoneNumResult = false;
        } else if (!(phoneNumRegExp.test(companyPhoneNum()))) {
            cPhoneNumFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
            return companyPhoneNumResult = false;
        } else {
            $.ajax({
                type: "POST",
                url: "/checkCompanyPhoneNum",
                data: {
                    companyPhoneNum: companyPhoneNum(),
                },
                dataType: "json",
                success: function (check) { // 통신 성공 시 "true" 혹은 "false" 반환
                    if (check) {
                        cPhoneNumFeedback().text("이미 가입된 휴대폰 번호입니다.");
                        return companyPhoneNumResult = false;
                    } else {//조건에 맞을 때
                        cPhoneNumFeedback().text("");
                        return companyPhoneNumResult = true;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/companyJoin";
                }
            })
        }
    });

    // 인증번호 체크
    $("#input-smsConfirmNum").blur(function () {
        checkSmsConfirmNum();
    });

    $("#smsBtn").click(function () {
        textDanger(cPhoneNumFeedback());
        if (companyPhoneNum() === null || companyPhoneNum() === "") { //값이 없을 때
            cPhoneNumFeedback().text("인증이 필요합니다.");
        } else if (!(phoneNumRegExp.test(companyPhoneNum()))) { //정규식에 맞지 않을 때
            cPhoneNumFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
        } else {
            $.ajax({
                type: "POST",
                url: "/checkCompanyPhoneNum",
                data: {
                    companyPhoneNum: companyPhoneNum(),
                },
                dataType: "json",
                success: function (check) { // 통신 성공 시 "true" 혹은 "false" 반환
                    if (check) {
                        cPhoneNumFeedback().text("이미 가입된 휴대폰 번호입니다.");
                    } else {//조건에 맞을 때
                        cPhoneNumFeedback().text("");
                        sendBtnClick();
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/companyJoin";
                }
            })
        }
    });

    // 회원가입 버튼 - 모든 조건이 만족할 때 submit됨
    $("#joinBtn").click(function () {
        if (companyId() === null || companyId() === "") {
            cIdFeedback().text("필수 정보입니다.");
        }
        if (companyPw() === null || companyPw() === "") {
            cPwFeedback().text("필수 정보입니다.");
        }
        if (companyPw2() == null || companyPw2() === "") {
            cPwFeedback2().text("필수 정보입니다.");
        }
        if (companyName() === null || companyName() === "") {
            cNameFeedback().text("필수 정보입니다.");
        }
        if (companyNumber() === null || companyNumber() === "") {
            cNumberFeedback().text("필수 정보입니다.");
        }
        if (companyEmail() === null || companyEmail() === "") {
            cEmailFeedback().text("필수 정보입니다.");
        }
        if (companyPhoneNum() === null || companyPhoneNum() === "") {
            cPhoneNumFeedback().text("인증이 필요합니다.");
        }

        if (companyIdResult && companyPwResult && companyPwResult2 && companyNameResult &&
            companyNumberResult && companyEmailResult && companyPhoneNumResult && checkSmsConfirmNum()) {
            alert("회원가입 신청이 완료되었습니다. 관리자의 승인 완료 후 이용하실 수 있습니다.")
            $("#joinBtn").attr("type", "submit");
        }
    });


});
