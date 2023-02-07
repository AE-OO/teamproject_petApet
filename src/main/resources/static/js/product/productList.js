var size = $('p[class = star]').length;
for (var i = 0; i < size; i++) {
    $('p[class = star]').eq(i).attr('id', "star" + i);

    $("#star" + i).find(".my_star").filter(function (k, v) {
        return $(v).attr("value") <= $("#star" + i).attr('value') ? $(v).addClass("on") : $(v).removeClass("on");
    });
}

function addDibs(id) {
    $.ajax({
        type: 'post',
        url: '/product/dibs/add',
        async: true,
        dataType: 'text',
        data: {"productId": id},
        success: function (result) {
            if (result === 'login') {
                location.href = '/login'
            }
            if (result === 'duplicate') {
            } else {
            }
        },
        error: function (request, status, error) {
        }
    })
}

const addCartBtn = $('.addCart');  // 상품 리스트에서 장바구니로 상품을 추가하는 기능

var postUrl = "cart";

$(addCartBtn).click(function () {
    var productId = $(this).val();
    var quantity = 1;
    var param = {"product": productId, "quantity": quantity};
    $.ajax({
        url: "/cart/add",
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
});

let urlSearchParams = new URLSearchParams(location.search);

$(document).ready(setActive());

function setActive() {

    const sortType = urlSearchParams.get('sortType');
    const productCategory = urlSearchParams.get('category');
    const pageNumber = urlSearchParams.get('page');
    if (sortType === null || sortType === '') {
        $("input:radio[name ='btnradio']:input[value=productId]").attr("checked", true);
    } else {
        $("input:radio[name ='btnradio']:input[value=" + sortType + "]").attr("checked", true);
    }
    $("ul[id = 'paginationBox'] li[value=" + pageNumber + ']').attr("class", 'page-item active');
    $("ul[id = 'portfolio-flters'] li[value=" + productCategory + ']').attr("class", 'mx-2 active');
}

function focusSearchBar() {
    $('#searchBar').focus();
}

$(document).ready(isRating());

function isRating() {
    let rating = urlSearchParams.get('rating');
    if (rating != 0 && rating != null) {
        const appendActivatedFilter = `<button class="btn btn-outline-secondary" id="ratingFilter">${rating}점 이상 <i class="bi bi-x-square ms-1"></button>`;
        $('#filter').append(appendActivatedFilter);
    }
    $('#ratingFilter').click(function () {
        urlSearchParams.set('rating', '0');
        location.href = '/product?' + urlSearchParams.toString();
    })

}

$('#priceSearchBtn').click(function () {
    let minPriceVal = $('input[name = minPrice]').val();
    let maxPriceVal = $('input[name = maxPrice]').val();
    urlSearchParams.set('isPriceRange', 'true');
    urlSearchParams.set('minPrice', minPriceVal);
    urlSearchParams.set('maxPrice', maxPriceVal);
    location.href = '/product?' + urlSearchParams.toString();
})

$(document).ready(isPriceRange);

function isPriceRange() {
    let isPriceRange = urlSearchParams.get('isPriceRange');
    let minPrice = urlSearchParams.get('minPrice');
    let maxPrice = urlSearchParams.get('maxPrice');
    if (isPriceRange === 'true' && maxPrice !== '' && minPrice !== '') {
        const appendPriceFilter = `<button class="btn btn-outline-secondary" id="priceFilter">${minPrice} ~ ${maxPrice}원 <i class="bi bi-x-square ms-1"></i></button>`;
        $('#filter').append(appendPriceFilter);
    } else if (isPriceRange === 'true' && maxPrice === '') {
        const appendPriceFilter = `<button class="btn btn-outline-secondary" id="priceFilter">${minPrice}원 이상<i class="bi bi-x-square ms-1"></i></button>`;
        $('#filter').append(appendPriceFilter);
    } else if (isPriceRange === 'true' && minPrice === '') {
        const appendPriceFilter = `<button class="btn btn-outline-secondary" id="priceFilter">${maxPrice}원 이하<i class="bi bi-x-square ms-1"></i></button>`;
        $('#filter').append(appendPriceFilter);
    }
    $('#priceFilter').click(function () {
        urlSearchParams.set('isPriceRange', 'false');
        urlSearchParams.set('minPrice', '');
        urlSearchParams.set('maxPrice', '');
        location.href = '/product?' + urlSearchParams.toString();
    })
}

$(document).ready(searchContent());
$(document).ready(isSearch());

function searchContent() {
    $('#contentSearchBtn').click(function () {
        let category = $('#category').val();
        let searchContent = $('#searchBar').val();
        let searchUrl = 'product?category=' + category + '&size=20&page=1&sortType=productId&searchContent=' + searchContent + '&rating=0&isPriceRange=false&minPrice=&maxPrice='
        location.href = searchUrl;
    })
}

function isSearch() {
    let searchContent = urlSearchParams.get('searchContent');
    if (searchContent != null && searchContent !== 'false') {
        const appendSearchFilter = `<button class="btn btn-outline-secondary" id="searchFilter">'${searchContent}'검색<i class="bi bi-x-square ms-1"></i></button>`;
        $('#filter').append(appendSearchFilter);
    }
    $('#searchFilter').click(function () {
        urlSearchParams.set('searchContent', 'false');
        location.href = '/product?' + urlSearchParams.toString();
    })
}