let urlSearchParams = new URLSearchParams(location.search);
let searchParam = urlSearchParams.get("searchContent");

$.ajax({
    url: '/searchResult?searchContent=' + searchParam,
    success: function (data) {
        $.each(data.content, function (idx, val) {
            let productId = val.productId;
            let productCategory = val.productCategory;
            val.productPrice = priceToString(val.productPrice) + '원';
            const html = `<tr class="productRow border-white">
                            <td class="productCategory">${val.productDiv}</td>
                            <td class="text-start">
                                <a class="me-2 productName">${val.productName}</a></td>
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
            setMoveProduct(productId, productCategory);
            $('#list_product').append(html);
        })
        setHrefAndConst(searchParam);
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

function setMoveProduct(productId, productCategory) {
    const q = "/product/" + productCategory + "/" + productId + "/details";
    $('.productName').prop('href', q);
}

