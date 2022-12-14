//form input value 값 ,,, 전역으로 사용하기위해 function 선언함
memberId = () => $("#input-memberId").val();
memberPw = () => $("#input-memberPw").val();
memberPw2 = () => $("#input-memberPw2").val();
memberName = () => $("#input-memberName").val();
memberYear = () => $("#input-year").val();
memberMonth = () => $("#select-month").val();
memberDay = () => $("#input-day").val();
memberEmail = () => $("#input-memberEmail").val();
memberDetailAddress = () => $("#sample6_detailAddress").val();
memberPostCode = () => $("#sample6_postcode").val();
memberExtraAddress = () => $("#sample6_extraAddress").val();
memberAddress = () => $("#sample6_address").val();
memberPhoneNum = () => $("#input-memberPhoneNum").val();
smsConfirmNum = () => $("#input-smsConfirmNum");

//error 메세지 (input 밑에 있는 p태그) 전역으로 사용하기위해 function 선언함
mIdFeedback = () => $("#feedback-memberId");
mPwFeedback = () => $("#feedback-memberPw");
mPwFeedback2 = () => $("#feedback-memberPw2");
mNameFeedback = () => $("#feedback-memberName");
mBirthFeedback = () => $("#feedback-memberBirthday");
mPhoneNumFeedback = () => $("#feedback-memberPhoneNum");
mEmailFeedback = () => $("#feedback-memberEmail");
mAddrFeedback = () => $("#feedback-memberAddress");
smsConfirmNumFeedback = () => $("#feedback-smsConfirmPhoneNum");

//select option 선택에 따른 style 변경용
function chnSelectGender() {
    if ($("#select-gender option:checked").val() == "") {
        $("#label-gender").hide();
        $("#select-gender").css("padding", "1rem 0.75rem");
        $("#select-gender").css("color", "#888");
    } else {
        $("#select-gender").css("color", "#212529");
        $("#label-gender").show();
        $("#select-gender").css("padding-top", "1.625rem");
        $("#select-gender").css("padding-bottom", "0.625rem");
        $("#label-gender").css("opacity", "0.65");
        $("#label-gender").css("transform", "scale(0.85) translateY(-0.5rem) translateX(0.15rem)");
    }
}

function chnSelectMonth() {
    if ($("#select-month option:checked").val() != "") {
        $("#select-month").css("color", "#212529");
        $("#label-gender").show();
        $("#select-month").css("padding-top", "1.625rem");
        $("#select-month").css("padding-bottom", "0.625rem");
        $("#label-month").css("opacity", "0.65");
        $("#label-month").css("transform", "scale(0.85) translateY(-0.5rem) translateX(0.15rem)");
    }
}

//error메세지 text 색상 변경용
function textInfo(value) {
    if ((value.hasClass("text-info"))) {

    } else {
        value.removeClass("text-danger").addClass("text-info");

    }
}

function textDanger(value) {
    if (value.hasClass("text-danger")) {

    } else {
        value.removeClass("text-info").addClass("text-danger");

    }
}

//아이디 정규식(영문 소문자, 숫자, 특수문자(-,_)  5-20 글자만 가능)
const mIdRegExp = /^[a-z0-9_-]{5,20}$/;
//비밀번호 정규식 (영어 대소문자,특수문자,숫자 필수입력, 8-16글자)
const mPwRegExp = /^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
//이름 정규식 (한글, 2-6글자)
const mNameRegExp = /^[가-힣]{2,6}$/;
//휴대전화 정규식 (-빼고 입력 01로 시작, 총 10-11글자)
const mPhoneNumRegExp = /^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$/;
//숫자 정규식 (길이 상관 없이 숫자만 입력)
const numRegExp = /^[0-9]+$/;
//이메일 정규식
const mEmailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

//인증시간 변수
let timer = null;
let certificationNum = null;
const leftSec = 180; // 제한시간(초)
//인증시간 타이머 함수
function startTimer(count) {
    $("#smsBtn").attr("disabled", true);
    smsConfirmNum().attr("disabled", false);
    $("#input-memberPhoneNum").attr("readonly", true);
    var minutes, seconds;
    timer = setInterval(function () {
        minutes = parseInt(count / 60, 10);
        seconds = parseInt(count % 60, 10);

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;
        mPhoneNumFeedback().text(minutes + ":" + seconds + " 안에 인증번호를 입력해주세요.");
        // 타이머 끝
        if (--count < 0) {
            clearInterval(timer);
            alert("인증시간이 초과되었습니다. 인증번호를 다시 전송해주세요.");
            $("#input-memberPhoneNum").attr("readonly", false);
            mPhoneNumFeedback().text("");
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
            mPhoneNumFeedback().text("");
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
    //         location.href="/join"
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
            window.location = "/join"
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
                window.location = "/join"
            }
        });
        return checkResult;
    }
}

//생년월일 변수
const start_year = "1922"; // 생년월일 시작 년도
let today_year = new Date().getFullYear(); // 현재 년도
//생년월일 체크
function checkMemberBirthday() {
    let lastDay = new Date(new Date(memberYear(), memberMonth(), 1) - 86400000).getDate();
    textDanger(mBirthFeedback());
    if (memberYear() === null || memberYear() === "") {
        mBirthFeedback().text("태어난 년도 4자리를 정확하게 입력하세요.");
        return false;
    } else if (memberMonth() === null || memberMonth() === "") {
        mBirthFeedback().text("태어난 월을 선택하세요.");
        return false;
    } else if (memberDay() === null || memberDay() === "") {
        mBirthFeedback().text("태어난 일(날짜) 2자리를 정확하게 입력하세요.");
        return false;
    } else if (!(numRegExp.test(memberYear()))
        || !(numRegExp.test(memberMonth()))
        || !(numRegExp.test(memberDay()))) {
        mBirthFeedback().text("생년월일을 다시 확인해주세요.");
        return false;
    } else if (memberYear() > today_year) {
        mBirthFeedback().text("미래에서 오셨군요.(૭ ᐕ)૭");
        return false;
    } else if (memberYear() < start_year || memberYear().length < 4) {
        mBirthFeedback().text("정말이세요? ༼;´༎ຶ ۝༎ຶ`༽");
        return false;
    } else if (memberDay() > lastDay || memberDay() < 1) {
        mBirthFeedback().text("존재하지 않는 날짜입니다. 생년월일을 다시 확인해주세요.");
        return false;
    } else {
        textInfo(mBirthFeedback());
        mBirthFeedback().text("OK");
        return true;
    }
}

// 주소 찾기 - 카카오 우편번호 서비스 api 사용
function findAddress() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.
                document.getElementById("sample6_extraAddress").value = extraAddr;

            } else {
                document.getElementById("sample6_extraAddress").value = '';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('sample6_postcode').value = data.zonecode;
            document.getElementById("sample6_address").value = addr;

            $("#sample6_extraAddress").attr("disabled", false);
            $("#sample6_extraAddress").attr("readonly", true);
            $("#sample6_postcode").attr("disabled", false);
            $("#sample6_postcode").attr("readonly", true);
            $("#sample6_address").attr("disabled", false);
            $("#sample6_address").attr("readonly", true);

            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("sample6_detailAddress").focus();
        }
    }).open();
}

$(document).ready(function () {
    let memberIdResult = false;
    let memberPwResult = false;
    let memberPwResult2 = false;
    let memberNameResult = false;
    let memberEmailResult = false;
    let memberAddressResult = false;
    let memberPhoneNumResult = false;

    // 생년월일 - 월 select option 자동 추가
    for (var i = 1; i < 13; i++) {
        $('#select-month').append('<option value="' + i + '">' + i + '</option>');
    }

    // 아이디 체크
    $("#input-memberId").blur(function () {
        if (memberId() === null || memberId() === "") { //값이 없을 때
            textDanger(mIdFeedback());
            mIdFeedback().text("필수 정보입니다.");
            return memberIdResult = false;
        } else if (!(mIdRegExp.test(memberId()))) { //정규식에 맞지 않을 때
            textDanger(mIdFeedback());
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
                        textDanger(mIdFeedback());
                        mIdFeedback().text("중복 아이디입니다.");
                        return memberIdResult = false;
                    } else {//조건에 맞을 때
                        textInfo(mIdFeedback());
                        mIdFeedback().text("OK");
                        return memberIdResult = true;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/join";
                }

            });

        }
    });
    // 비밀번호 체크
    $("#input-memberPw").blur(function () {
        // alert(memberPw()); // 확인용
        textDanger(mPwFeedback());
        if (memberPw() === null || memberPw() === "") { //값이 없을 때
            mPwFeedback().text("필수 정보입니다.");
            memberPwResult = false;
        } else if (!(mPwRegExp.test(memberPw()))) { //정규식에 맞지 않을 때
            mPwFeedback().text("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
            memberPwResult = false;
        } else { // 조건에 맞을 때
            textInfo(mPwFeedback());
            mPwFeedback().text("OK");
            memberPwResult = true;
        }
        if (memberPwResult2) {
            if (!(memberPw() === memberPw2())) {
                textDanger(mPwFeedback2());
                mPwFeedback2().text("비밀번호가 일치하지 않습니다.");
                memberPwResult2 = false;
            }
        }

    });
    // 비밀번호 확인 체크
    $("#input-memberPw2").blur(function () {
        textDanger(mPwFeedback2());
        if (memberPw2() == null || memberPw2() === "") { //값 없을 때
            mPwFeedback2().text("필수 정보입니다.");
            return memberPwResult2 = false;
        } else if (!(memberPw2() === memberPw())) { //값이 같지 않을 때
            mPwFeedback2().text("비밀번호가 일치하지 않습니다.");
            return memberPwResult2 = false;
        } else { //일치할 때
            textInfo(mPwFeedback2());
            mPwFeedback2().text("OK");
            return memberPwResult2 = true;
        }
    });
    // 이름 체크
    $("#input-memberName").blur(function () {
        textDanger(mNameFeedback());
        if (memberName() === null || memberName() === "") { //값이 없을 때
            mNameFeedback().text("필수 정보입니다.");
            return memberNameResult = false;
        } else if (!(mNameRegExp.test(memberName()))) { //정규식에 맞지 않을 때
            mNameFeedback().text("한글을 사용하세요. (특수기호, 공백 사용 불가)");
            return memberNameResult = false;
        } else { // 조건에 맞을 때
            textInfo(mNameFeedback());
            mNameFeedback().text("OK");
            return memberNameResult = true;
        }
    });

    // 이메일 체크
    $("#input-memberEmail").blur(function () {
        if (memberEmail() === null || memberEmail() === "") { //값이 없을 때
            textDanger(mEmailFeedback());
            mEmailFeedback().text("필수 정보입니다.");
            return memberEmailResult = false;
        } else if (!(mEmailRegExp.test(memberEmail()))) { //정규식에 맞지 않을 때
            textDanger(mEmailFeedback());
            mEmailFeedback().text("이메일을 올바르게 입력해주세요. (예: petapet@pet.com)");
            return memberEmailResult = false;
        } else {
            $.ajax({
                type: "POST",
                url: "/checkEmail",
                data: {
                    memberEmail: memberEmail(),
                },
                dataType: "json",
                success: function (check) { // 통신 성공 시 "true" 혹은 "false" 반환
                    if (check) {
                        textDanger(mEmailFeedback());
                        mEmailFeedback().text("이미 가입된 이메일 주소입니다. 다른 이메일을 입력하여 주세요.");
                        return memberEmailResult = false;
                    } else {//조건에 맞을 때
                        textInfo(mEmailFeedback());
                        mEmailFeedback().text("OK");
                        return memberEmailResult = true;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/join";
                }
            });
        }
    });

    // 주소 체크
    $("#sample6_detailAddress").blur(function () {
        textDanger(mAddrFeedback());
        if (memberPostCode() === null || memberPostCode() === "") { //값이 없을 때
            mAddrFeedback().text("필수 정보입니다.");
            memberAddressResult = false;

        } else if (memberDetailAddress() === "") {
            mAddrFeedback().text("상세 주소를 입력해주세요.");
            return memberAddressResult = false;
        } else {
            textInfo(mAddrFeedback());
            mAddrFeedback().text("OK");
            return memberAddressResult = true;
        }
    });
    //휴대전화 체크
    $("#input-memberPhoneNum").blur(function () {
        textDanger(mPhoneNumFeedback());
        if (memberPhoneNum() === null || memberPhoneNum() === "") {
            mPhoneNumFeedback().text("인증이 필요합니다.");
            return memberPhoneNumResult = false;
        } else if (!(mPhoneNumRegExp.test(memberPhoneNum()))) {
            mPhoneNumFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
            return memberPhoneNumResult = false;
        } else {
            $.ajax({
                type: "POST",
                url: "/checkPhoneNum",
                data: {
                    memberPhoneNum: memberPhoneNum(),
                },
                dataType: "json",
                success: function (check) { // 통신 성공 시 "true" 혹은 "false" 반환
                    if (check) {
                        mPhoneNumFeedback().text("이미 가입된 휴대폰 번호입니다.");
                        return memberPhoneNumResult = false;
                    } else {//조건에 맞을 때
                        mPhoneNumFeedback().text("");
                        return memberPhoneNumResult = true;
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/join";
                }
            })
        }
    });

    // 인증번호 체크
    $("#input-smsConfirmNum").blur(function () {
        checkSmsConfirmNum();
    });


    $("#smsBtn").click(function () {
        textDanger(mPhoneNumFeedback());
        if (memberPhoneNum() === null || memberPhoneNum() === "") { //값이 없을 때
            mPhoneNumFeedback().text("인증이 필요합니다.");
        } else if (!(mPhoneNumRegExp.test(memberPhoneNum()))) { //정규식에 맞지 않을 때
            mPhoneNumFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
        } else {
            $.ajax({
                type: "POST",
                url: "/checkPhoneNum",
                data: {
                    memberPhoneNum: memberPhoneNum(),
                },
                dataType: "json",
                success: function (check) {
                    if (check) {
                        mPhoneNumFeedback().text("이미 가입된 휴대폰 번호입니다.");
                    } else {
                        mPhoneNumFeedback().text("");
                        sendBtnClick();
                    }
                },
                error: function () {
                    console.log("통신 오류");
                    window.location = "/join";
                }
            })
        }
    });

    // 회원가입 버튼 - 모든 조건이 만족할 때 submit됨
    $("#joinBtn").click(function () {
        if (memberId() === null || memberId() === "") {
            mIdFeedback().text("필수 정보입니다.");
        }
        if (memberPw() === null || memberPw() === "") {
            mPwFeedback().text("필수 정보입니다.");
        }
        if (memberPw2() == null || memberPw2() === "") {
            mPwFeedback2().text("필수 정보입니다.");
        }
        if (memberName() === null || memberName() === "") {
            mNameFeedback().text("필수 정보입니다.");
        }
        if (memberEmail() === null || memberEmail() === "") {
            mNameFeedback().text("필수 정보입니다.");
        }
        if (memberPostCode() === null || memberPostCode() === "") { //값이 없을 때
            mAddrFeedback().text("필수 정보입니다.");

        } else if (memberDetailAddress() === "") {
            mAddrFeedback().text("상세 주소를 입력해주세요.");
        }
        if (memberPhoneNum() === null || memberPhoneNum() === "") { //값이 없을 때
            mPhoneNumFeedback().text("인증이 필요합니다.");
        }

        // alert(memberIdResult + "///" + memberPwResult + "///" + memberPwResult2 + "///" + memberNameResult
        //     + "///" + checkMemberBirthday() + "///" + memberAddressResult + "///" + memberPhoneNumResult
        //     + "///" + checkSmsConfirmNum()); //확인용

        if (memberIdResult && memberPwResult && memberPwResult2 && memberNameResult && memberEmailResult &&
            checkMemberBirthday() && memberAddressResult && memberPhoneNumResult && checkSmsConfirmNum()) {
            $("#joinBtn").attr("type", "submit");
        }
    });


});
