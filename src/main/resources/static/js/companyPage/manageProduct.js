$(document).ready(function(){
    getProductList();
})

function getProductList(){
    $.getJSON('/product/manageProduct', function(result){
        var list = '';
        if(result.length > 0){
            $.each(result, function(idx, product) {
                list = `<tr id="productData`+ idx +`">
                                    <td class="pl-0" id="productId">`+ product.productId +`</td>
                                    <td>` + product.productName + `</td>
                                    <td>` + product.productDiv + `</td>
                                    <td>` + product.productPrice + `</td>
                                    <td>` + product.productStock + `</td>
                                    <td>` + product.productStatus + `</td>
                                    <td>0</td>
                                    <td>` + product.productReport + `</td>
                                </tr>`;

                $(".productData").append(list);
            })
        }else{
            $("#noData").text("등록된 상품이 없습니다.");
        }
    })
}