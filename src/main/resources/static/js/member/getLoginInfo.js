let loginId;
let authorities;
getLoginId = function () {
    $.ajax({
        url: "/getLoginId",
        type: "post",
        dateType: "json",
        async: false,
        success: function (data) {
            if (data.length > 0) {
                loginId = data[0];
                authorities = data[1];
            }
        }
        , error: function () {
            console.log('로그인 아이디 오류');
        }
    })
}
$(function () {
    getLoginId();
});
