$(function () {
    getCommentList();
    //댓글 글자수
    $("#commentContent").keyup(function () {
        $("#commentLength").text($("#commentContent").val().length);
    });
    //댓글 등록 버튼
    $("#commentSubmitBtn").click(function () {
        if ($("#commentContent").val() == '') {
            alert("내용을 입력해주세요");
            return;
        } else {
            submitComment();
        }
    });
    //비밀댓글 버튼
    $("#lockBtn").click(function () {
        $("#lockIcon").toggleClass("bi-unlock-fill");
        $("#lockIcon").toggleClass("text-gray");
        $("#lockIcon").toggleClass("bi-lock-fill");
        $("#lockIcon").toggleClass("text-danger");
    });
    //댓글 이미지첨부 버튼
    $("#commentImgBtn").click(function () {
        $("#inputFile").click();
    });
    //댓글작성 이미지 삭제버튼
    $("#imgRemoveBtn").click(function () {
        $("#inputFile").val('');
        $("#thumbnailImgDiv").addClass(' d-none');
        $("#thumbnailImg").attr("src", "");
    });
    //댓글 팝업 확인(닫기) 버튼
    $("#closeBtn").click(function () {
        window.close();
    });
    $("#backBtn").click(function () {
        history.go(-1);
        // window.location = "/community";
    });
    //commentMain 팝업 창 로그인 버튼
    $("#commentLoginBtn").click(function () {
        opener.parent.location.href = '/login'
        window.close();
    });
    //commentMain 팝업 창 회원가입 버튼
    $("#commentJoinBtn").click(function () {
        opener.parent.location.href = '/join'
        window.close();
    });
    //댓글 이전 페이지
    $(document).on("click", "#prevPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        goCommentPage(nowPage - 1);
    });
    //댓글 다음 페이지
    $(document).on("click", "#nextPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        goCommentPage(nowPage + 1);
    });
    //댓글 페이지선택
    $(document).on("click", ".selectPage", function () {
        if ($(this).parent().hasClass('active')) {
            return;
        }
        goCommentPage($(this).text() - 1);
    })
    //댓글 이미지 확대
    $(document).on("click", ".zoom-in", function () {
        $(this).attr("width", "");
        $(this).attr("height", "");
        $(this).css("max-width", $(".commentImgDiv").width());
        $(this).toggleClass("zoom-in");
        $(this).toggleClass("zoom-out");
    });
    //댓글 이미지 축소
    $(document).on("click", ".zoom-out", function () {
        $(this).attr("width", "130");
        $(this).attr("height", "130");
        $(this).toggleClass("zoom-in");
        $(this).toggleClass("zoom-out");
    });
    //댓글 삭제 버튼
    $(document).on("click", ".commentDelete", function () {
        if (confirm("정말로 삭제하시겠습니까?") == true) {
            let cImg = $(this).closest('.memberDiv').find(".commentImg").attr("src") === undefined ? undefined : $(this).closest('.memberDiv').find(".commentImg").attr("src").substring(7);
            $.ajax({
                url: "/comment/delete",
                type: "delete",
                dateType: "json",
                data: {
                    commentId: $(this).closest('.memberDiv').find(".sideMenu").attr('id'),
                    memberId: loginId,
                    commentImg: cImg
                },
                success: function (data) {
                    alert("댓글이 삭제되었습니다.")
                    goCommentPage(nowPage)
                }
                , error: function () {
                    alert('오류');
                }
            })
        } else {
            return;
        }

    });
    //댓글 수정 버튼
    $(document).on("click", ".commentUpdate", function () {
        $(this).closest('.memberDiv').append(commentForm())
        $("#commentContent2").val($(this).closest('.memberDiv').find(".commentContent").text());
        if ($(this).closest('.memberDiv').find(".commentImg").attr("src") !== undefined) {
            $("#thumbnailImg2").attr("src", $(this).closest('.memberDiv').find(".commentImg").attr("src"));
            $("#thumbnailImgDiv2").removeClass('d-none');
        }
        if ($(this).closest('.memberDiv').find("i").hasClass('bi-lock-fill')) {
            $("#lockIcon2").toggleClass("bi-unlock-fill");
            $("#lockIcon2").toggleClass("text-gray");
            $("#lockIcon2").toggleClass("bi-lock-fill");
            $("#lockIcon2").toggleClass("text-danger");
        }
        if (authorities === "[ROLE_ADMIN]") {
            $("#lockBtn2").remove()
        }
        $("#cancelBtn").next().attr("id", "updateCommentBtn");
        $("#commentLength2").text($("#commentContent2").val().length);
    });
    //댓글 수정 등록 버튼
    $(document).on("click", "#updateCommentBtn", function () {
        if ($("#commentContent2").val() == '') {
            alert("내용을 입력해주세요");
            return;
        } else {
            submitUpdateComment($(this).closest('.memberDiv').find(".sideMenu").attr('id'),loginId,
                $(this).closest('.memberDiv').find(".commentImg").attr("src"));
        }
    });
    //댓글 답글(대댓글) 달기 버튼
    $(document).on("click", ".recomment", function () {
        $(this).closest('.memberDiv').append(commentForm())
        if (authorities === "[ROLE_ADMIN]") {
            $("#lockBtn2").remove()
        }
        $("#cancelBtn").next().attr("id", "recommentBtn");
    });
    //댓글 답글(대댓글) 등록 버튼
    $(document).on("click", "#recommentBtn", function () {
        if ($("#commentContent2").val() == '') {
            alert("내용을 입력해주세요");
            return;
        } else {
            submitRecomment($(this).closest('.memberDiv').find(".sideMenu").attr('id'));
        }
    });
    //답글,댓글수정 취소버튼
    $(document).on("click", "#cancelBtn", function () {
        $("#commentForm").remove();
    });
    //답글,댓글수정 비밀글 설정
    $(document).on("click", "#lockBtn2", function () {
        $("#lockIcon2").toggleClass("bi-unlock-fill");
        $("#lockIcon2").toggleClass("text-gray");
        $("#lockIcon2").toggleClass("bi-lock-fill");
        $("#lockIcon2").toggleClass("text-danger");
    });
    //답글,댓글수정 이미지 첨부 버튼
    $(document).on("click", "#commentImgBtn2", function () {
        $("#inputFile2").click();
    });
    //답글,댓글수정 이미지 삭제 버튼
    $(document).on("click", "#imgRemoveBtn2", function () {
        $("#inputFile2").val('');
        $("#thumbnailImgDiv2").addClass(' d-none');
        $("#thumbnailImg2").attr("src", "");
    });
    //답글,댓글수정 글자수 체크
    $(document).on("keyup", "#commentContent2", function () {
        $("#commentLength2").text($("#commentContent2").val().length)
    });
});
let nowPage;
let postsMemberId
//댓글 이미지 썸네일
setThumbnail = function (event) {
    let from = Array.from(event.target.files);
    $('#thumbnailImg').empty();
    $.each(from, function (idx, el) {
        let reader = new FileReader();
        reader.onload = function (event) {
            $("#thumbnailImg").attr("src", event.target.result);
            $("#thumbnailImgDiv").removeClass('d-none');
        };
        reader.readAsDataURL(el);
    });
}
//대댓글 ,댓글 수정 이미지 썸네일
setThumbnail2 = function (event) {
    let from = Array.from(event.target.files);
    $('#thumbnailImg2').empty();
    $.each(from, function (idx, el) {
        let reader = new FileReader();
        reader.onload = function (event) {
            $("#thumbnailImg2").attr("src", event.target.result);
            $("#thumbnailImgDiv2").removeClass('d-none');
        };
        reader.readAsDataURL(el);
    });
}
//댓글 전송
submitComment = function () {
    let commentSercretRes = "";
    if ($("#lockIcon").hasClass("bi-lock-fill")) {
        commentSercretRes = "Y";
    } else {
        commentSercretRes = "N";
    }
    let submitData = {
        communityId: $(location).attr('pathname').split("/")[2],
        commentContent: $("#commentContent").val(),
        commentSecret: commentSercretRes,
    }
    let formData = new FormData();
    formData.append("commentInsertDTO", new Blob([JSON.stringify(submitData)], {type: "application/json"}));
    formData.append("commentImg", $('#inputFile').get(0).files[0]);

    $.ajax({
        url: "/comment/insert",
        type: "post",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log('success');
            goCommentPage(nowPage)
            // getCommentList();
            $("#commentContent").val('');
            $("#inputFile").val('');
            $("#thumbnailImgDiv").addClass(' d-none');
            $("#thumbnailImg").attr("src", "");
            $('#thumbnailImg').empty();

        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    });
}
//대댓글 전송
submitRecomment = function (value) {
    let commentSercretRes;
    if ($("#lockIcon2").hasClass("bi-lock-fill")) {
        commentSercretRes = "Y";
    } else {
        commentSercretRes = "N";
    }
    let submitData = {
        communityId: $(location).attr('pathname').split("/")[2],
        replyId: value,
        commentContent: $("#commentContent2").val(),
        commentSecret: commentSercretRes,
    }
    // alert($('#inputFile').val())
    let formData = new FormData();
    formData.append("insertReplyDTO", new Blob([JSON.stringify(submitData)], {type: "application/json"}));
    formData.append("commentImg", $('#inputFile').get(0).files[0]);

    $.ajax({
        url: "/comment/insertReply",
        type: "post",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log('success');
            goCommentPage(nowPage)
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    });
}
//수정댓글 전송
submitUpdateComment = function (cId, cMemberId, cImg) {
    let commentSercretRes = "";
    let deleteImgRes;
    if ($("#lockIcon2").hasClass("bi-lock-fill")) {
        commentSercretRes = "Y";
    } else {
        commentSercretRes = "N";
    }
    if ($("#thumbnailImg2").attr("src") !== cImg) {
        deleteImgRes = cImg === undefined ? 0 : cImg.substring(7);
    }
    let submitData = {
        commentId: cId,
        memberId: cMemberId,
        commentImg: cImg === undefined ? null : cImg.substring(7),
        deleteImg: deleteImgRes,
        commentContent: $("#commentContent2").val(),
        commentSecret: commentSercretRes,
    }
    let formData = new FormData();
    formData.append("commentUpdateDTO", new Blob([JSON.stringify(submitData)], {type: "application/json"}));
    formData.append("commentUpdateImg", $('#inputFile2').get(0).files[0]);

    $.ajax({
        url: "/comment/update",
        type: "patch",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log('success');
            // getCommentList();
            goCommentPage(nowPage);
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    });
}
//댓글 List 가져오는 ajax
getCommentList = function () {
    $.ajax({
        url: "/comment",
        type: "post",
        data: {communityId: $(location).attr('pathname').split("/")[2]},
        dateType: "json",
        success: function (data) {
            showList(data);
            showPage(data);
            nowPage = data.number;
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}
//댓글 페이지 선택
goCommentPage = function (value) {
    $.ajax({
        url: "/comment/page",
        type: "post",
        data: {
            pageNum: value,
            communityId: $(location).attr('pathname').split("/")[2]
        },
        dataType: "json",
        success: function (data) {
            showList(data);
            showPage(data);
            nowPage = data.number;
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    });
}
//댓글 태그 (댓글 수정, 대댓글용)
commentForm = function () {
    $("#commentForm").remove();
    let str = '';
    str += `<div id="commentForm" class="bg-white my-5 p-4 pt-2 border border-black border-opacity-10">
            <textarea id="commentContent2" style="resize:none; height: 100px; border:none;" class="w-100 form-control px-1 "
            placeholder="인터넷은 우리가 함께 만들어가는 소중한 공간입니다. 댓글 작성 시 타인에 대한 배려와 책임을 담아주세요."
            maxlength="600"></textarea>
            <div id="thumbnailImgDiv2" class="my-3 position-relative w-fit-content d-none">
            <img id="thumbnailImg2" class="border" width="150" height="150" style="object-fit: cover;">
            <button id="imgRemoveBtn2" type="button"
            class="btn-close btn-close-white bg-dark position-absolute top-0 end-0 rounded-0"
            aria-label="Close"></button>
            </div>
            <input type="file" id="inputFile2" class="form-control d-none" onchange="setThumbnail2(event)" accept=".gif, .jpg, .png">
            <div class="text-end mt-2">
            <button id="commentImgBtn2" class="btn btn-link2 float-start p-0">
            <i class="bi bi-image fs-4 mt-3"></i>
            </button>
            <span id="commentLength2">0</span><span class="text-grey">/600</span>
            <button id="lockBtn2" class="btn btn-link2 ms-2 p-0">
            <i id="lockIcon2" class="bi bi-unlock-fill fs-4 m-0"></i>
            </button>
            <button type="button" id="cancelBtn" class="btn btn-info ms-2 text-end">취소</button>
            <button type="button" class="btn btn-danger text-end">등록</button>
            </div>
            </div>`
    return str;
}
//댓글 리스트 태그
showList = function (data) {
    let str = '';
    let parentId;
    let parentMemberId;
    $.each(data.content, function (idx, val) {
        if(val.commentId === val.replyId){
            parentId = val.commentId;
            parentMemberId = val.memberId;
        }
        //부모 댓글이 삭제되었을 때
        if (val.isDeleted === 1) {
            str += `<div class="memberDiv px-5 py-2">
                    <div class="d-inline-block align-middle py-2">
                    <i class="bi bi-exclamation-circle me-1"></i>
                    <span class="pt-0 m-0">삭제된 댓글입니다.</span>
                    </div>
                    </div><hr class="m-0">`
        }

        //본인이 작성한 댓글이면 댓글 배경색 다르게 구현
        str += `<div class="memberDiv px-5 py-2`
        if (val.memberRole === "ROLE_ADMIN") {
            str += ` bg-info bg-opacity-10`
        } else if (val.memberId === loginId) {
            str += ` bg-light`
        }
        str += `">`

        //대댓글이면 ㄴ> 아이콘 추가
        if (val.commentId !== val.replyId) {
            str += `<i class="bi bi-arrow-return-right fs-5 px-3 align-middle"></i>`
        }

        //비밀댓글 + 댓글 작성자, 게시글 작성자, 권한이 관리자가 아닐 때
        if (val.commentSecret === "Y" &&
            !(val.memberId === loginId || postsMemberId === loginId || authorities === "[ROLE_ADMIN]" || (val.commentId !== val.replyId && val.replyId === parentId && loginId === parentMemberId))) {
            str += `<div class="d-inline-block align-middle py-2">
                        <i class="bi bi-lock-fill text-secondary me-1"></i>
                        <span class="pt-0 m-0">해당 댓글은 작성자와 운영자만 볼 수 있습니다.</span>
                        <span class="float-none mx-1">${val.modifiedDate}</span>
                        </div>`
            //공개댓글 or 비밀 댓글이면서 댓글 작성자, 게시글 작성자, 권한이 관리자일 때
        } else {
            str += `<div class="d-inline-block me-2">`

            //관리자가 작성한 댓글일 때 프로필 사진
            if (val.memberRole === "ROLE_ADMIN") {
                str += `<img class="rounded-circle border border-1" src="/img/adminProfile.jpg" width="50" height="50" style="object-fit:cover">`
                //개인 회원이 작성한 댓글 + 프로필 사진 null일 때 기본 프로필로 설정
            } else if (val.memberImg == 0) {
                str += `<img class="rounded-circle border border-1" src="/img/profile.jpg" width="50" height="50" style="object-fit:cover">`
                //개인 회원이면서 프로필 사진을 지정했을 때
            } else {
                str += `<img class="rounded-circle border border-1" src="/image/${val.memberImg}" width="50" height="50" style="object-fit:cover">`
            }

            str += `</div>
                    <div class="d-inline-block align-middle">`
            if (val.memberRole === "ROLE_ADMIN") {
                str += `<div class="me-1 d-inline-block "><strong class="text-dark">관리자</strong>`
            } else {
                str += `<div class="dropdown me-1 d-inline-block">
                        <a href="javascript:" role="button" class="memberId" data-bs-toggle="dropdown" aria-expanded="false">${val.memberId}</a>
                        <ul class="dropdown-menu" style="min-width: auto;">
                        <li><a class="dropdown-item memberProfile" href="javascript:">회원정보</a></li>
                        <li><a class="dropdown-item memberWriting" href="javascript:">작성글보기</a></li>`
                if (val.memberId !== loginId && authorities === "[ROLE_MEMBER]") {
                    str += `<li><a class="dropdown-item sendMessage" href="javascript:">쪽지보내기</a></li>
                            <button type="button" class="dropdown-item memberReport" id="addProductReport"
                                    data-bs-toggle="modal" data-bs-target="#addReportModal"
                                    title="신고하기">신고하기
                            </button></ul>`

                }
            }
            str += `</div>
                    <span class="float-none mx-1">${val.modifiedDate}</span>
                    <p class="pt-0 m-0">`
            //비밀 댓글일 경우 자물쇠 아이콘 추가
            if (val.commentSecret === "Y") {
                str += `<i class="bi bi-lock-fill text-secondary me-1"></i>`
            }
            str += `<span class="commentContent">${val.commentContent}</span>
                    </p>
                    </div>`

            //부모 댓글일 경우
            if (val.commentId === val.replyId) {
                str += `<div class="dropdown mt-1 float-end">
                        <button class="btn btn-link2" type="button" data-bs-toggle="dropdown">
                        <i class="bi bi-three-dots-vertical m-auto fs-5"></i></button>
                        <ul id="${val.commentId}" class="dropdown-menu sideMenu" style="min-width:auto;">
                        <li><a class="dropdown-item recomment" href="javascript:">답글</a></li>`
                if (val.memberId === loginId) {
                    str += `<li><a class="dropdown-item commentUpdate" href="javascript:">수정</a></li>
                            <li><a class="dropdown-item commentDelete" href="javascript:">삭제</a></li>`
                } else if(authorities === "[ROLE_ADMIN]"){
                    str += `<li><a class="dropdown-item commentDelete" href="javascript:">삭제</a></li>`
                }
                str += '</ul></div>'
            //대댓글일 경우
            } else {

                //로그인 회원 = 댓글 작성회원일경우........
                if (val.memberId === loginId) {
                    str += `<div class="dropdown mt-1 float-end">
                            <button class="btn btn-link2" type="button" data-bs-toggle="dropdown">
                            <i class="bi bi-three-dots-vertical m-auto fs-5"></i></button>
                            <ul id="${val.commentId}" class="dropdown-menu sideMenu" style="min-width:auto;" >
                            <li><a class="dropdown-item commentUpdate" href="javascript:">수정</a></li>
                            <li><a class="dropdown-item commentDelete" href="javascript:">삭제</a></li>
                            </ul></div>`
                }else if(authorities === "[ROLE_ADMIN]"){
                    str += `<div class="dropdown mt-1 float-end">
                            <button class="btn btn-link2" type="button" data-bs-toggle="dropdown">
                            <i class="bi bi-three-dots-vertical m-auto fs-5"></i></button>
                            <ul id="${val.commentId}" class="dropdown-menu sideMenu" style="min-width:auto;" >
                            <li><a class="dropdown-item commentDelete" href="javascript:">삭제</a></li>
                            </ul></div>`
                }
            }
            if (val.commentImg != 0) {
                str += `<div class="ms-5 ps-3 mt-2 commentImgDiv">
                            <img src="/image/${val.commentImg}" class="zoom-in commentImg" width="130" height="130" style="object-fit: cover;">
                            </div>`
            }
        }
        str += `</div>
                <hr class="m-0">`
    });
    $("#commentListSize").text(data.length);
    $("#commentList").html(str);
}

