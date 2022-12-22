$(document).ready(function(){
    getProductList();

    //테이블의 행 클릭 시
    $("#productTable").on("click", "tr", function(){
        //숨겨놨던 세부사항 표시 부분을 보이게 함
        $("#productDetailInfo").css("display","");
        //클릭한 행의 데이터 가져옴
        var tr = $(this);
        //선택한 상품의 PK 넘김(수정과 삭제를 위해서)
        $("#detailProductId").val(tr.children().eq(0).text());
        //선택한 상품의 이름 표시
        $("#detailName").text("[" + tr.children().eq(1).text() + "] 상품 세부사항");
        //선택한 상품의 재고 표시
        $("#detailStock").val(tr.children().eq(4).text());
        //선택한 상품의 판매상황 표시
        $("#detailStatus").val(tr.children().eq(5).text()).attr("selected","selected");

    })

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

    //상품 세부사항의 X 버튼 클릭 시
    $("#closeDetailInfoBTN").click(function(){
        //세부사항 정보를 다시 안보이게 함
        $("#productDetailInfo").css("display","none");
    })
})

//회사가 등록한 상품 리스트 출력
function getProductList(){
    $.getJSON('/product/manageProduct', function(result){
        var list = '';
        if(result.length > 0){
            $.each(result, function(idx, product) {
                list += `<tr id="productData`+ idx +`">
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