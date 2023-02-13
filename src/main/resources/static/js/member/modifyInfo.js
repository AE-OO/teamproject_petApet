//form input value 값 ,,, 전역으로 사용하기위해 function 선언함
originalMemberPw = () => $("#input-originalMemberPw").val();
newMemberPw = () => $("#input-newMemberPw").val();
newMemberPw2 = () => $("#input-newMemberPw2").val();
memberYear = () => $("#input-year").val();
memberMonth = () => $("#input-month").val();
memberDay = () => $("#input-day").val();
memberDetailAddress = () => $("#sample6_detailAddress").val();
memberPostCode = () => $("#sample6_postcode").val();
memberExtraAddress = () => $("#sample6_extraAddress").val();
memberAddress = () => $("#sample6_address").val();
memberPhoneNum = () => $("#input-memberPhoneNum").val();
memberEmail = () => $("#input-memberEmail").val();
smsConfirmNum = () => $("#input-smsConfirmNum");

//error 메세지 (input 밑에 있는 p태그) 전역으로 사용하기위해 function 선언함
oPwFeedback = () => $("#feedback-originalMemberPw");
mNewPwFeedback = () => $("#feedback-newMemberPw");
mNewPwFeedback2 = () => $("#feedback-newMemberPw2");
mBirthFeedback = () => $("#feedback-memberBirthday");
mPhoneNumFeedback = () => $("#feedback-memberPhoneNum");
mEmailFeedback = () => $("#feedback-memberEmail");
mAddrFeedback = () => $("#feedback-memberAddress");
smsConfirmNumFeedback = () => $("#feedback-smsConfirmNum");
smsConfirmNumFeedback2 = () => $("#feedback-smsConfirmNum2");

//비밀번호 정규식 (영어 대소문자,특수문자,숫자 필수입력, 8-16글자)
const mPwRegExp = /^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
//휴대전화 정규식 (-빼고 입력 01로 시작, 총 10-11글자)
const mPhoneumRegExp = /^([01]{2})([0|1|6|7|8|9]{1})([0-9]{3,4})([0-9]{4})$/;
//숫자 정규식 (길이 상관 없이 숫자만 입력)
const numRegExp = /^[0-9]+$/;
const mEmailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

//현재 비밀번호 체크
function checkOriginalMemberPw() {
    if (originalMemberPw() === null || originalMemberPw() === "") {
        oPwFeedback().text("필수 정보입니다.");
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: "/checkPw",
            data: {
                memberPw: originalMemberPw()
            },
            dataType: "json",
            async: false,
            success: function (check) {
                if (check) {
                    oPwFeedback().text("");
                    $("#input-newMemberPw").attr("disabled", false);
                    $("#input-newMemberPw2").attr("disabled", false);
                    return isSuccess = true;
                } else {
                    oPwFeedback().text("현재 비밀번호와 일치하지 않습니다. ");
                    return isSuccess = false;
                }
            },
            error: function () {
                alert("통신 오류");
                window.location = "/member/checkInfo";
            }
        });
        return isSuccess;
    }
}

//새 비밀번호 체크
function checkNewMemberPw() {
    if (newMemberPw() === null || newMemberPw() === "") { //값이 없을 때
        mNewPwFeedback().text("필수 정보입니다.");
        return false;
    } else if (newMemberPw() === originalMemberPw()) {
        mNewPwFeedback().text("현재 비밀번호와 동일합니다.");
        return false;
    } else if (!(mPwRegExp.test(newMemberPw()))) { //정규식에 맞지 않을 때
        mNewPwFeedback().text("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
        return false;
    } else {
        mNewPwFeedback().text("");
        return true;
    }

    if (!(newMemberPw() === newMemberPw2())) {
        mNewPwFeedback2().text("비밀번호가 일치하지 않습니다.");
        return false;
    }
}

//새 비밀번호 확인 체크
function checkNewMemberPw2() {
    if (newMemberPw2() == null || newMemberPw2() === "") { //값 없을 때
        mNewPwFeedback2().text("필수 정보입니다.");
        return false;
    } else if (!(newMemberPw2() === newMemberPw())) { //값이 같지 않을 때
        mNewPwFeedback2().text("비밀번호가 일치하지 않습니다.");
        return false;
    } else if (newMemberPw2() === newMemberPw()) {
        mNewPwFeedback2().text("");
        return true;
    }
}

//휴대전화 체크
function checkMemberPhoneNum() {
    if (memberPhoneNum() === null || memberPhoneNum() === "") {
        $("#smsBtn").attr("disabled", true);
        mPhoneNumFeedback().text("휴대폰 번호를 입력해주세요.");
        return false;
    }else if(!(mPhoneumRegExp.test(memberPhoneNum()))) {
        mPhoneNumFeedback().text("형식에 맞지 않는 번호입니다. (-)제외하여 숫자만 정확히 입력해주세요.");
        $("#smsBtn").attr("disabled", true);
        return false;
    }else if(memberPhoneNum() == originalMemberPhoneNum){
        mPhoneNumFeedback().text("");
        $("#smsBtn").attr("disabled", false);
        return true;
    }else{
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
                    $("#smsBtn").attr("disabled", true);
                    return checkResult = false;
                } else {//조건에 맞을 때
                    mPhoneNumFeedback().text("");
                    $("#smsBtn").attr("disabled", false);
                    return checkResult = true;
                }
            },
            error: function () {
                console.log("통신 오류");
                window.location = "member/checkInfo";
            }
        });
        return checkResult;
    }
}

//인증시간 변수
let timer = null;
let certificationNum = null;
const leftSec = 180; // 제한시간(초)

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
            // $("#staticBackdrop").modal('hide');
            $("#input-memberPhoneNum").attr("readonly", false);
            $("#smsBtn").attr("disabled", false);
            clearInterval(timer);

            return;
        }
        if (checkSmsConfirmNum()) {
            smsConfirmNum().attr("disabled", true);
            smsConfirmNumFeedback().text("인증이 완료되었습니다.");
            $("#input-memberPhoneNum").attr("readonly", true);
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
    //         to: memberPhoneNum(),
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
    //         location.href="/member/checkInfo"
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
            $("#staticBackdrop").modal('show');
            alert("인증번호를 발송했습니다. 인증번호가 오지 않으면 입력하신 번호가 맞는지 확인해 주세요.");
            startTimer(leftSec); // 타이머 시작
            alert(response);
            return certificationNum = response;
        },
        error: function () {
            alert("인증번호 발송 실패");
            window.location.reload()
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
                // checkResult = false;
                if (check) {
                    // alert("인증이 완료되었습니다.");
                    // $("#input-smsConfirmNum").attr("readonly", true);
                    checkResult = true;
                    // $("#staticBackdrop").modal('hide');
                } else {
                    smsConfirmNumFeedback2().text("인증번호가 일치하지 않습니다. 인증번호를 다시 확인해주세요.");
                    checkResult = false;
                }
            }, error: function () {
                alert("통신오류");
                window.location.reload()
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
    } else if (memberDay() > lastDay || memberDay() < 1 || memberMonth() < 1 || memberMonth() > 12) {
        mBirthFeedback().text("존재하지 않는 날짜입니다. 생년월일을 다시 확인해주세요.");
        return false;
    } else {
        mBirthFeedback().text("");
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

// 주소 체크
function checkMemberAddress() {
    if (memberPostCode() === null || memberPostCode() === "") { //값이 없을 때
        mAddrFeedback().text("필수 정보입니다.");
        return false;
    } else if (memberDetailAddress() === "") {
        mAddrFeedback().text("상세 주소를 입력해주세요.");
        return false;
    } else {
        mAddrFeedback().text("");
        return true;
    }
}

function checkMemberEmail() {
    if (memberEmail() === null || memberEmail() === "") { //값이 없을 때
        mEmailFeedback().text("필수 정보입니다.");
        return false;
    } else if (!(mEmailRegExp.test(memberEmail()))) { //정규식에 맞지 않을 때
        mEmailFeedback().text("이메일을 올바르게 입력해주세요. (예: petapet@pet.com)");
        return false;
    } else if (memberEmail() === originalMemberEmail) {
        mEmailFeedback().text("");
        return true;
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
                    mEmailFeedback().text("이미 가입된 이메일 주소입니다. 다른 이메일을 입력하여 주세요.");
                    return checkResult = false;
                } else {//조건에 맞을 때
                    mEmailFeedback().text("");
                    return checkResult = true;
                }
            },
            error: function () {
                console.log("통신 오류");
                window.location = "/member/checkInfo";
            }
        });
        return checkResult;
    }
}

function setThumbnail(event) {
    let from = Array.from(event.target.files);
    $('#thumbnailImg').empty();
    $.each(from, function (idx, el) {
        let reader = new FileReader();
        reader.onload = function (event) {
            let img = $("#profileImg");
            img.attr("src", event.target.result);
        };
        reader.readAsDataURL(el);
    });
    updateProfile();
    $("#basicA").show();
}
let selectProfile = () => {
    $("#inputFile").click();
}
let selectBasicProfile = () => {
    let img = $("#profileImg");
    img.attr("src", "/img/profile.jpg");
    $('#inputFile').val('');
    updateProfile();
    $("#basicA").hide();
}

let updateProfile = () =>{
    let newMemberImg = $('#inputFile').get(0).files[0];
    let formData = new FormData();
    // formData.append('originalMemberImg', memberImg);
    formData.append('memberImg', newMemberImg);
    $.ajax({
        type: "POST",
        url: "/updateMemberImg",
        data: formData,
        processData: false,
        contentType: false,
        success: function () {
            // alert("수정완");
        },
        error: function () {
            console.log("통신 오류");
            window.location = "member/checkInfo";
        }
    });
}
$(document).ready(function () {
    if(memberImg === "/img/profile.jpg"){
        $("#basicA").hide();
    }
    $("#updateMemberPwBtn").click(function () {
        $("#updateMemberPw").modal('show');
    })
    $("#sendNewMemberPwBtn").click(function () {
        if (checkOriginalMemberPw() && checkNewMemberPw() && checkNewMemberPw2()) {
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

        if (memberEmail() === null || memberEmail() === "") { //값이 없을 때
            mEmailFeedback().text("필수 정보입니다.");
        }

        if (memberPhoneNum() === null || memberPhoneNum() === "") { //값이 없을 때
            mPhoneNumFeedback().text("인증이 필요합니다.");
        }

        if (!checkMemberBirthday()) {
            mBirthFeedback().text("필수 정보입니다.");
        }
        if (smsConfirmNum().val().length > 0) {
            if (checkMemberBirthday() && checkMemberAddress() && checkMemberPhoneNum() && checkSmsConfirmNum() && checkMemberEmail()) {
                alert("수정이 완료되었습니다.");
                $("#modifyBtn").attr("type", "submit");
            }
            return;
        }

        // alert(checkMemberBirthday()+"///"+checkMemberAddress()+"///"+checkMemberPhoneNum()+"///")
        if (checkMemberBirthday() && checkMemberAddress() && checkMemberPhoneNum() && checkMemberEmail()) {
            alert("수정이 완료되었습니다.");
            $("#modifyBtn").attr("type", "submit");
        }
    });

    // 회원탈퇴 버튼
    $("#withdrawalBtn").click(function () {
        window.location = "/member/withdrawal"
    });
});
