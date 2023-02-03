let urlSearchParams = new URLSearchParams(location.search);
let searchParam = urlSearchParams.get("searchContent");

$.ajax({
    url: '/searchResult?searchContent=' + searchParam,
    success: function (data) {
        let productRes = data.product;
        let communityRes = data.community;

        if (productRes.content.length == 0) {
            const html = `<tr><td class="py-5" colspan="5"><strong>검색 결과가 없습니다</strong></td></tr>`
            $('#list_product').append(html);
        } else {
            $.each(productRes.content, function (idx, val) {
                let productId = val.productId;
                let productCategory = val.productCategory.toString().toLowerCase();
                val.productPrice = priceToString(val.productPrice) + '원';
                const html = `<tr class="productRow">
                            <td class="productCategory">${val.productDiv}</td>
                            <td class="text-start">
                                <a class="me-2 productName" href="/product/${productCategory}/${productId}/details">${val.productName}</a></td>
                            <td class="productPrice">
                               ${val.productPrice}
                            </td>
                            <td class="productRating">
                               <fieldset class="p-1">
                                  <div class="d-flex flex-row" style="max-height: 1rem; margin-top: 0.1rem">
                                     <p class="star" value="${val.productRating}">
                                        <a class="my_star" value="1">★</a>
                                        <a class="my_star" value="2">★</a>
                                        <a class="my_star" value="3">★</a>
                                        <a class="my_star" value="4">★</a>
                                        <a class="my_star" value="5">★</a>
                                     <p>
                                  </div>
                               </fieldset>
                            </td>
                            <td class="productViewCount">${val.productViewCount}</td>
                        </tr>`
                $('#list_product').append(html);
            })
        }
        setHrefAndConst(searchParam);
        setCommunityResult(communityRes);
        setResult(productRes.totalElements, communityRes.totalElements);
        let size = $('p[class = star]').length;
        for (let i = 0; i < size; i++) {
            $('p[class = star]').eq(i).attr('id', "star" + i);

            $("#star" + i).find(".my_star").filter(function (k, v) {
                return $(v).attr("value") <= $("#star" + i).attr('value') ? $(v).addClass("on") : $(v).removeClass("on");
            });
        }
    },
    error: function (error) {
    }
});

function priceToString(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

function setHrefAndConst(searchParam) {
    const q = "/product?searchContent=" + searchParam;
    $('.productSearchConst').text("'" + searchParam + "' 상품 검색 결과")
    $('.moveProductList').prop('href', q);
}

function setResult(productTotalElements, communityTotalElements) {
    const product = `<span class="ms-2 fw-normal text-body small">(총 ${productTotalElements}건)</span>`;
    const community = `<span class="ms-2 fw-normal text-body small">(총 ${communityTotalElements}건)</span>`;

    $("#searchContentRes").text(`'${searchParam}'`);
    $("#totalElements").text(productTotalElements + communityTotalElements);
    $("#searchContent").val(searchParam)
    $('.productSearchConst').append(product);
    $('.communitySearchConst').append(community);
};

function setCommunityResult(communityRes) {
    const url = `/community/search?type=titleContent&searchContent=${searchParam}`;
    $('.communitySearchConst').text(`'${searchParam}' 커뮤니티 검색 결과`)
    $('.moveCommunityList').prop('href', url);
    showCommunitySearchOrMemberWritingList(communityRes);
    $(".communityContent").remove();
    $("input[name=deleteCheck]").remove();
}

$(function () {
    $("#integratedSearchBtn").click(function () {
        if ($("#searchContent").val() === null || $("#searchContent").val() === '') {
            alert("내용을 입력해 주세요.")
        } else {
            window.location = "/search?searchContent=" + $("#searchContent").val();
        }
    })
});