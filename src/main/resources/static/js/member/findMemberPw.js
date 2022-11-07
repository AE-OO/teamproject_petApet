//form input value 값 ,,, 전역으로 사용하기위해 function 선언함
memberId = () => $("#input-memberId").val();
memberName = () => $("#input-memberName").val();
memberPhoneNum = () => $("#input-memberPhoneNum").val();
smsConfirmNum = () => $("#input-smsConfirmNum");
email = () => $("#input-email").val();

//error 메세지 (input 밑에 있는 p태그) 전역으로 사용하기위해 function 선언함
mIdFeedback = () => $("#feedback-memberId");
mNameFeedback = () => $("#feedback-memberName");
mPhoneNumFeedback = () => $("#feedback-memberPhoneNum");
smsConfirmNumFeedback = () => $("#feedback-smsConfirmPhoneNum");
emailFeedback = () => $("#feedback-email");

//아이디 정규식(영문 소문자, 숫자, 특수문자(-,_)  5-20 글자만 가능)
const mIdRegExp = /^[a-z0-9_-]{5,20}$/;
//이름 정규식 (한글, 2-6글자)
const mNameRegExp = /^[가-힣]{2,6}$/;
//휴대전화 정규식 (-빼고 입력 01로 시작, 총 10-11글자)
const mPhoneNumRegExp = /^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$/;
const emailRegExp = /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;

//인증시간 변수
let timer = null;
let certificationNum = null;
const leftSec = 180; // 제한시간(초)
// const leftSec = 10; // 제한시간(초)

//인증시간 타이머 함수
function startTimer(count) {
    smsConfirmNum().attr("disabled", false);
    var minutes, seconds;
    timer = setInterval(function () {
        // $("#smsBtn").text("인증번호 받기");
        minutes = parseInt(count / 60, 10);
        seconds = parseInt(count % 60, 10);
        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        mPhoneNumFeedback().text(minutes + ":" + seconds + " 안에 인증번호를 입력해주세요.");
        if (--count < 0) {// 타이머 끝
            // alert("인증시간이 초과되었습니다. 인증번호를 다시 전송해주세요.");
            mPhoneNumFeedback().text("");
            smsConfirmNumFeedback().text("인증시간이 초과되었습니다. 인증번호를 다시 전송해주세요.");
            smsConfirmNum().attr("disabled", true);
            $("#input-memberPhoneNum").attr("readonly", false);
            clearInterval(timer);
            return;
        }
        if (checkSmsConfirmNum()) {
            smsConfirmNum().attr("readonly", true);
            mPhoneNumFeedback().text("");
            alert("인증이 완료되었습니다.");
            smsConfirmNumFeedback().text("인증이 완료되었습니다.");
            $("#input-memberPhoneNum").attr("readonly", true);
            $("#smsBtn").attr("disabled", true);
            clearInterval(timer);
            timer == null;
            return;
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
    //         to: memberPhoneNum(),
    //     },
    //     dataType: "json",
    //     success: function (response) {
    //         $("#staticBackdrop").modal();
    //         alert("인증번호를 발송했습니다. 인증번호가 오지 않으면 입력하신 번호가 맞는지 확인해 주세요.");
    //         startTimer(leftSec); // 타이머 시작
    //         alert(response);
    //         return certificationNum = response;
    //     },
    //     error: function () {
    //         alert("인증번호 발송 실패");
    //         location.href="/findMemberPw"
    //     }
    // });
    //////////////////////////테스트용////////////////////////////
    $.ajax({
        type: "POST",
        url: "/sms/test",
        data: {
            to: memberPhoneNum(),
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
            window.location = "/findMemberPw"
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
                window.location = "/findMemberPw"
            }
        });
        return checkResult;
    }
}

$(document).ready(function () {
    let memberIdResult = false;
    let memberNameResult = false;
    let memberPhoneNumResult = false;
    let emailResult = false;

    // 아이디 체크
    $("#input-memberId").blur(function () {
        if (memberId() === null || memberId() === "") { //값이 없을 때
            mIdFeedback().text("필수 정보입니다.");
            return memberIdResult = false;
        } else if (!(mIdRegExp.test(memberId()))) { //정규식에 맞지 않을 때
            mIdFeedback().text("5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.");
            return memberIdResult = false;
        } else {
            //ajax 아이디 중복 확인
            $.ajax({
                type: "POST",
                url: "/checkId",
                data: {
                    memberId: memberId(),
                },
                dataType: "json",
                success: function (check) { // 통신 성공 시 "true" 혹은 "false" 반환
                    if (check) { // 아이디 이미 존재
                        mIdFeedback().text("");
                        return memberIdResult = true;
                    } else {//조건에 맞을 때
                        mIdFeedback().text("존재하지 않는 아이디입니다.");
                        return memberIdResult = false;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/findMemberPw";
                }

            });

        }
    });

    // 이름 체크
    $("#input-memberName").blur(function () {
        if (memberName() === null || memberName() === "") { //값이 없을 때
            mNameFeedback().text("필수 정보입니다.");
            return memberNameResult = false;
        } else if (!(mNameRegExp.test(memberName()))) { //정규식에 맞지 않을 때
            mNameFeedback().text("한글을 사용하세요. (특수기호, 공백 사용 불가)");
            return memberNameResult = false;
        } else { // 조건에 맞을 때
            mNameFeedback().text("");
            return memberNameResult = true;
        }
    });

    //휴대전화 체크
    $("#input-memberPhoneNum").blur(function () {
        if (memberPhoneNum() === null || memberPhoneNum() === "") {
            mPhoneNumFeedback().text("인증이 필요합니다.");
            return memberPhoneNumResult = false;
        } else if (!(mPhoneNumRegExp.test(memberPhoneNum()))) {
            mPhoneNumFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
            return memberPhoneNumResult = false;
        } else {
            mPhoneNumFeedback().text("");
            return memberPhoneNumResult = true;
        }
    });

    // 인증번호 체크
    $("#input-smsConfirmNum").blur(function () {
        checkSmsConfirmNum();
    });

    $("#smsBtn").click(function () {
        if (memberPhoneNum() === null || memberPhoneNum() === "") { //값이 없을 때
            mPhoneNumFeedback().text("인증이 필요합니다.");
        } else if (!(mPhoneNumRegExp.test(memberPhoneNum()))) { //정규식에 맞지 않을 때
            mPhoneNumFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
        } else {
            sendBtnClick();
        }
    });

    // 비밀번호 찾기
    $("#findBtn").click(function () {
        if (memberId() === null || memberId() === "") {
            mIdFeedback().text("필수 정보입니다.");
        }
        if (memberName() === null || memberName() === "") {
            mNameFeedback().text("필수 정보입니다.");
        }
        if (memberPhoneNum() === null || memberPhoneNum() === "") { //값이 없을 때
            mPhoneNumFeedback().text("인증이 필요합니다.");
        }
        if (smsConfirmNum().val() === null || smsConfirmNum().val() === "") {
            mPhoneNumFeedback().text("인증이 필요합니다.");
        }

        if (memberIdResult && memberNameResult && memberPhoneNumResult && checkSmsConfirmNum()) {
            $("#findBtn").attr("type", "submit");
        }
    });

    if ($("#findMemberPwResult").val().length > 0) {
        $("#staticBackdrop").modal();
    }

    $("#input-email").blur(function () {
        if (email() === null || email() === "") { //값이 없을 때
            emailFeedback().text("필수 정보입니다.");
            return emailResult = false;
        } else if (!(emailRegExp.test(email()))) { //정규식에 맞지 않을 때
            emailFeedback().text("이메일주소를 정확하게 입력해주세요. ex)aaa@petapet.com");
            return emailResult = false;
        } else { // 조건에 맞을 때
            emailFeedback().text("");
            return emailResult = true;
        }
    });

    $("#sendEmailBtn").click(function () {
        if (emailResult) {
            $.ajax({
                type: "POST",
                url: "email/send",
                data: {
                    email: email(),
                    memberId: $("#findMemberPwResult").val()
                },
                dataType: "json",
                success: function (data) {
                    if(data){
                        alert("임시비밀번호를 발송하였습니다.");
                        window.location = "/login";
                    }else{
                        alert("임시비밀번호 발송에 실패했습니다.");
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/findMemberPw";
                }
            });
        }
    });

});
