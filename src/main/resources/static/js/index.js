// window.kakaoAsyncInit = function () {
//     Kakao.Channel.createChatButton({
//         container: '#kakao-talk-channel-chat-button',
//     });
// };

// (function (d, s, id) {
//     var js, fjs = d.getElementsByTagName(s)[0];
//     if (d.getElementById(id)) return;
//     js = d.createElement(s);
//     js.id = id;
//     js.src = 'https://t1.kakaocdn.net/kakao_js_sdk/2.0.0/kakao.channel.min.js';
//     js.integrity = 'sha384-qN6njjEgLQzM0N/uuB26JXrlFnwdctF+owkJc/pTqUpi1Qrp9xAvRKYIkzH5XcJU';
//     js.crossOrigin = 'anonymous';
//     fjs.parentNode.insertBefore(js, fjs);
// })(document, 'script', 'kakao-js-sdk');

$(document).ready(sortProduct('productViewCount'));

async function sortProduct(sortType) {
    $('#appendTarget').empty();
    fetch('/product/sort?sortType=' + sortType, {
        method: 'GET',
    })
        .then(response => {
            return response.json();
        })
        .then(data => {
            $.each(data, function (idx) {
                const productListHtml = `
                <div class="col-lg-3 col-md-6 portfolio-item first wow fadeInUp" data-wow-delay="0.1s">
                <div class="portfolio rounded">
                    <img class="" src="/product/images/${data[idx].productImg.storeFileName}" width="300" height="300" alt="">
                    <div class="portfolio-text" style="opacity: 1;">
                        <h1 class="text-start rounded ms-2">
                            <a style="text-decoration: none;" class="text-dark fs-5" href="/product/food/${data[idx].productId}/details">${data[idx].productName}</a>
                        </h1>
                        <div class="d-flex flex-row">
                            <div>
                                <fieldset class="p-1 col">
                                    <div class="d-flex flex-row" style="max-height: 1rem; margin-top: 0.1rem">
                                        <p class="star" value="${data[idx].productRating}" id="star0">
                                            <a class="my_star" value="1">★</a>
                                            <a class="my_star" value="2">★</a>
                                            <a class="my_star" value="3">★</a>
                                            <a class="my_star" value="4">★</a>
                                            <a class="my_star" value="5">★</a>
                                        </p><p>
                                    </p></div>
                                </fieldset>
                            </div>
                            <h6 class="align-self-center mt-2">(${data[idx].productReview})</h6>
                        </div>
                        <div class="d-flex flex-column">
                            <div class="col">
                                <h1 class="fs-6 text-end" id="priceToString${idx}"></h1>
                            </div>
                            <div class="d-flex flex-row">
                                <div class="d-flex flex-fill justify-content-sm-end btn-group-sm">
                                    <button type="button" class="btn bg-secondary me-1 addCart" value="${data[idx].productId}" onclick="location.href='/product/food/${data[idx].productId}/details'">장바구니
                                        <input type="hidden" class="quan" value="1"></button>
                                    <a class="btn bg-secondary me-1 addBuy" href="/product/food/${data[idx].productId}/details">바로구매
                                        <input type="hidden" class="quan" name="quantity" value="1"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`;
                var size = $('p[class = star]').length;
                for (var i = 0; i < size; i++) {
                    $('p[class = star]').eq(i).attr('id', "moreRating" + i);

                    $("#moreRating" + i).find(".my_star").filter(function (k, v) {
                        return $(v).attr("value") <= $("#moreRating" + i).attr('value') ? $(v).addClass("on") : $(v).removeClass("on");
                    });
                }
                $('#appendTarget').append(productListHtml);
                $('#priceToString' + idx).text(priceToString(data[idx].productPrice) + '원')
            })
        });
}

function priceToString(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

$(function () {
    getIndexCommunityList();
    // getPopularCommunityList();
});
getIndexCommunityList = function () {
    $.getJSON('/community/getIndexList', function (data) {
        showList(data.latest,"latest");
        showList(data.popular,"popular");
        showList(data.notice,"notice");
    })
}

showList = function (data, classification) {
    let str = ``
    if (data.content.length === 0) {
        str += `<li class="text-danger">작성된 글이 없습니다</li>`;
    } else {
        $.each(data.content, function (idx, val) {
            str += `<li class="text-truncate">
                        <span class="text-lightGray ps-0">${idx + 1}.</span>
                        <a href="/community/${val.communityId}" class="ps-0 text-dark">
                        <span class="text-danger me-2">${val.communityCategory}</span>`
            if (val.communitySubCategory != null) {
                str += `<span class="text-lightGray me-2">${val.communitySubCategory}</span>`
            }
            str += `<span>${val.communityTitle}</span></a></li>`
        })
    }
    $(`#${classification}List`).html(str);
    // $('#' + classification + 'List').html(str);
}