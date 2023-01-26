$(function () {
    getLoginId();
    //댓글 보기
    $(document).on("click", ".badge2", function () {
        window.open("/comment/" + $(this).parent().prev().text().trim(), "_blank", "width=900,height=800,left=0,top=0,location=no");
    });
    //찾기버튼
    $("#findBtn").click(function () {
        if ($("#type").val() === "no" && isNaN($("#searchContent").val())) {
            alert("숫자만 입력해주세요.");
            return;
        }
        if ($("#searchContent").val() === null || $("#searchContent").val() === '') {
            alert("내용을 입력해 주세요.")
        } else {
            window.location = "/community/search?type=" + $("#type").val() + "&searchContent=" + $("#searchContent").val()
        }
    })
});
let nowPage;
let nowCommunityCategory = "all";
let nowPageSize;
let nowSort;
let searchParams = new URLSearchParams($(location).attr('search'))
const params = Object.fromEntries(searchParams.entries());
let type = params.type
let searchContent = params.searchContent;

//게시글 List 가져오는 ajax
getCommunityList = function (value1, value2, value3) {
    $.ajax({
        url: "/community/getCommunityList",
        type: "post",
        data: {
            communityCategory: value1.toLowerCase(),
            pageNum: value2,
            pageSize: value3,
        },
        dateType: "json",
        success: function (data) {
            // console.log(data);
            nowCommunityCategory = value1.toLowerCase();
            nowPage = data.number;
            nowPageSize = data.size;
            showCommunityList(data);
            showPage(data);
            getTodayPosts(value1);
            $("#pageSize").text(data.size);
            $("#totalElements").text(data.totalElements);
            $("#categoryList").find('a').parent().removeClass('active')
            $("#categoryList").find("a:contains(" + nowCommunityCategory.charAt(0).toUpperCase() + ")").parent().addClass('active')
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}
//새글 갯수 가져오는 ajax
getTodayPosts = function (value) {
    $.ajax({
        url: "/community/todayPosts",
        type: "post",
        data: {communityCategory: value},
        dateType: "json",
        success: function (data) {
            $("#totayPosts").text(data);
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}
//공지사항 List 가져오는 ajax
getNotice = function () {
    $.ajax({
        url: "/community/getNotice",
        type: "post",
        dateType: "json",
        success: function (data) {
            showNoticeList(data)
            // console.log(data)
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}
//검색 기능 ajax
getSearchList = function (value1, value2, value3) {
    if (type === "no" && isNaN(searchContent)) {
        alert("숮자만 입력해주세요")
        return;
    }
    $.ajax({
        url: "/community/searchList/" + type,
        type: "post",
        data: JSON.stringify({
            searchContent: searchContent,
            pageNum: value1,
            pageSize: value2,
            sort: value3
        }),
        dateType: "json",
        contentType: 'application/json',
        success: function (data) {
            if (data.number > data.totalPages - 1 && data.totalPages > 0) {
                getSearchList(0, value2, value3);
                return;
            }
            console.log(data.content.length);
            nowPage = data.number;
            nowPageSize = data.size;
            nowSort = value3
            // console.log(nowSort)
            showCommunitySearchOrMemberList(data);
            showPage(data);
            $("#sort").find("button").removeClass('active')
            $("#sort").find("button[value='" + nowSort + "']").addClass('active')
            $("#totalElements").text(data.totalElements);
            $("#pageSize").text(data.size);
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}
//회원 작성글보기 ajax
getMemberWritingList = function (value1, value2, value3) {
    $.ajax({
        url: "/community/memberWritingList/" + value3,
        type: "post",
        data: {
            memberId: $(location).attr('pathname').split("/")[3],
            pageNum: value1,
            pageSize: value2,
        },
        dateType: "json",
        success: function (data) {
            if (data.number > data.totalPages - 1 && data.totalPages > 0) {
                getMemberWritingList(0, value2, value3);
                return;
            }
            // console.log(data)
            nowPage = data.number;
            nowPageSize = data.size;
            nowSort = value3;
            showCommunitySearchOrMemberList(data);
            showPage(data);
            $("#type").find("button").removeClass('active')
            $("#type").find("button[value='" + nowSort + "']").addClass('active')
            // $("#totalElements").text(data.totalElements);
            $("#pageSize").text(data.size);
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}

//게시글 리스트 태그
showCommunityList = function (data) {
    let str = '';
    if (data.content.length === 0) {
        str += `<tr><td class="py-5" colspan="5"><strong>작성된 글이 없습니다</strong></td></tr>`;
    } else {
        $.each(data.content, function (idx, val) {
            str += `<tr>
                            <td>${val.communityId}</td>
                            <td class="text-start">
                            <a href="/community/${val.communityId}" class="mx-2">`
            if (nowCommunityCategory === 'all') {
                str += `<strong class="me-2 text-danger">${val.communityCategory}</strong>`
            }
            if (val.communitySubCategory != null) {
                str += `<strong class="me-2">${val.communitySubCategory}</strong>`
            }
            str += `${val.communityTitle}</a>`
            if (val.commentListSize > 0) {
                str += `<span class="badge2" role="button">${val.commentListSize}</span></td>`
            }
            str += `<td><div class="memberDiv dropdown">
                    <a href="javascript:" class="memberId" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    ${val.memberId}</a>`
            if (loginId !== undefined) {
                str += `<ul class="dropdown-menu" style="min-width: auto;">
                        <li><a class="dropdown-item memberProfile" href="javascript:">회원정보</a></li>
                        <li><a class="dropdown-item memberWriting" href="javascript:">작성글보기</a></li>`
                if (val.memberId !== loginId) {
                    str += `<li><a class="dropdown-item sendMessage" href="javascript:">쪽지보내기</a></li>
                            <li><button type="button" class="dropdown-item memberReport" id="addProductReport"
                                    data-bs-toggle="modal" data-bs-target="#addReportModal" title="신고하기">신고하기</button></li>`
                }
            }
            str += `</ul></div></td>
                    <td>${val.modifiedDate}</td>
                    <td>${val.viewCount}</td>
                    </tr>`
        });
    }
    $("#communityList").html(str);
}
//공지 리스트
showNoticeList = function (data) {
    if (data.length === 0) {
        $("#noticeList").remove();
        return;
    }
    let str = '';
    $.each(data, function (idx, val) {
        str += `<tr class="bg-light fw-bold">
                        <td class="py-1"><botton class="btn btn-dark btn-sm">${val.communityCategory}</botton></td>
                        <td class="text-start">
                        <a href="/community/${val.communityId}" class="mx-2">${val.communityTitle}</a>`
        if (val.commentListSize > 0) {
            str += `<span class="badge2" role="button">${val.commentListSize}</span>`
        }
        str += `<td>관리자</td>
                        <td>${val.modifiedDate}</td>
                        <td>${val.viewCount}</td>
                        </tr>
                        </td>`
    });
    $("#noticeList").html(str);
}
//게시글 페이지네이션 태그
showPage = function (data) {
    let str = '';
    if (data.totalPages > 0) {
        str += `<div aria-label="Page navigation"><ul class="pagination justify-content-center">`
        if (data.first) {
            str += `<li id = "prevPage" class = "page-item disabled" >
                            <a class = "page-link" href = "javascript:" style = "pointer-events: none;" >
                            <span aria-hidden = "true"> <i class = "bi bi-chevron-left"></i></span>
                            </a>
                            </li>`
        } else {
            str += `<li id = "prevPage" class = "page-item">
                            <a class = "page-link" href = "javascript:">
                            <span aria-hidden = "true"><i class = "bi bi-chevron-left"></i></span>
                            </a>
                            </li>`
        }
        if (data.totalPages <= 5) {
            for (var i = 1; i <= data.totalPages; i++) {
                str += `<li class = "page-item`
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
                    str += `">
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
                <a class="page-link" href="javascript:">
                <span aria-hidden="true"><i class="bi bi-chevron-right"></i></span>
                </a>
                </li>`
        }
        str += `</ul></div>`
    }
    $("#page").html(str);
}

typeRes = function (type) {
    if (type === "titleContent") {
        return "제목+내용";
    }
    if (type === "title") {
        return "글제목";
    }
    if (type === "writer") {
        return "글작성자";
    }
    if (type === "no") {
        return "글번호";
    }
}

showCommunitySearchOrMemberList = function (data) {
    let str = '';
    if (data.content.length === 0) {
        str += `<tr><td class="py-5" colspan="5"><strong>검색 결과가 없습니다</strong></td></tr>`;
    } else {
        $.each(data.content, function (idx, val) {
            if ($("input[name=radioCheck]:checked").val() === "titleContent") {
                str += `<tr class="communityTitle border-white">`
            } else {
                str += `<tr class="communityTitle">`
            }
            str += `<td>${val.communityId}</td>
                            <td class="text-start">
                            <a href="/community/${val.communityId}" class="me-2">
                            <span class="me-1 text-danger">[${val.communityCategory}]</span>`
            if (val.communitySubCategory != null) {
                str += `<span class="me-2 text-lightGray">[${val.communitySubCategory}]</span>`
            }
            str += `${val.communityTitle}</a>`
            if (val.commentListSize > 0) {
                str += `<span class="badge2" role="button">${val.commentListSize}</span></td>`
            }
            str += `<td><div class="memberDiv dropdown">
                    <a href="javascript:" class="memberId" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    ${val.memberId}</a>`
            if (loginId !== undefined) {
                str += `<ul class="dropdown-menu" style="min-width: auto;">
                        <li><a class="dropdown-item memberProfile" href="javascript:">회원정보</a></li>
                        <li><a class="dropdown-item memberWriting" href="javascript:">작성글보기</a></li>`
                if (val.memberId !== loginId) {
                    str += `<li><a class="dropdown-item sendMessage" href="javascript:">쪽지보내기</a></li>
                            <li><button type="button" class="dropdown-item memberReport" id="addProductReport"
                                    data-bs-toggle="modal" data-bs-target="#addReportModal" title="신고하기">신고하기</button></li>`
                }
            }
            str += `</ul></div></td>
                    <td>${val.modifiedDate}</td>
                    <td>${val.viewCount}</td>
                    </tr>`
            if ($("input[name=radioCheck]:checked").val() === "titleContent") {
                str += `<tr class="communityContent">
                        <td class="pt-0"></td>
                        <td class="pt-0 text-start small" colspan="3">
                        <a href="/community/${val.communityId}" class="me-2 text-lightGray">` + val.communityContent.replace(/<[^>]*>?/g, '') + `</a></td>
                        <td class="pt-0"></td>
                        </tr>`
            } else {
                str += `<tr class="communityContent" style="display: none">
                        <td class="pt-0"></td>
                        <td class="pt-0 text-start small" colspan="3">
                        <a href="/community/${val.communityId}" class="me-2 text-lightGray">` + val.communityContent.replace(/<[^>]*>?/g, '') + `</a></td>
                        <td class="pt-0"></td>
                        </tr>`
            }
        });
    }
    $("#list").html(str);
}

getLoginMemberWritingList = function (value1, value2, value3) {
    $.ajax({
        url: "/" + value3 + "/loginMemberWritingList",
        type: "post",
        data: {
            pageNum: value1,
            pageSize: value2,
        },
        dateType: "json",
        success: function (data) {
            if (data.number > data.totalPages - 1 && data.totalPages > 0) {
                getMemberWritingList(0, value2, value3);
                return;
            }
            // console.log(data)
            nowPage = data.number;
            nowPageSize = data.size;
            nowSort = value3;
            if (value3 === "community") {
                showLoginMemberWritingList(data);
            } else {
                console.log(data)
                showLoginMemberCommentWritingList(data);
            }
            showPage(data);
            $("#type").find("button").removeClass('active')
            $("#type").find("button[value='" + nowSort + "']").addClass('active')
            $("#pageSize").text(data.size);
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}

showLoginMemberWritingList = function (data) {
    $("#radioCheck").show();
    let str = `<thead>
               <tr>
               <th class="col-md-1">번호</th>
               <th>제목</th>
               <th class="col-md-2">글쓴이</th>
               <th class="col-md-2">작성일</th>
               <th class="col-md-1">조회</th>
               </tr>
               </thead>
               <tbody>`;
    if (data.content.length === 0) {
        str += `<tr><td class="py-5" colspan="5"><strong>작성한 글이 없습니다</strong></td></tr>`;
    } else {
        $.each(data.content, function (idx, val) {
            if ($("input[name=radioCheck]:checked").val() === "titleContent") {
                str += `<tr class="communityTitle border-white">`
            } else {
                str += `<tr class="communityTitle">`
            }
            str += `<td>
                    <input class="form-check-input me-2" value="${val.communityId}" name="deleteCheck" type="checkbox">${val.communityId}</td>
                            <td class="text-start">
                            <a href="/community/${val.communityId}" class="me-2">
                            <span class="me-1 text-danger">[${val.communityCategory}]</span>`
            if (val.communitySubCategory != null) {
                str += `<span class="me-2 text-lightGray">[${val.communitySubCategory}]</span>`
            }
            str += `${val.communityTitle}</a>`
            if (val.commentListSize > 0) {
                str += `<span class="badge2" role="button">${val.commentListSize}</span></td>`
            }
            str += `<td><div class="memberDiv dropdown">
                    <a href="javascript:" class="memberId" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                    ${val.memberId}</a>`
            if (loginId !== undefined) {
                str += `<ul class="dropdown-menu" style="min-width: auto;">
                        <li><a class="dropdown-item memberProfile" href="javascript:">회원정보</a></li>
                        <li><a class="dropdown-item memberWriting" href="javascript:">작성글보기</a></li>`
                if (val.memberId !== loginId) {
                    str += `<li><a class="dropdown-item sendMessage" href="javascript:">쪽지보내기</a></li>
                            <li><button type="button" class="dropdown-item memberReport" id="addProductReport"
                                    data-bs-toggle="modal" data-bs-target="#addReportModal" title="신고하기">신고하기</button></li>`
                }
            }
            str += `</ul></div></td>
                    <td>${val.modifiedDate}</td>
                    <td>${val.viewCount}</td>
                    </tr>`
            if ($("input[name=radioCheck]:checked").val() === "titleContent") {
                str += `<tr class="communityContent">
                        <td class="pt-0"></td>
                        <td class="pt-0 text-start small" colspan="3">
                        <a href="/community/${val.communityId}" class="me-2 text-lightGray">` + val.communityContent.replace(/<[^>]*>?/g, '') + `</a></td>
                        <td class="pt-0"></td>
                        </tr>`
            } else {
                str += `<tr class="communityContent" style="display: none">
                        <td class="pt-0"></td>
                        <td class="pt-0 text-start small" colspan="3">
                        <a href="/community/${val.communityId}" class="me-2 text-lightGray">` + val.communityContent.replace(/<[^>]*>?/g, '') + `</a></td>
                        <td class="pt-0"></td>
                        </tr>`
            }
        });
    }
    str += `</tbody>`
    $("#list").html(str);
}
showLoginMemberCommentWritingList = function (data) {
    $("#radioCheck").hide();
    let str = `<tbody class="border-top border-2">`;
    if (data.content.length === 0) {
        str += `<tr><td class="py-5" colspan="5"><strong>작성한 댓글이 없습니다</strong></td></tr>`;
    } else {
        $.each(data.content, function (idx, val) {
            str += `<tr class="communityTitle text-start">
                    <td>
                    <div class="d-inline-block align-top">                    
                    <input class="form-check-input me-2" value="${val.commentId}" name="deleteCheck" type="checkbox">
                    </div>
                    <div class="d-inline-block">
                    <div>                    
                    <strong class="me-2">${val.memberId}</strong>
                    <span class="text-lightGray" style="font-size:11px !important;">${val.modifiedDate}</span>
                    </div>
                    <div>
                    <a href="/community/${val.communityId}" class="text-dark">
                    <span class="me-1">${val.commentContent}</span>
                    </a></div>
                    <div>
                    <a href="/community/${val.communityId}" class="me-2 text-lightGray" style="font-size:12px !important;">
                    원문제목 : <span class="text-lightGray" style="font-size:12px !important;">${val.communityTitle}</span>
                    <span class="text-danger" style="font-size:12px !important;">[${val.commentListSize}]</span>
                    </a></div></div></td></tr>`
        });
    }
    str += `</tbody>`
    $("#list").html(str);
}


