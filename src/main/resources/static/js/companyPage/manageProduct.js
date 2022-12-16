$(document).ready(function(){
    getProductList();

    //재고반영 버튼 클릭 시
    $("#detailStockBTN").click(function(){
        $.ajax({
            url: "/product/updateStock/" + $("#detailProductId").val(),
            method: "post",
            data : {productStock : $("#detailStock").val()},
            success: function(data){
                getProductList();
            }
        });
    });
    
    //판매상황반영 버튼 클릭 시
    $("#detailStatusBTN").click(function(){
        $.ajax({
            url: "/product/updateStatus/" + $("#detailProductId").val(),
            method: "post",
            data : {productStatus : $("#detailStatus").val()},
            success: function(data){
                getProductList();
            }
        });
    });
})

//회사가 등록한 상품 리스트 출력
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

//선택한 상품의 세부사항 표시
function showProductDetailInfo(productId, productName, productStock, productStatus){
    $("#detailProductId").val(productId);
    //선택한 상품의 이름 표시
    $("#detailName").text("[" + productName + "] 상품 세부사항");
    //선택한 상품의 재고 표시
    $("#detailStock").val(productStock);
    //선택한 상품의 판매상황 표시
    $("#detailStatus").val(productStatus).attr("selected","selected");
}