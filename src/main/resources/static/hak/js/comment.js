function commentSave() {
    const data = {
        communityId: $('#communityId').val(),
        commentContent: $('#wr_content').val()
    }

    // 공백 및 빈 문자열 체크
    if (!data.commentContent || data.commentContent.trim() === "") {
        alert("공백 또는 입력하지 않은 부분이 있습니다.");
        return false;
    } else {
        alert(data.communityId);
        $.ajax({
            type: 'POST',
            url: '/community/' + data.communityId + '/comment',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('댓글이 등록되었습니다.');
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

}