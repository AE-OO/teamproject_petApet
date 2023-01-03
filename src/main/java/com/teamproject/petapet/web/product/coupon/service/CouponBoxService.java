package com.teamproject.petapet.web.product.coupon.service;


import com.teamproject.petapet.web.product.coupon.coupondtos.CouponBoxDTO;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface CouponBoxService {

    boolean save(Long couponId, Principal principal);

    boolean duplicateCheckCoupon(Long couponId, Principal principal);

    Page<CouponBoxDTO> findCouponBoxList(String sortStr, String isUsed);
}
