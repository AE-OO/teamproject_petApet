$(document).ready(function() {
    getProductList();
    
});

//회사 마이페이지에 문의 내역 리스트 출력
function getProductList(){
    $.getJSON('/product/manageProduct', function(result){
        var list = '';
        if(result.length > 0){
            $.each(result, function(idx, product) {
                list += `<tr id="productData`+ idx +`" onclick="showProductDetailInfo(`+ product.productId + `,'` + product.productName + `',` + product.productStock + `,'` + product.productStatus + `')">
                                    <td class="pl-0" id="productId">`+ product.productId +`</td>
                                    <td><a th:href="#">` + product.productName + `</a></td>
                                    <td>` + product.productDiv + `</td>
                                    <td>` + product.productPrice + `</td>
                                    <td>` + product.productStock + `</td>
                                    <td>` + product.productStatus + `</td>
                                    <td>0</td>
                                    <td>` + product.productReport + `</td>
                                </tr>`;
            })
        }else{
            $("#noData").text("등록된 상품이 없습니다.");
        }

        $(".productData").html(list);
    })
}