//form input value 값 ,,, 전역으로 사용하기위해 function 선언함
memberName = () => $("#input-memberName").val();
memberPhoneNum = () => $("#input-memberPhoneNum").val();
smsConfirmNum = () => $("#input-smsConfirmNum");
companyName = () => $("#input-companyName").val();
companyNumber = () => $("#input-companyNumber").val();

//error 메세지 (input 밑에 있는 p태그) 전역으로 사용하기위해 function 선언함
mNameFeedback = () => $("#feedback-memberName");
mPhoneNumFeedback = () => $("#feedback-memberPhoneNum");
smsConfirmNumFeedback = () => $("#feedback-smsConfirmPhoneNum");
cNameFeedback = () => $("#feedback-companyName");
cNumberFeedback = () => $("#feedback-companyNumber");

//이름 정규식 (한글, 2-6글자)
const mNameRegExp = /^[가-힣]{2,6}$/;
//휴대전화 정규식 (-빼고 입력 01로 시작, 총 10-11글자)
const mPhoneNumRegExp = /^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$/;
const cNameRegExp = /^[가-힣|a-z|A-Z]{1,20}$/;
const cNumberRegExp = /([0-9]{3})([0-9]{2})([0-9]{5})/;

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
    //         alert("인증번호를 발송했습니다. 인증번호가 오지 않으면 입력하신 번호가 맞는지 확인해 주세요.");
    //         startTimer(leftSec); // 타이머 시작
    //         alert(response);
    //         return certificationNum = response;
    //     },
    //     error: function () {
    //         alert("인증번호 발송 실패");
    //         location.href="/findId"
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
            window.location = "/findId"
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
                window.location = "/findId"
            }
        });
        return checkResult;
    }
}

function goFindId(){
    window.location="/findId";
}

function goLogin(){
    window.location="/login";
}

$(document).ready(function () {
    let memberNameResult = false;
    let memberPhoneNumResult = false;
    let companyNameResult = false;
    let companyNumberResult = false;

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
        } else if (!(mPhoneNumRegExp.test(memberPhoneNum()))) {ZZ
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

    // 상호명체크
    $("#input-companyName").blur(function () {
        if (companyName() === null || companyName() === "") {
            cNameFeedback().text("필수 정보입니");
            return companyNameResult = false;
        } else if (!(cNameRegExp.test(companyName()))) {
            cNameFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
            return companyNameResult = false;
        } else {
            cNameFeedback().text("");
            return companyNameResult = true;
        }
    });

    $("#input-companyNumber").blur(function () {
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
                    if (check) { // 존재
                        cNumberFeedback().text("");
                        return companyNumberResult = true;
                    } else {//조건에 맞을 때
                        cNumberFeedback().text("가입되지 않은 사업자번호입니다.");
                        return companyNumberResult = false;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/findId";
                }
            });
        }
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

    $("#findBtn").click(function () {
        if (memberName() === null || memberName() === "") {
            mNameFeedback().text("필수 정보입니다.");
        }
        if (memberPhoneNum() === null || memberPhoneNum() === "") { //값이 없을 때
            mPhoneNumFeedback().text("인증이 필요합니다.");
        }
        if (smsConfirmNum().val() === null || smsConfirmNum().val() === "") {
            mPhoneNumFeedback().text("인증이 필요합니다.");
        }
        if (memberNameResult && memberPhoneNumResult && checkSmsConfirmNum()) {
            $("#findBtn").attr("type", "submit");
        }
    });

    $("#findCompanyBtn").click(function () {
        if (companyName() === null || companyName() === "") {
            cNameFeedback().text("필수 정보입니다.");
        }
        if (companyNumber() === null || companyNumber() === "") { //값이 없을 때
            cPhoneNumFeedback().text("필수 정보입니다.");
        }
        if (companyNameResult && companyNumberResult) {
            $("#findCompanyBtn").attr("type", "submit");
        }
    });

    if($("#findMemberIdResult").val().length > 0){
        $("#staticBackdrop").modal('show');
    }

    if($("#findCompanyIdResult").val().length > 0){
        $("#staticBackdrop2").modal('show');
    }

    $("#companyBtn").click(function () {
        $("#memberForm").attr('hidden',true);
        $("#companyForm").attr('hidden',false);
    })
    $("#memberBtn").click(function () {
        $("#companyForm").attr('hidden',true);
        $("#memberForm").attr('hidden',false);
    })

});
