$(document).ready(function () {
    loadingFirst();
    moveSideBar();
    movePage();
    setOutOfStock()

    //공지사항 삭제 버튼 클릭
    $(document).on("click", ".deleteNotice", function () {
        var id = $(this).attr("id");

        var returnConfirm = confirm("삭제하시겠습니까?");
        if (returnConfirm) {
            $.ajax({
                url: "/admin/deleteCommunity/" + id,
                type: "get",
                success() {
                    getNoticeList();
                }
            })
        }
    });

    //커뮤니티 관리 부분의 버튼 클릭 시 모달 뜨게
    $(document).on("click", ".communityModal", function () {
        var communityId = $(this).attr("id");

        $(".communityDelete").empty();

        var modalBody = '';
        modalBody += '게시글<br/>';
        modalBody += '<div style="color: red">' + communityId + '</div> 번';
        modalBody += '을(를) 삭제하시겠습니까?<br/>삭제 버튼을 누르면 삭제됩니다.<br/>';
        $(".communityDelete").append(modalBody);
        $(".confirmCommunityDelete").attr("id", communityId);
        $("#confirmCommunityDeleteModal").modal('show');
    });

    //게시글 삭제 버튼 클릭
    $(".confirmCommunityDelete").click(function () {
        var communityId = $(this).attr("id");
        console.log(communityId);

        $.ajax({
            url: "/admin/deleteCommunity/" + communityId,
            type: "get",
            success() {
                getCommunityList();
            }
        })
    });

    //회원관리 부분의 버튼 클릭시 모달 뜨게
    $(document).on("click", ".memberModal", function () {
        var memberId = $(this).attr("id");

        $(".memberDelete").empty();

        var modalBody = '';
        modalBody += '아이디<br/>';
        modalBody += '<div style="color: red">' + memberId + '</div>';
        modalBody += '을(를) 정지 또는 강제 탈퇴하겠습니까?<br/>확인을 누르면 처리됩니다.<br/>';
        $(".memberDelete").append(modalBody);
        $(".confirmDisabledMember").attr("id", memberId);
        $(".confirmMemberDelete").attr("id", memberId);
        $("#confirmMemberDeleteModal").modal('show');
    })

    //회원 정지 버튼 클릭
    $(".confirmDisabledMember").click(function () {
        var memberId = $(this).attr("id");

        $.ajax({
            url: "/admin/disabledMember/" + memberId,
            type: "get",
            success() {
                alert("회원을 정지시켰습니다.");
                getMemberList();
            }
        })
    });

    //회원 강제탈퇴 버튼 클릭
    $(".confirmMemberDelete").click(function () {
        var memberId = $(this).attr("id");

        $.ajax({
            url: "/admin/deleteMember/" + memberId,
            type: "get",
            success() {
                alert("회원을 강제탈퇴하였습니다.");
                getMemberList();
            }, error() {
                getMemberList();
            }
        })
    });

    //신고 승인 버튼 클릭
    $("#acceptReportBtn").click(function () {
        if ($("#modalTargetType").val() === "community") {
            $.ajax({
                url: "/admin/acceptCommunityReport/" + $("#modalReportId").val(),
                data: {communityId: $("#modalTargetId").val()},
                type: "get",
                success() {
                    getCommunityReportList();
                    getCommunityList();
                }
            })
        } else if ($("#modalTargetType").val() === "product") {
            $.ajax({
                url: "/admin/acceptProductReport/" + $("#modalReportId").val(),
                data: {productId: $("#modalTargetId").val()},
                type: "get",
                success() {
                    getProductReportList();
                }
            })
        } else {
            $.ajax({
                url: "/admin/acceptMemberReport/" + $("#modalReportId").val(),
                data: {memberId: $("#modalTargetId").val()},
                type: "get",
                success() {
                    getMemberReportList();
                    getMemberList();
                }
            })
        }
    });

    //신고 반려 버튼 클릭
    $("#cancelReportBtn").click(function () {
        $.ajax({
            url: "/admin/refuseReport/" + $("#modalReportId").val(),
            type: "post",
            success() {
                getProductReportList();
                getMemberReportList();
                getProductReportList();
            }
        })
    });

    //회사 가입 승인 버튼 클릭
    $("#acceptCompanyJoinBtn").click(function () {
        $.ajax({
            url: "/acceptJoinCompany/" + $("#modalCompanyId").val(),
            type: "post",
            success() {
                getCompanyJoinAcceptList();
            }
        })
    });

    //회사 가입 거절 버튼 클릭
    $("#refuseCompanyJoinBtn").click(function () {
        $.ajax({
            url: "/email/sendRefuseReason",
            type: "post",
            data: {companyId: $("#modalCompanyId").val(), reason: $("#refuseReasonJoinCompany option:selected").val()},
            success() {
                alert("메일을 전송했습니다.");
                $.ajax({
                    url: "/refuseJoinCompany/" + $("#modalCompanyId").val(),
                    type: "post",
                    success() {
                        getCompanyJoinAcceptList();
                    }
                })
            }
        })
    })
})

//처음 adminPage화면 로딩할 때 데이터 가져가는 메소드
function loadingFirst() {
    getNoticeList();
    getOtherInquiryList();
    getCommunityReportList();
    getMemberReportList();
    getProductReportList();
    getCompanyJoinAcceptList();
    getCommunityList();
    getMemberList();

    reportColor();
}

//공지사항 리스트 출력
function getNoticeList() {
    $.getJSON('/admin/getNoticeList', function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, notice) {
                list += `<tr>
                            <td class="pl-0">${notice.communityId}</td>
                            <td><a href="/admin/updateNoticeForm/${notice.communityId}">${notice.communityTitle}</a>
                            </td>
                            <td>
                                <a href="/admin/updateNoticeForm/${notice.communityId}">
                                    <button class="btn btn-success btn-sm updateFAQ" type="button">수정
                                    </button>
                                </a>
                            </td>
                            <td>
                                <button class="btn btn-danger btn-sm deleteNotice" type="button"
                                        id="${notice.communityId}">삭제
                                </button>
                            </td>
                        </tr>`;
            })
        }
        $(".noticeList").html(list);
    })
}

//문의내역 리스트 출력
function getOtherInquiryList() {
    $.getJSON('/admin/getOtherInquiryList', function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, other) {
                list += `<tr>
                            <td class="pl-0">${other.inquiredId}</td>
                            <td><a href="/admin/${other.inquiredId}/edit">${other.inquiredTitle}</a>
                            </td>
                            <td>${other.inquiredCategory}</td>
                            <td>`;

                if(other.checked == 0){
                    list += `<label class="badge badge-danger">답변대기</label>`;
                }else if(other.checked == 1){
                    list += `<label class="badge badge-primary">답변완료</label>`;
                }

                list += `</td> </tr>`;
            })
        }

        $(".otherList").html(list);
    })
}

//커뮤니티 신고 리스트 출력
function getCommunityReportList() {
    $.getJSON('/admin/getCommunityReportList', function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, communityReport) {
                list += `<tr>
                            <td class="pl-0" id="communityReportId">${communityReport.reportId}</td>
                            <td id="communityId">${communityReport.communityId}</td>
                            <td>${communityReport.reportReason}</td>
                            <td>
                                <button class="btn btn-danger btn-sm" id="showCommunityReportModal"
                                        onclick="getReportReason(${communityReport.reportId}, 'community');"
                                        data-bs-toggle="modal"
                                        data-bs-target="#confirmReportModal"
                                        type="button">승인
                                </button>
                            </td>
                        </tr>`;
            })
        }

        $(".communityReport").html(list);
    })
}

//회원 신고 리스트 출력
function getMemberReportList() {
    $.getJSON('/admin/getMemberReportList', function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, memberReport) {
                list += `<tr>
                            <td class="pl-0" id="memberReportId">${memberReport.reportId}</td>
                            <td id="memberId">${memberReport.memberId}</td>
                            <td>${memberReport.reportReason}</td>
                            <td>
                                <button class="btn btn-danger btn-sm" id="showMemberReportModal"
                                        onclick="getReportReason(${memberReport.reportId}, 'member');"
                                        data-bs-toggle="modal"
                                        data-bs-target="#confirmReportModal"
                                        type="button">승인
                                </button>
                            </td>
                        </tr>`;
            })
        }
        $(".memberReport").html(list);
    })
}

//상품 신고 리스트 출력
function getProductReportList() {
    $.getJSON('/admin/getProductReportList', function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, productReport) {
                list += `<tr>
                            <td class="pl-0" id="productReportId">${productReport.reportId}</td>
                            <td id="productId">${productReport.productId}</td>
                            <td>${productReport.reportReason}</td>
                            <td>
                                <button class="btn btn-danger btn-sm" id="showProductReportModal"
                                        onclick="getReportReason(${productReport.reportId}, 'product');"
                                        data-bs-toggle="modal"
                                        data-bs-target="#confirmReportModal"
                                        type="button">승인
                                </button>
                            </td>
                        </tr>`;
            })
        }
        $(".productReport").html(list);
    })
}

//사업자 회원 가입 승인 리스트 출력
function getCompanyJoinAcceptList() {
    $.getJSON('/admin/getCompanyJoinAcceptList', function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, company) {
                list += `<tr>
                            <td>` + (idx + 1) + `</td>
                            <td>${company.companyName}</td>
                            <td>${company.companyNumber}</td>
                            <td>
                                <button class="btn btn-danger btn-sm"
                                        onclick="getCompanyInfo('${company.companyId}')"
                                        id="showCompanyInfoModal"
                                        data-bs-toggle="modal"
                                        data-bs-target="#confirmCompanyInfoModal"
                                        type="button">승인
                                </button>
                            </td>
                        </tr>`;
            })
        }
        $(".companyJoinAccept").html(list);
    })
}

//전체 커뮤니티 리스트 출력
function getCommunityList() {
    $.getJSON('/admin/getCommunityList', function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, community) {
                list += `<div>
                        <tr th:id="${community.communityId}">
                            <td>${community.communityId}</td>
                            <td><a href="/community/${community.communityId}">${community.communityTitle}</a></td>
                            <td>${community.memberId}</td>
                            <td>${community.communityCategory}</td>
                            <td>${community.communityDate}</td>
                            <td class="text-success" id="report">${community.communityReport}
                            </td>
                            <td>
                                <button class="btn btn-danger btn-sm communityModal" type="button"
                                        id="${community.communityId}">게시글 삭제
                                </button>
                            </td>
                        </tr>
                    </div>`;
            })
        }

        $(".wholeCommunityData").html(list);
    })
}

//전체 회원 리스트 출력
function getMemberList() {
    $.getJSON('/admin/getMemberList', function (result) {
        var list = '';
        if (result.length > 0) {
            $.each(result, function (idx, member) {
                list += `<tr>
                            <td>` + (idx + 1) + `</td>
                            <td>${member.memberId}</td>
                            <td>${member.memberName}</td>
                            <td>${member.memberGender}</td>
                            <td>${member.memberJoinDate}</td>
                            <td class="text-success" id="report">${member.memberReport}</td>
                            <td>`;

                if (member.memberActivated) {
                    list += `<button class="btn btn-danger btn-sm memberModal" type="button" id="${member.memberId}">회원탈퇴/정지</button>`
                } else {
                    list += `<button class="btn btn-secondary btn-sm" type="button" disabled>~ ${member.memberStopDate}</button>`
                }
                list += `</td></tr>`;
            })
        }

        $(".wholeMemberData").html(list);
    })
}

//신고수가 3 이상이 되면 글씨 빨갛게 바꾸기
function reportColor() {
    if ($("#report").text() >= 3) {
        $("#report").attr("class", "text-danger");
    }
}

//재고 0인 상품 판매상태를 재고없음으로 바꿈
function setOutOfStock() {
    var size = $("input[name=productStock]").length;
    var productIdList = [];

    if (size > 0) {
        for (i = 0; i < size; i++) {
            if ($("input[name=productStock]").eq(i).val() === '0') {
                productIdList.push(i);
            }
        }

        $.ajax({
            url: "/admin/setOutOfStock",
            type: "get",
            data: {productIdList: productIdList},
            success: function () {
            }
        })
    }
}

//스크롤 위치를 따라 이동하는 사이드바 구현
function moveSideBar() {
    $('.float_sidebar').css('position', 'relative').css('z-index', '1');

    $(window).scroll(function () {
        yPosition = $(window).scrollTop();  //스크롤의 현재 위치
        if (yPosition < 0) {
            yPosition = 0;
        }
        $('.float_sidebar').animate({"top": yPosition}, {duration: 700, easing: 'linear', queue: false});
    });
}

//페이지 내 이동 구현
function movePage() {
    $('#goInquiryManage').click(function () {
        var offset = $('#inquiryManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });

    $('#goReportManage').click(function () {
        var offset = $('#reportManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });

    $('#goProductManage').click(function () {
        var offset = $('#productManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });

    $('#goCommunityManage').click(function () {
        var offset = $('#communityManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });

    $('#goMemberManage').click(function () {
        var offset = $('#memberManage').offset();
        $('html').animate({scrollTop: offset.top}, 400);
    });
}

//신고사유 모달
function getReportReason(targetId, type) {
    $.getJSON("/admin/getReportReason/" + targetId + "/" + type, function (data) {
        //클릭한 승인버튼에 맞는 신고 사유를 모달에 출력함 (이하 2줄)
        $("#reportReason").val(data.report.reportReason);
        $("#reportDetailReason").text(data.report.reportReasonDetail);

        //히든 값으로 리포트아이디, 타입, 신고대상의 아이디를 모달에 넘겨줌 (이하 8줄)
        $("#modalReportId").val(data.report.reportId);
        $("#modalTargetType").val(type);

        if (type === "product" || type === "community") {
            $("#modalTargetId").val(data.report.targetLongId);
        } else {
            $("#modalTargetId").val(data.report.targetStringId);
        }
    });
}

// 사업자 가입 안됨
//사업자 정보 불러오는 모달
function getCompanyInfo(companyId) {
    $.getJSON("/getCompanyInfo/" + companyId, function (data) {
        $("#modalCompanyName").val(data.companyName);
        $("#modalCompanyId").val(data.companyId);
        $("#modalCompanyPhoneNum").val(data.companyPhoneNum);
        $("#modalCompanyEmail").val(data.companyEmail);
        $("#modalCompanyNumber").val(data.companyNumber);

        //사업자 번호 상태확인
        var number = {
            "b_no": [data.companyNumber] //사업자 번호 받아옴
        };

        $.ajax({
            url: "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=svcYNFjOh38o5jqJ7WAjnGxrQbGB7iFHkuLaWMdiulZa61RK3DpXgaFdClE%2F6xNkMEusenNBIBj5%2BoFIDCGiiw%3D%3D",  //내가 발급받은 사용 key 번호
            type: "POST",
            data: JSON.stringify(number),
            dataType: "JSON",
            contentType: "application/json",
            accept: "application/json",
            success: function (result) {
                $("#modalCompanyNumberConfirm").val(result.data[0].tax_type);  //국세청에 등록되어있는 사업자번호인지 확인
            },
            error: function (result) {
                console.log(result.responseText);
            }
        });
    });
}