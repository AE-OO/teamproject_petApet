memberId = () => $("#input-memberId");
memberPw = () => $("#input-memberPw");
mIdFeedback = () => $("#feedback-memberId");
mPwFeedback = () => $("#feedback-memberPw");
//비밀번호 정규식 (영어 대소문자,특수문자,숫자 필수입력, 8-16글자)
const mPwRegExp = /^(?=.*[a-zA-z0-9])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
//이 문자가 포함된 아이디는 에러메세지 안없어짐
// const secret = window.atob("YWRtaW4=");

$(document).ready(function () {
    let memberIdResult = false;
    let memberPwResult = false;

    memberId().blur(function () {
        if (memberId().val() == "") {  //ID란이 비어있다면
            mIdFeedback().text("아이디를 입력해주세요.");
            return memberIdResult = false;
        }else{
            mIdFeedback().text("");
            return memberIdResult = true;
            // $.ajax({
            //     type: "POST",
            //     url: "/checkId",
            //     data: {
            //         memberId: memberId().val(),
            //     },
            //     dataType: "json",
            //     success: function (check) { // 통신 성공 시 "true" 혹은 "false" 반환
            //         if (check) { // 아이디 이미 존재
            //             if (memberId().val().includes(secret)) { //관리자 아이디인걸 확인 못하게 에러메세지 띄움
            //                 mIdFeedback().text("존재하지 않는 아이디입니다.(사실 관리자 아이디임)");
            //             } else {
            //                 mIdFeedback().text("")
            //             }
            //             return memberIdResult = true;
            //         } else {
            //             mIdFeedback().text("존재하지 않는 아이디입니다.");
            //             return memberIdResult = false;
            //         }
            //     },
            //     error: function () {
            //         console.log("통신 오류");
            //         window.location = "/login";
            //     }
            // });
        }
    });

    memberPw().blur(function () {
        if (memberPw().val() == "") { //PW란이 비어있다면
            mPwFeedback().text("비밀번호를 입력해주세요.");
            return memberPwResult = false;
        } else if (!(mPwRegExp.test(memberPw().val()))) {
            mPwFeedback().text("8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.");
            return memberPwResult = false;
        } else {
            mPwFeedback().text("");
            return memberPwResult = true;
        }
    });

    $("#loginBtn").click(function () {

        if (memberId().val() == "") {
            mIdFeedback().text("아이디를 입력해주세요.");
            memberId().focus();
            return;
        } else {
            mIdFeedback().text("");
        }

        if (memberPw().val() == "") {
            mPwFeedback().text("비밀번호를 입력해주세요.");
            memberPw().focus();
            return;
        } else {
            mPwFeedback().text("")
        }

        if (memberIdResult && memberPwResult) {
            $("#loginBtn").attr("type","submit")
        // $.ajax({
        //     type: "POST",
        //     url: "/login",
        //     data: JSON.stringify({
        //         memberId: memberId().val(),
        //         memberPw: memberPw().val()
        //     }),
        //     dataType: "json",
        //     contentType: 'application/json',
        //     success: function (response) {//검증을 통과하면 서버는 토큰을 발행시켜줌
        //         alert(response['token']); //토큰 발행 확인용
        //         // window.location = "/";
        //     },
        //     error: function () {
        //         alert("로그인 실패..ㅠ");
        //         window.location = "/login";
        //     }
        // });
        }
    });
});