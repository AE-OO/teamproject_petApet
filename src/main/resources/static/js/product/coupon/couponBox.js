function getCouponList(url) {

fetch(url)
    .then((response) => response.json())
    .then((data) => {
            $.each(data.content, function (idx, coupon) {
                switch (coupon.couponAcceptType) {
                    case "ALL":
                        coupon.couponAcceptType = "전체상품";
                        break;
                    case "FOOD":
                        coupon.couponAcceptType = "사료";
                        break;
                    case "SNACK":
                        coupon.couponAcceptType = "간식";
                        break;
                    case "TOY":
                        coupon.couponAcceptType = "장난감";
                        break;
                    case "WALKING":
                        coupon.couponAcceptType = "산책용품";
                        break;
                    case "FASHION":
                        coupon.couponAcceptType = "패션/잡화";
                        break;
                    case "STROLLER":
                        coupon.couponAcceptType = "유모차";
                        break;
                }
                if (coupon.couponDiscRate.toString().length > 2) {
                    let s = coupon.couponDiscRate.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
                    coupon.couponDiscRate = s + '원 할인';
                } else if (coupon.couponDiscRate.toString().length <= 2) {
                    coupon.couponDiscRate += '% 할인';
                }
                const html = `<div class="border p-lg-3 rounded m-1" style="flex: 0 0 32.33333%;  max-width: 32.33333%;">
                                <div>
                                    <div>
                                        <h6 class="card-text couponEndDate">${coupon.couponEndDate.substring(0, 19)} 까지</h6>
                                        <h5 class="card-text py-2 couponName">${coupon.couponName}</h5>
                                        <div class="row">
                                            <div class="col-6">
                                                <h6 class="couponAcceptType">${coupon.couponAcceptType}</h6>
                                            </div>
                                            <div class="text-center col-6">
                                                <h6 class="couponDiscRate text-danger">${coupon.couponDiscRate}</h6>
                                            </div>
                                        </div>
                                        <div class="d-flex flex-column">
                                            <button class="btn btn-sm btn-primary" id="${coupon.couponId}">쿠폰 받기</button>
                                        </div>
                                    </div>
                                </div>
                            </div>`;
                $('.couponContent').append(html);
            })
        }
    )
}

// async function getCouponList(url) {
//
//     fetch('/member/coupon?isActive=active', {
//         method: 'GET',
//         headers: {
//             'Content-Type': 'application/json',
//         },
//     })
//         .then((response) => response.json())
//         .then((data) => {
//                 $.each(data.content, function (idx, coupon) {
//                     switch (coupon.couponAcceptType) {
//                         case "ALL":
//                             coupon.couponAcceptType = "전체상품";
//                             break;
//                         case "FOOD":
//                             coupon.couponAcceptType = "사료";
//                             break;
//                         case "SNACK":
//                             coupon.couponAcceptType = "간식";
//                             break;
//                         case "TOY":
//                             coupon.couponAcceptType = "장난감";
//                             break;
//                         case "WALKING":
//                             coupon.couponAcceptType = "산책용품";
//                             break;
//                         case "FASHION":
//                             coupon.couponAcceptType = "패션/잡화";
//                             break;
//                         case "STROLLER":
//                             coupon.couponAcceptType = "유모차";
//                             break;
//                     }
//                     if (coupon.couponDiscRate.toString().length > 2) {
//                         let s = coupon.couponDiscRate.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
//                         coupon.couponDiscRate = s + '원 할인';
//                     } else if (coupon.couponDiscRate.toString().length <= 2) {
//                         coupon.couponDiscRate += '% 할인';
//                     }
//                     const html = `<div class="border p-lg-3 rounded m-1" style="flex: 0 0 32.33333%;  max-width: 32.33333%;">
//                                 <div>
//                                     <div>
//                                         <h6 class="card-text couponEndDate">${coupon.couponEndDate.substring(0, 19)} 까지</h6>
//                                         <h5 class="card-text py-2 couponName">${coupon.couponName}</h5>
//                                         <div class="row">
//                                             <div class="col-6">
//                                                 <h6 class="couponAcceptType">${coupon.couponAcceptType}</h6>
//                                             </div>
//                                             <div class="text-center col-6">
//                                                 <h6 class="couponDiscRate text-danger">${coupon.couponDiscRate}</h6>
//                                             </div>
//                                         </div>
//                                         <div class="d-flex flex-column">
//                                             <button class="btn btn-sm btn-primary" id="${coupon.couponId}">쿠폰 받기</button>
//                                         </div>
//                                     </div>
//                                 </div>
//                             </div>`;
//                     $('.couponContent').append(html);
//                 })
//             }
//         )
//     ;
// }
//     getCouponList('/member/coupon?isActive=active').then((data) => {
//         $.each(data.content, function (idx, coupon) {
//             switch (coupon.couponAcceptType) {
//                 case "ALL":
//                     coupon.couponAcceptType = "전체상품";
//                     break;
//                 case "FOOD":
//                     coupon.couponAcceptType = "사료";
//                     break;
//                 case "SNACK":
//                     coupon.couponAcceptType = "간식";
//                     break;
//                 case "TOY":
//                     coupon.couponAcceptType = "장난감";
//                     break;
//                 case "WALKING":
//                     coupon.couponAcceptType = "산책용품";
//                     break;
//                 case "FASHION":
//                     coupon.couponAcceptType = "패션/잡화";
//                     break;
//                 case "STROLLER":
//                     coupon.couponAcceptType = "유모차";
//                     break;
//             }
//             if (coupon.couponDiscRate.toString().length > 2) {
//                 let s = coupon.couponDiscRate.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
//                 coupon.couponDiscRate = s + '원 할인';
//             } else if (coupon.couponDiscRate.toString().length <= 2) {
//                 coupon.couponDiscRate += '% 할인';
//             }
//             const html = `<div class="border p-lg-3 rounded m-1" style="flex: 0 0 32.33333%;  max-width: 32.33333%;">
//                                 <div>
//                                     <div>
//                                         <h6 class="card-text couponEndDate">${coupon.couponEndDate.substring(0, 19)} 까지</h6>
//                                         <h5 class="card-text py-2 couponName">${coupon.couponName}</h5>
//                                         <div class="row">
//                                             <div class="col-6">
//                                                 <h6 class="couponAcceptType">${coupon.couponAcceptType}</h6>
//                                             </div>
//                                             <div class="text-center col-6">
//                                                 <h6 class="couponDiscRate text-danger">${coupon.couponDiscRate}</h6>
//                                             </div>
//                                         </div>
//                                         <div class="d-flex flex-column">
//                                             <button class="btn btn-sm btn-primary" id="${coupon.couponId}">쿠폰 받기</button>
//                                         </div>
//                                     </div>
//                                 </div>
//                             </div>`;
//             $('.couponContent').append(html);
//         })
//     }
// )
$(getCouponList('/member/coupon?isActive=active'));
$(document).ready(function () {
$('.couponTab button').click(function () {
    let attr = $(this).attr('data-id');
    $('.couponTab').children('button').removeClass('active');
    $(this).addClass('active');
    if (attr === '1'){
        $('.couponContent').empty();
        getCouponList('/member/coupon?isActive=active')
    }
    if (attr === '2'){
        $('.couponContent').empty();
        getCouponList('/member/coupon??isActive=active&sort=endDateAsc')
    }
})
})
