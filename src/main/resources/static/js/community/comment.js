$(function () {
    getLoginId();
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
    //닫기 버튼
    $("#closeBtn").click(function () {
        window.close();
    });
    //글목록 버튼
    $("#communityBtn").click(function () {
        window.location = "/community";
    });

    $("#backBtn").click(function () {
        window.location = "/community";
    });
    $("#loginBtn").click(function () {
        window.location = "/login";
    });

    $("#joinBtn").click(function () {
        window.location = "/join";
    });

    $("#commentLoginBtn").click(function () {
        opener.parent.location.href = '/login'
        window.close();
    });
    $("#commentJoinBtn").click(function () {
        opener.parent.location.href = '/join'
        window.close();
    });
    //게시물 삭제버튼
    $("#postsDelete").click(function () {
        let arr = [];
        $("#postsContent").find("img").each(function (index, item) {
            // arr[index] = $(this).attr('src').substr(16)
            arr.push($(this).attr('src').substr(16))
        });
        $.ajax({
            type: "POST",
            url: "/community/delete",
            data: {
                communityId: $(location).attr('pathname').split("/")[2],
                deleteImg: arr,
            },
            dataType: "json",
            success: function (result) {
                alert("삭제가 완료되었습니다.");
                window.location = "/community";
            },
            // error: function () {
            //     alert("오류가 발생했습니다.");
            // },
            complete: function (data) {
                alert("삭제가 완료되었습니다.");
                window.location = "/community";
                //  실패했어도 완료가 되었을 때 처리
            }
        });
    });
    //게시물 수정버튼
    $("#postsUpdate").click(function () {
        let form = document.createElement('form');
        let communityId;
        communityId = document.createElement('input');
        communityId.setAttribute('type', 'hidden');
        communityId.setAttribute('name', 'communityId');
        communityId.setAttribute('value', $(location).attr('pathname').split("/")[2]);
        form.appendChild(communityId);
        form.setAttribute('method', 'post');
        form.setAttribute('action', '/community/update');
        document.body.appendChild(form);
        form.submit();
    });
    //이전페이지
    $(document).on("click", "#prevPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        goCommentPage(nowPage - 1);
    });
    //다음페이지
    $(document).on("click", "#nextPage", function () {
        if ($(this).hasClass('disabled')) {
            return;
        }
        goCommentPage(nowPage + 1);
    });
    //페이지선택
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
        if ($(this).closest('.memberDiv').find(".memberId").text() === loginId) {
            let cImg = $(this).closest('.memberDiv').find(".commentImg").attr("src") === undefined ? undefined : $(this).closest('.memberDiv').find(".commentImg").attr("src").substring(7);
            // console.log($(this).closest('.memberDiv').find(".sideMenu").attr('id'))
            $.ajax({
                url: "/comment/delete",
                type: "delete",
                dateType: "json",
                data: {
                    commentId: $(this).closest('.memberDiv').find(".sideMenu").attr('id'),
                    memberId: $(this).closest('.memberDiv').find(".memberId").text(),
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
        }
    });
    //댓글 수정 버튼
    $(document).on("click", ".commentUpdate", function () {
        if ($(this).closest('.memberDiv').find(".memberId").text() === loginId) {
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
            $("#lockBtn2").next().next().attr("id", "updateCommentBtn");
            $("#commentLength2").text($("#commentContent2").val().length);
        }
    });
    //댓글 수정 등록 버튼
    $(document).on("click", "#updateCommentBtn", function () {
        if ($(this).closest('.memberDiv').find(".memberId").text() === loginId) {
            if ($("#commentContent2").val() == '') {
                alert("내용을 입력해주세요");
                return;
            } else {
                // alert($(this).closest('.memberDiv').find(".commentImg").attr("src"));
                // alert($(this).closest('.memberDiv').find(".sideMenu").attr('id'));
                // alert($(this).closest('.memberDiv').find(".memberId").text());
                submitUpdateComment($(this).closest('.memberDiv').find(".sideMenu").attr('id'),
                    $(this).closest('.memberDiv').find(".memberId").text(),
                    $(this).closest('.memberDiv').find(".commentImg").attr("src"));
            }
        }
    });
    //답글 달기 버튼
    $(document).on("click", ".recomment", function () {
        $(this).closest('.memberDiv').append(commentForm())
        $("#lockBtn2").next().next().attr("id", "recommentBtn");
    });
    //답글 등록 버튼
    $(document).on("click", "#recommentBtn", function () {
        if ($("#commentContent2").val() == '') {
            alert("내용을 입력해주세요");
            return;
        } else {
            submitRecomment($(this).closest('.memberDiv').find(".sideMenu").attr('id'));
        }
    });

    //답글작성, 댓글수정 취소버튼
    $(document).on("click", "#cancelBtn", function () {
        $("#commentForm").remove();
    });
    //답글작성, 댓글수정 비밀글 설정
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

    $(document).on("keyup", "#commentContent2", function () {
        $("#commentLength2").text($("#commentContent2").val().length)
    });

    $("#communityMemberProfile").click(function () {
        let _width = '400';
        let _height = '300';
        // 팝업을 가운데 위치시키기 위해 아래와 같이 값 구하기
        let _left = Math.ceil(( window.screen.width - _width )/2);
        let _top = Math.ceil(( window.screen.height - _height )/2);
        window.open('/community/memberProfile/'+$("#postsMemberId").text(), '_blank',
            'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top+', location=no,resizeable=no,menubar=no,scrollbars=no,status=no' );
    })

});
let loginId;
let authorities;
let nowPage;
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
    // alert($('#inputFile').val())
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
            // alert(data[1])
            // alert(data[0])
        }
        , error: function () {
            alert('오류');
        }
    })
}
//댓글 리스트 태그
showList = function (data) {
    let str = '';
    $.each(data.content, function (idx, val) {
        if (val.isDeleted === 1) {
            str += `<div class="memberDiv px-5 py-2">
                    <div class="d-inline-block align-middle py-2">
                    <i class="bi bi-exclamation-circle me-1"></i>
                    <span class="pt-0 m-0">삭제된 댓글입니다.</span>
                    </div>
                    </div><hr class="m-0">`
        }

        str += `<div class="memberDiv px-5 py-2`
        if (val.memberId === loginId) {
            str += ` bg-light`
        }
        str += `">`
        if (val.commentId !== val.replyId) {
            str += `<i class="bi bi-arrow-return-right fs-5 px-3 align-middle"></i>`
        }
        if (val.commentSecret === "Y") {
            if (val.memberId === loginId || $("#postsMemberId").text() === loginId || authorities === "[ROLE_ADMIN]") {
                str += `<div class="d-inline-block me-2">`
                if (val.memberImg == 0) {
                    str += `<img class="rounded-circle border border-1" src="/img/profile.jpg" width="50" height="50" style="object-fit:cover">`
                } else {
                    str += `<img class="rounded-circle border border-1" src="/image/${val.memberImg}" width="50" height="50" style="object-fit:cover">`
                }
                str += `</div>
                        <div class="d-inline-block align-middle">
                        <div class="dropdown me-1 d-inline-block">
                        <a href="javascript:" role="button" class="memberId" data-bs-toggle="dropdown" aria-expanded="false">${val.memberId}</a>
                        <ul class="dropdown-menu" style="min-width: auto;">
                        <li><a class="dropdown-item memberProfile" href="javascript:">회원정보</a></li>
                        <li><a class="dropdown-item" href="javascript:">작성글보기</a></li>`
                if (val.memberId !== loginId) {
                    str += `<li><a class="dropdown-item sendMessage" href="javascript:">쪽지보내기</a></li>
                            <button type="button" class="dropdown-item memberReport" id="addProductReport"
                                    data-bs-toggle="modal" data-bs-target="#addReportModal"
                                    title="신고하기">신고하기
                            </button>`
                }
                str += `</ul>
                        </div>
                        <span class="float-none mx-1">${val.modifiedDate}</span>
                        <p class="pt-0 m-0">
                        <i class="bi bi-lock-fill text-secondary me-1"></i>
                        <span class="commentContent">${val.commentContent}</span>
                        </p>
                        </div>
                        <div class="dropdown mt-1 float-end">
                        <button class="btn btn-link2" type="button" data-bs-toggle="dropdown">
                        <i class="bi bi-three-dots-vertical m-auto fs-5"></i></button>
                        <ul id="${val.commentId}" class="dropdown-menu sideMenu" style="min-width:auto;">`
                if (val.commentId === val.replyId) {
                    str += `<li><a class="dropdown-item recomment" href="javascript:">답글</a></li>`
                }
                if (val.memberId === loginId) {
                    str += `<li><a class="dropdown-item commentUpdate" href="javascript:">수정</a></li>
                            <li><a class="dropdown-item commentDelete" href="javascript:">삭제</a></li>`
                }
                str += '</ul></div>'
                if (val.commentImg != 0) {
                    str += `<div class="ms-5 ps-3 mt-2">
                            <img src="/image/${val.commentImg}" class="zoom-in commentImg" width="130" height="130" style="object-fit: cover;">
                            </div>`
                }
            } else {
                str += `<div class="d-inline-block align-middle py-2">
                        <i class="bi bi-lock-fill text-secondary me-1"></i>
                        <span class="pt-0 m-0">해당 댓글은 작성자와 운영자만 볼 수 있습니다.</span>
                        <span class="float-none mx-1">${val.modifiedDate}</span>
                        </div>`
            }
        } else if (val.commentSecret === "N") {
            str += `<div class="d-inline-block me-2">`
            if (val.memberImg == 0) {
                str += `<img class="rounded-circle border border-1" src="/img/profile.jpg" width="50" height="50" style="object-fit:cover">`
            } else {
                str += `<img class="rounded-circle border border-1" src="/image/${val.memberImg}" width="50" height="50" style="object-fit:cover">`
            }
            str += `</div>
                    <div class="d-inline-block align-middle">
                    <div class="dropdown me-1 d-inline-block">
                    <a href="javascript:"  class="memberId" role="button" data-bs-toggle="dropdown" aria-expanded="false">${val.memberId}</a>
                    <ul class="dropdown-menu" style="min-width: auto;">
                    <li><a class="dropdown-item memberProfile" href="javascript:">회원정보</a></li>
                    <li><a class="dropdown-item" href="javascript:">작성글보기</a></li>`
            if (val.memberId !== loginId) {
                str += `<li><a class="dropdown-item" href="javascript:">쪽지보내기</a></li>
                        <button type="button" class="dropdown-item memberReport" id="addProductReport"
                                    data-bs-toggle="modal" data-bs-target="#addReportModal"
                                    title="신고하기">신고하기
                        </button>`
            }
            str += `</ul>
                    </div>
                    <span class="float-none mx-1">${val.modifiedDate}</span>
                    <p class="pt-0 m-0"><span class="commentContent">${val.commentContent}</span></p>
                    </div>`
            if (val.commentId === val.replyId) {
                if (authorities === "[ROLE_MEMBER]" || authorities === "[ROLE_ADMIN]") {
                    str += `<div class="dropdown mt-1 float-end">
                            <button class="btn btn-link2" type="button" data-bs-toggle="dropdown">
                            <i class="bi bi-three-dots-vertical m-auto fs-5"></i></button>
                            <ul id="${val.commentId}" class="dropdown-menu sideMenu" style="min-width:auto;" >
                            <li><a class="dropdown-item recomment" href="javascript:">답글</a></li>`
                    if (val.memberId === loginId) {
                        str += `<li><a class="dropdown-item commentUpdate" href="javascript:">수정</a></li>
                                <li><a class="dropdown-item commentDelete" href="javascript:">삭제</a></li>`
                    }
                    str += `</ul></div>`
                }
                if (val.commentImg != 0) {
                    str += `<div class="ms-5 ps-3 mt-2">
                            <img src="/image/${val.commentImg}" class="zoom-in commentImg" width="130" height="130" style="object-fit: cover;">
                            </div>`
                }
            } else {
                if (val.memberId === loginId) {
                    str += `<div class="dropdown mt-1 float-end">
                            <button class="btn btn-link2" type="button" data-bs-toggle="dropdown">
                            <i class="bi bi-three-dots-vertical m-auto fs-5"></i></button>
                            <ul id="${val.commentId}" class="dropdown-menu sideMenu" style="min-width:auto;" >
                            <li><a class="dropdown-item commentUpdate" href="javascript:">수정</a></li>
                            <li><a class="dropdown-item commentDelete" href="javascript:">삭제</a></li>
                            </ul></div>`
                }
                if (val.commentImg != 0) {
                    str += `<div class="ms-6">
                            <img src="/image/${val.commentImg}" class="zoom-in commentImg" width="130" height="130" style="object-fit: cover;">
                            </div>`
                }
            }
        }
        str += `</div>
                <hr class="m-0">`
    });
    $("#commentListSize").text(data.length);
    $("#commentList").html(str);
}
showPage = function (data) {
    let str = '';
    if (data.totalPages > 0) {
        str += `<div aria-label="Page navigation" class="mt-4">
                <ul class="pagination justify-content-center">`
        if (data.first) {
            str += `<li id="prevPage" class="page-item disabled">
                    <a class="page-link" href="javascript:" style="pointer-events: none;">
                    <span aria-hidden="true"><i class="bi bi-chevron-left"></i></span>
                    </a>
                    </li>`
        } else {
            str += `<li id="prevPage" class="page-item">
                    <a class="page-link" href="javascript:">
                    <span aria-hidden="true"><i class="bi bi-chevron-left"></i></span>
                    </a>
                    </li>`
        }

        if (data.totalPages <= 5) {
            for (var i = 1; i <= data.totalPages; i++) {
                str += `<li class="page-item`
                if (data.number + 1 === i) {
                    str += ` active`
                }
                str += `">
                        <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                        </li>`
            }
        }
        if (data.totalPages > 5 && data.totalPages <= 10) {
            if (data.number + 1 <= 5) {
                for (var i = 1; i <= 5; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">'
                            <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                            </li>`
                }
                str += `<li class="page-item disabled"><a class="page-link">...</a></li>
                        <li class="page-item"><a class="page-link selectPage" href="javascript:">${data.totalPages}</a></li>`

            }
            if (data.number + 1 > 5) {
                str += `<li class="page-item"><a class="page-link selectPage" href="javascript:">1</a></li>
                        <li class="page-item disabled"><a class="page-link">...</a></li>`
                for (var i = 6; i <= data.totalPages; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                            <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                            </li>`
                }
            }
        }
        if (data.totalPages > 10) {
            if (data.number + 1 <= 5) {
                for (var i = 1; i <= 5; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                            <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                            </li>`
                }
                str += `<li class="page-item disabled"><a class="page-link">...</a></li>
                        <li class="page-item"><a class="page-link selectPage" href="javascript:">${data.totalPages}</a></li>`
            }
            if (data.number + 1 > 5 && data.number + 1 < data.totalPages - 4) {
                str += `<li class="page-item"><a class="page-link selectPage" href="javascript:">1</a></li>
                        <li class="page-item disabled"><a class="page-link">...</a></li>`
                for (var i = data.number + 1 - 2; i <= data.number + 1 + 2; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                            <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                            </li>`
                }
                str += `<li class="page-item disabled"><a class="page-link">...</a></li>
                        <li class="page-item"><a class="page-link selectPage" href="javascript:">${data.totalPages}</a></li>`
            }
            if (data.number + 1 >= data.totalPages - 4) {
                str += `<li class="page-item"><a class="page-link selectPage" href="javascript:">1</a></li>
                        <li class="page-item disabled"><a class="page-link">...</a></li>`

                for (var i = data.totalPages - 4; i <= data.totalPages; i++) {
                    str += `<li class="page-item`
                    if (data.number + 1 === i) {
                        str += ` active`
                    }
                    str += `">
                            <a class="page-link selectPage" href="javascript:"><span>${i}</a>
                            </li>`
                }
            }
        }

        if (data.last) {
            str += `<li id="nextPage" class="page-item disabled">
                    <a class="page-link" href="javascript:" style="pointer-events: none;">
                    <span aria-hidden="true"><i class="bi bi-chevron-right"></i></span>
                    </a>
                    </li>`
        } else {
            str += `<li id="nextPage" class="page-item">
                    <a class="page-link" href="javascript:" style="pointer-events: none;">
                    <span aria-hidden="true"><i class="bi bi-chevron-right"></i></span>
                    </a>
                    </li>`
        }
        str += `</ul></div>`
    }
    $("#commentPage").html(str);
}
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
            <button id="lockBtn2" class="btn btn-link2 mx-2 p-0">
            <i id="lockIcon2" class="bi bi-unlock-fill fs-4 m-0"></i>
            </button>
            <button type="button" id="cancelBtn" class="btn btn-info text-end">취소</button>
            <button type="button" class="btn btn-danger text-end">등록</button>
            </div>
            </div>`
    return str;
}