$(function () {
    getLoginId();
    //댓글 보기
    $(document).on("click", ".badge2", function () {
        let communityId = $(this).parent().prev().text().trim();
        if(communityId ==="공지사항"){
            communityId = $(this).attr("data-value");
        }
        window.open("/comment/" + communityId, "_blank", "width=900,height=800,left=0,top=0,location=no");
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
let searchParams = new URLSearchParams(location.search)
let type = searchParams.get("type")
let searchContent = searchParams.get("searchContent");
let communityContentParam = []
//게시글 List 가져오는 ajax
getCommunityList = function (category, page, size) {
    $.ajax({
        url: "/community/getCommunityList",
        type: "post",
        data: {
            communityCategory: category.toLowerCase(),
            pageNum: page,
            pageSize: size,
        },
        dateType: "json",
        success: function (data) {
            if (data.number > data.totalPages - 1 && data.totalPages > 0) {
                getCommunityList(category, 0, size);
                return;
            }
            nowCommunityCategory = category.toLowerCase();
            nowPage = data.number;
            nowPageSize = data.size;
            showCommunityList(data);
            showPage(data);
            getTodayPosts(category);
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
//게시글 List 태그
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
                if (val.memberId !== loginId && authorities==="[ROLE_MEMBER]") {
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
//새글 갯수 가져오는 ajax
getTodayPosts = function (category) {
    $.ajax({
        url: "/community/todayPosts",
        type: "post",
        data: {communityCategory: category.toLowerCase()},
        dateType: "json",
        success: function (data) {
            $("#todayPosts").text(data);
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
        }
        , error: function () {
            alert('오류가 발생하였습니다.');
        }
    })
}
//공지사항 List 태그
showNoticeList = function (data) {
    if (data.length === 0) {
        $("#noticeList").remove();
        return;
    }
    let str = '';
    $.each(data, function (idx, val) {
        str += `<tr class="bg-light fw-bold">
                        <td class="py-1"><button class="btn btn-dark btn-sm pe-none">${val.communityCategory}</button></td>
                        <td class="text-start">
                        <a href="/community/${val.communityId}" class="mx-2">${val.communityTitle}</a>`
        if (val.commentListSize > 0) {
            str += `<span class="badge2" role="button" data-value="${val.communityId}">${val.commentListSize}</span>`
        }
        str += `</td><td>관리자</td>
                        <td>${val.modifiedDate}</td>
                        <td>${val.viewCount}</td>
                        </tr>
                        </td>`
    });
    $("#noticeList").html(str);
}

//커뮤니티 검색 List 가져오는 ajax
getSearchList = function (page, size, sort) {
    if (type === "no" && isNaN(searchContent)) {
        alert("숮자만 입력해주세요")
        return;
    }
    $.ajax({
        url: "/community/searchList",
        type: "post",
        data: {
            type: type,
            searchContent: searchContent,
            pageNum: page,
            pageSize: size,
            sort: sort
        },
        dateType: "json",
        success: function (data) {
            if (data.number > data.totalPages - 1 && data.totalPages > 0) {
                getSearchList(0, size, sort);
                return;
            }
            nowPage = data.number;
            nowPageSize = data.size;
            nowSort = sort
            showCommunitySearchOrMemberWritingList(data);
            $("input[name=deleteCheck]").remove();
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
//회원 작성글 List 가져오는 ajax
getMemberWritingList = function (page, size, type) {
    $.ajax({
        url: "/community/memberWritingList",
        type: "post",
        data: {
            memberId: $(location).attr('pathname').split("/")[3],
            type: type,
            pageNum: page,
            pageSize: size,
        },
        dateType: "json",
        success: function (data) {
            if (data.number > data.totalPages - 1 && data.totalPages > 0) {
                getMemberWritingList(0, size, type);
                return;
            }
            nowPage = data.number;
            nowPageSize = data.size;
            nowSort = type;
            showCommunitySearchOrMemberWritingList(data);
            $("input[name=deleteCheck]").remove();
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
//커뮤니티 검색, 회원 작성글 리스트 태그
showCommunitySearchOrMemberWritingList = function (data) {
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
        str += `<tr><td class="py-5" colspan="5"><strong>검색 결과가 없습니다</strong></td></tr>`;
    } else {
        $.each(data.content, function (idx, val) {
            if ($("input[name=radioCheck]:checked").val() === "titleContent") {
                str += `<tr class="communityTitle border-white">`
            } else {
                str += `<tr class="communityTitle">`
            }
            str += `<td>
                    <input class="form-check-input me-2" value="${val.communityId}" name="deleteCheck" type="checkbox">${val.communityId}
                    </td>
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
                if (val.memberId !== loginId && authorities==="[ROLE_MEMBER]") {
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
                str += `<tr class="communityContent">`
            } else {
                str += `<tr class="communityContent" style="display: none">`
            }
            str += `<td class="pt-0"></td>
                    <td class="pt-0 text-start small" colspan="3">`
            if(val.communityContent.replace(/<[^>]*>?/g, '') ===''){
                str += `<a href="/community/${val.communityId}" class="me-2 text-lightGray" data-value="${idx}">[내용없음]</a></td>`
            }else{
                str += `<a href="/community/${val.communityId}" class="me-2 text-lightGray" data-value="${idx}">` + val.communityContent.replace(/<[^>]*>?/g, '') + `</a></td>`
            }
            str += `<td class="pt-0"></td>
                    </tr>`
        });
    }
    $("#list").html(str);
}

typeRes = function (type) {
    switch (type) {
        case "titleContent":
            return "제목+내용";
        case "title":
            return "글제목";
        case "writer":
            return "글작성자";
        case "no":
            return "글번호";
    }
}

//로그인회원 작성글 리스트 가져오는 ajax
getLoginMemberWritingList = function (page, size, communityOrComment) {
    $.ajax({
        url: `/${communityOrComment}/loginMemberWritingList`,
        type: "post",
        data: {
            pageNum: page,
            pageSize: size,
        },
        dateType: "json",
        success: function (data) {
            if (data.number > data.totalPages - 1 && data.totalPages > 0) {
                getMemberWritingList(0, size, communityOrComment);
                return;
            }
            nowPage = data.number;
            nowPageSize = data.size;
            nowSort = communityOrComment;
            if (communityOrComment === "community") {
                showCommunitySearchOrMemberWritingList(data);
                communityImgDummyArrSave(data.content);
            } else {
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
//로그인회원 작성댓글 리스트 태그
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
                    <div class="d-inline-block col-11">
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
                    </a></div>`
            if (val.commentImg !== "0") {
                str += `<div class="commentImgDiv">
                            <img src="/image/${val.commentImg}" class="zoom-in commentImg" width="70" height="70" style="object-fit: cover;">
                            </div>`
            }
            str += `</div></td></tr>`
        });
    }
    str += `</tbody>`
    $("#list").html(str);
}

communityImgDummyArrSave = function (data) {
    let dummy = document.createElement("div");
    $.each(data, function (idx, val) {
        dummy.innerHTML = val.communityContent;
        let images = Array.from(dummy.querySelectorAll("img[src]"));
        let sources = images.map(function (item) {
            return item.src.split("/").pop();
        });
        communityContentParam.push(sources)
    })
}


