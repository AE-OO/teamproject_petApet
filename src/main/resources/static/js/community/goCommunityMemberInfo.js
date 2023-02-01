$(function () {
    //쪽지보내기
    $(document).on("click", ".sendMessage", function () {
        goMessagePage($(this).closest('.memberDiv').find(".memberId").text().trim())
    })

    //회원정보 팝업 창
    $(document).on("click",".memberProfile",function () {
        let _width = '400';
        let _height = '300';
        // 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
        let _left = Math.ceil((window.screen.width - _width) / 2);
        let _top = Math.ceil((window.screen.height - _height) / 2);
        window.open('/community/memberProfile/' + $(this).closest('.memberDiv').find(".memberId").text().trim(), '_blank',
            'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top + ', location=no,resizeable=no,menubar=no,scrollbars=no,status=no');
    })

    //회원 작성글보기
    $(document).on("click",".memberWriting",function () {
        if($(location).attr('pathname').split("/")[1] === "comment"){
            opener.parent.location.href = "/community/memberWriting/"+ $(this).closest('.memberDiv').find(".memberId").text().trim()
            window.close();
        }else{
            window.location="/community/memberWriting/"+ $(this).closest('.memberDiv').find(".memberId").text().trim()
        }
    })
})

goMessagePage = function (memberId) {
    let _width = '450';
    let _height = '600';
    let _left = Math.ceil((window.screen.width - _width) / 2);
    let _top = Math.ceil((window.screen.height - _height) / 2);
    window.open('/message/' + memberId , '_blank',
        'width=' + _width + ', height=' + _height + ', left=' + _left + ', top=' + _top + ', location=no,resizeable=no,menubar=no,scrollbars=no,status=no');
}
