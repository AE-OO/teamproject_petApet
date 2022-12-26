package com.teamproject.petapet.web.product.service;

import com.teamproject.petapet.web.product.coupondtos.CouponDTO;
import org.springframework.data.domain.Page;

public interface CouponService {

    void createCoupon(CouponDTO couponDTO);

    Page<CouponDTO> findCouponList();

    void updateCoupon(Long couponId);

    Long activeCoupon();
}
