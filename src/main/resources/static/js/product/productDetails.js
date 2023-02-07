let totalPage = 10;
let $productTitle = $('.productTitle');
let productId = $productTitle.attr('data-id');
let productType = $productTitle.attr('aria-label');
let quantity = 1;

$('#quantity').change(function () {
    quantity = $('#quantity').val();
});

function updateReviewForm() {
    $('.myReview').remove();
    $('#myReviewForm').attr('style', 'display: ');
}

$('#reviewDeleteBtn').click(function () {
    {
        fetch('/product/deleteReview', {
            method: 'POST',
            headers: {
                "Content-Type": "application/text",
            },
            body: productId,
        })
            .then(response => {
                // return response.json();
            })
            .then(data => {

            })
    }
    window.location.href = '/product/' + productType + '/' + productId + '/details';
})

$('.reviewImgModalBtn').click(function () {
    let imgSrc = $(this).children(1).attr('src');
    let $modalImage = $('.modalImage');
    $modalImage.attr('src', imgSrc);
})

function moreReviewImgModalBtn(src) {
    let lastIndex = src.lastIndexOf("/");
    let s = src.substring(lastIndex + 1);
    let $modalImage = $('.modalImage');
    $modalImage.attr('src', '/product/images/' + s);
}

function deleteReviewImg(id) {
    let productId = $("#productId").val();
    let $this = $('#' + id);
    let next = $this.next(1).children(1);
    let attrSrc = next.attr('src');
    let attrAlt = next.attr('alt');
    let pos = attrSrc.lastIndexOf("/");
    let substringSrc = attrSrc.substring(pos + 1);
    fetch('/product/deleteReviewImg?productId=' + productId, {
        method: 'POST',
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            substringSrc: substringSrc,
            attrAlt: attrAlt,
        }),
    })
        .then(response => {
            // return response.json();
        })
        .then(data => {

        })
    next.remove()
    $this.remove()
}

async function updateReview() {
    let storeFileNameArr = new Array();
    let uploadFileNameArr = new Array();
    let myReviewImgSize = $('.myReviewImg img[class=reviewImage]').length;
    for (var i = 0; i < myReviewImgSize; i++) {
        let src = $('.myReviewImg img[id=reviewImage' + i + ']').attr('src');
        let uploadFileName = $('.myReviewImg img[id=reviewImage' + i + ']').attr('alt');
        let pos = src.lastIndexOf("/");
        let storeFileName = src.substring(pos + 1);
        storeFileNameArr.push(storeFileName);
        uploadFileNameArr.push(uploadFileName);
    }
    $('#appendTarget').empty();
    let formData = new FormData(document.getElementById("myReviewForm"));
    for (var o = 0; o < storeFileNameArr.length; o++) {
        formData.append('storeFileName', storeFileNameArr[o])
        formData.append('uploadFileName', uploadFileNameArr[o])
        formData.append('uploadFile', 'UploadFile(uploadFileName=' + uploadFileNameArr[o] + ', storeFileName=' + storeFileNameArr[o] + ') ')
    }
    fetch('/product/updateReview?productId=' + productId, {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            // return response.json();
        })
        .then(data => {

        })
    window.location.href = '/product/' + productType + '/' + productId + '/details';
}

$('.addDibs').click(function () {

    $.ajax({
        type: 'post',
        url: '/product/dibs/add',
        async: true,
        dataType: 'text',
        data: {"productId": productId},
        success: function (result) {
            if (result === 'login') {
                alert('로그인이 필요 합니다.')
            }
            if (result === 'duplicate') {
                alert('찜하기 완료')
            } else {
            }
        },
        error: function (request, status, error) {
            alert(request.responseText);
        }
    })

})

function review_scroll_move() {
    let offset = $("#reviews").offset();
    $("html, body").animate({scrollTop: offset.top});
};

function shipping_scroll_move() {
    let offset = $("#shipping").offset();
    $("html, body").animate({scrollTop: offset.top});
};

function productInquiry_scroll_move() {
    let offset = $("#productInquiry").offset();
    $("html, body").animate({scrollTop: offset.top});
};

function productDetail_scroll_move() {
    let offset = $("#productDetail").offset();
    $("html, body").animate({scrollTop: offset.top});
};

$('.carousel-inner > div:nth-child(1)').addClass('active');
$('.carousel-indicators > button:nth-child(1)').addClass('active');

function setThumbnail(event) {
    for (var image of event.target.files) {
        var reader = new FileReader();

        reader.onload = function (event) {
            var img = document.createElement("img");
            img.setAttribute("src", event.target.result);
            img.width = 100;
            img.height = 100;
            document.querySelector("div #myform").appendChild(img);
        };
        reader.readAsDataURL(image);
    }
}

$(document).ready(function () {

    var size = $('p[class = star]').length;
    for (var i = 0; i < size; i++) {
        $('p[class = star]').eq(i).attr('id', "star" + i);

        $("#star" + i).find(".my_star").filter(function (k, v) {
            return $(v).attr("value") <= $("#star" + i).attr('value') ? $(v).addClass("on") : $(v).removeClass("on");
        });
    }
})


function moreReview() {

    let nextPage = (totalPage / 10);

    let url = '/product/moreReview?productId=' + productId + '&page=' + nextPage;
    $.getJSON(url,
        function (result) {
            $.each(result, function (idx, el) {
                let reviewHtml =
                    `<div class="col-1">
                            <img src="/img/petSnack.jpg" class="img-fluid">
                        </div>
                        <div class="col-1 me-2">
                            <h6>${result[idx].reviewMember}</h6>
                            <h6 style="font-size: 0.5rem" class="text-secondary mt-1">${result[idx].reviewDate}</h6>
                        </div>
                        <div class="col-2" style="position: center">
                            <p class="star" id="moreRating">
                                <a class="my_star" value="1">★</a>
                                <a class="my_star" value="2">★</a>
                                <a class="my_star" value="3">★</a>
                                <a class="my_star" value="4">★</a>
                                <a class="my_star" value="5">★</a>
                            </p>
                        </div>
                        <div class="col-7" id="imgArea${idx}">
                        </div>
                        <div class="col text-end">
                            <button type="button" class="btn btn-outline-primary memberReport" id="addMemberReport"
                                            data-bs-toggle="modal" data-bs-target="#addReportModal"
                                            title="신고">신고
                            </button>
                        </div>
                        <div class="d-flex mt-2">
                            <div class="me-2">
                                <h6>상품명</h6>
                            </div>
                            <div class="col-3">
                                <h6 class="opacity-75">${result[idx].reviewProduct}</h6>
                            </div>
                        </div>
                        <div class="review_comment">
                            <div class="review_content">
                                <div class="contentStr">
                                ${result[idx].reviewContent}
                                </div>
                            </div>
                        </div>
                        <hr class="my-4">`;
                $('#tagArea:last').append(reviewHtml);
                $.each(result[idx].reviewImg, function (index) {
                    let aTag = document.createElement('a');
                    aTag.setAttribute('class', 'btn btn-link');
                    aTag.setAttribute('data-bs-toggle', 'modal');
                    aTag.setAttribute('data-bs-target', '#reviewImgModal');
                    let moreReviewImg = document.createElement('img');
                    moreReviewImg.setAttribute('class', 'reviewImage');
                    moreReviewImg.setAttribute('onclick', 'moreReviewImgModalBtn(this.src)');
                    moreReviewImg.src = '/product/images/' + result[idx].reviewImg[index].storeFileName;
                    moreReviewImg.width = 100;
                    moreReviewImg.height = 100;
                    $('#imgArea' + idx).append(aTag);
                    aTag.appendChild(moreReviewImg);
                })

                $('#moreRating').attr('value', result[idx].reviewRating);
                $('#moreReviewProduct').text(result[idx].reviewProduct);
                $('#moreReview').attr('id', 'moreReview');
                var size = $('p[class = star]').length;
                for (var i = 0; i < size; i++) {
                    $('p[class = star]').eq(i).attr('id', "moreRating" + i);

                    $("#moreRating" + i).find(".my_star").filter(function (k, v) {
                        return $(v).attr("value") <= $("#moreRating" + i).attr('value') ? $(v).addClass("on") : $(v).removeClass("on");
                    });
                }
            })
            if (result.length == 0) {
                let noMoreReview =
                    `<div><h6 class="m-lg-1 fs-5 text-center">더 이상 리뷰가 존재하지 않습니다.</h6></div>`;
                $('#moreReview').hide();
                $('#tagArea:last').append(noMoreReview);
            }
        }
    )
    totalPage += 10;
}

const btnMinus = $('#minus');
const btnPlus = $('#plus');
const quantityConst = $('#quantity');
var urlCart = "/cart/add";

$(btnMinus).click(function () {
    var $input = $(this).parents().eq(2).find(quantityConst);
    var count = parseInt($input.val()) - 1;
    count = count < 1 ? 1 : count;
    $input.val(count).change();
});

$(btnPlus).click(function () {
    var $input = $(this).parents().eq(2).find(quantityConst);
    let remainStock = $('#remainStock').text();
    let quantityVal = $("#quantity").val();
    if (parseInt(quantityVal) < parseInt(remainStock)) {
        $input.val(parseInt($input.val()) + 1).change();

    } else {
        const excessQuantityHtml = `
            <h6 class="text-secondary osh">재고가 부족합니다</h6>`;
        $('.osh').remove()
        $('#quantityInputBox').append(excessQuantityHtml);
    }
});

var postUrl = "/cart";

var urlCart = "/cart/add";
var urlBuy = "/buy/addByProduct";
var quantityVal = quantity;
var param = {"product": productId, "quantity": quantityVal};

$('#addCart').click(function () {

    var param = {"product": productId, "quantity": quantity};

    $.ajax({
        url: urlCart,
        type: "POST",
        data: JSON.stringify(param),
        dataType: "text",
        contentType: "application/json",
        charset: "UTF-8",
        success: function (data) {
            if (confirm("장바구니로 이동 하시겠습니까?") == true) {
                location.replace(postUrl)
            } else {

            }
        },
        error: function (jqXHR, status, errorThrown) {
            alert(jqXHR.responseText);
        }
    });
})

$('#addBuyBtn').click(function () {
    location.href = '/direct/checkout?productId=' + productId + '&quantity=' + quantity;
})