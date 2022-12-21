package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.web.product.coupondtos.CouponDTO;
import org.springframework.data.domain.Page;

public interface CouponService {

    void addCoupon(CouponDTO couponDTO);

    Page<CouponDTO> findCouponList();

    void updateCoupon(Long couponId);

    void activeCoupon(Long couponId);
}
