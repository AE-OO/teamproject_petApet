$(getDibsList())

function getDibsList() {
    fetch('/product/dibs/getDibs', {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        }
    })
        .then((response) => response.json())
        .then((dibs) => {
            let dibsList = '';
            if (dibs.length === 0) {
                dibsList = `<div><h6 class="text-center">찜한 상품이 없습니다</h6></div>`;
                $('.dibs').html(dibsList);
            } else {

                $.each(dibs, function (idx, dib) {
                    const dibFrom = `<tr class="sc-gnmni8-3 gmGnuU dibs">
         <td class="sc-gnmni8-5 hUzAOG">
             <div class="sc-ki5ja7-0 bQVZKC">
                 <div class="sc-ki5ja7-1 krPkOP">
                 </div>
                 <div class="sc-ki5ja7-2 grCKyH">
                     <button size="28"
                             class="sc-izcbvi-0 bScTTg">
                         <div class="sc-izcbvi-1 JHhgQ"></div>
                         <div class="sc-izcbvi-1 JHhgQ"></div>
                         <div class="sc-izcbvi-1 JHhgQ"></div>
                     </button>
                 </div>
             </div>
             <div class="d-flex col">
                 <a class="col-3"
                    href="/product/${dib.productType.toString().toLowerCase()}/${dib.productId}/details">
                     <img style="width: 100%; height: auto"
                          src="/product/images/${dib.productImg}">
                 </a>
                 <div class="col-6 p-lg-3">
                     <span>
                         <a style="font-size: 1.5rem" href="/product/${dib.productType.toString().toLowerCase()}/${dib.productId}/details">
                         ${dib.productName}
                         </a>
                     </span>
                     <div class="text-secondary dibProductPrice">${dib.productPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원</div>
                 </div>
                 <div class="col-3">
                     <div class="p-2">
                         <button class="fUUUKW p-2" data-id="${dib.productId}">장바구니 담기</button>
                     </div>
                     <div class="p-2">
                         <button class="fTrGbC p-2" data-id="${dib.productId}">찜하기 취소</button>
                     </div>
                 </div>
             </div>
         </td>
     </tr>`;

                    dibsList += dibFrom;
                });
            }

            $('.dibs').html(dibsList);

            $('.fUUUKW').click(function () {
                let productId = $(this).attr('data-id');
                let quantity = 1;
                fetch('/direct/checkout/checkLogin', {
                    method: 'GET',
                })
                    .then((response) => response.text())
                    .then((data) => {
                        if (data !== 'ok') {
                            alert(data);
                        } else {
                            let isExist = checkCart(productId);
                            if (isExist === true) {
                                alert('이미 장바구니에 상품이 있습니다.');
                                if (confirm("장바구니로 이동 하시겠습니까?") == true) {
                                    location.href = "/cart"
                                }
                            } else {
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
                                            location.href = "/cart"
                                        } else {

                                        }
                                    },
                                    error: function (jqXHR, status, errorThrown) {
                                        let result = JSON.parse(jqXHR.responseText);
                                        alert(result.message)
                                    }
                                });
                            }
                        }
                    })
            });

            $('.fTrGbC').click(function () {
                let productId = $(this).attr('data-id');
                fetch('/product/dibs/remove', {
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: productId
                })
                    .then((response) => response.text())
                    .then((data) => {
                        if (data === 'ok') {
                            alert("찜하기 취소했습니다")
                            getDibsList()
                        }
                    })
                    .catch();
            });

            function checkCart(productId) {
                let isExist = '';
                $.ajax({
                    url: "/cart/isExist?productId=" + productId,
                    type: "GET",
                    async: false,
                    success: function (data) {
                        isExist = data;
                    },
                    error: function (jqXHR, status, errorThrown) {
                    }
                })
                return isExist;
            }


        })
}



