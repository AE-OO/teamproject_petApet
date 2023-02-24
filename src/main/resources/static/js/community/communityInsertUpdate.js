const categoryList = {
    거래: ['나눔합니다', '팝니다', '삽니다'],
    추천: ['용품', '동물병원', '기타'],
    일상: ['강아지', '고양이', '소동물']
}
subCategory = (category) => {
    $("#subCategory").text(category);
    if (category === '말머리없음') {
        $("input[name=communitySubCategory]").val('');
        return;
    }
    $("input[name=communitySubCategory]").val(category);
}

getSubCategoryList = (key) => {
    let subCategoryHtml = '<li><a class="dropdown-item subCategory" href="javascript:">말머리없음</a></li>';
    const list = categoryList[key];
    for (let i = 0; i < list.length; i++) {
        subCategoryHtml += '<li><a class="dropdown-item subCategory" href="javascript:">'+list[i]+'</a></li>';
    }
    $('#subCategoryUl').html(subCategoryHtml);
}

//community insert + update 공통
$(function () {
    //insert일 떄 초기 화면에서 서브 카테고리는 숨김
    if($(location).attr('pathname').split("/")[2] === "insert"){
        $("#subCategoryDropdown").hide();
        //관리자 로그인 시 메인 카테고리는 무조건 '공지사항'으로 들어가게 함
        $("input[name=communityCategory]").val($("#adminCategory").text());
    }
    if($(location).attr('pathname').split("/")[2] === "update" && $("#adminCategory").text() === ''){
        getSubCategoryList(category);
    }

    //메인 카테고리 선택 시 서브 카테고리 목록 보여줌
    $("#categoryUl").children().click(function () {
        $("#category").text($(this).text());
        if($(location).attr('pathname').split("/")[2] === "insert"){
            $("#subCategoryDropdown").show();
            $("#subCategory").text('말머리없음');
        }
        getSubCategoryList($(this).text());
        $("input[name=communityCategory]").val($(this).text());
    });
    //취소 버튼
    $("#cancelBtn").click(function () {
        window.history.back();
    })
    //서브 카테고리 클릭 시
    $(document).on("click", ".subCategory", function () {
        subCategory($(this).text());
    });

    $("#updateSubmitBtn").click(function () {
        if($("input[name=communityCategory]").val() ==='' || $("input[name=communityCategory]").val() === null){
            alert("말머리를 선택해주세요");
            return;
        } else if($("input[name=communityTitle]").val() ==='' || $("input[name=communityTitle]").val() === null){
            alert("제목을 작성해주세요");
            return;
        }else if($("#summernoteContent").val() === '' || $("#summernoteContent").val() === null){
            alert("내용을 작성해주세요");
            return;
        } else{
            let arr = [];
            let deleteImg ;
            let compareImg;
            let inputDeleteImg;
            let inputCommunityId;
            deleteImg = document.createElement('div');
            deleteImg.innerHTML = $("#summernoteContent").text();
            compareImg = document.createElement('div');
            compareImg.innerHTML = $("#summernoteContent").val();
            $(deleteImg).find("img").each(function(idx,val){
                arr.push($(val).attr('src').substring(16))
                $(compareImg).find("img").each(function(idx2,val2){
                    if($(val).attr('src').substring(16) === $(val2).attr('src').substring(16)){
                        arr.pop($(val).attr('src').substring(16));
                    }
                });
            });
            inputDeleteImg = document.createElement('input');
            inputDeleteImg.setAttribute('type', 'hidden');
            inputDeleteImg.setAttribute('name', 'deleteImg[]');
            inputDeleteImg.setAttribute('value', arr);
            inputCommunityId = document.createElement('input');
            inputCommunityId.setAttribute('type', 'hidden');
            inputCommunityId.setAttribute('name', 'communityId');
            inputCommunityId.setAttribute('value', $("#summernoteContent").data("value"));
            $("input[name=communitySubCategory]").after(inputDeleteImg);
            $("input[name=communitySubCategory]").after(inputCommunityId);
            $("#updateSubmitBtn").attr("type", "submit");
        }
    });

})
