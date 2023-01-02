package com.teamproject.petapet.web.product.coupon.service;

import com.teamproject.petapet.web.product.coupon.coupondtos.CouponBoxDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.Map;

public interface CouponService {

    void createCoupon(CouponDTO couponDTO);

    Page<CouponBoxDTO> findCouponList(Integer page, String isActive, String sortStr);

    Page<CouponDTO> findCouponList(Integer page, String acceptType, String isActive, String sort, String searchContent);

    void updateCoupon(CouponDTO couponDTO);

    Long activeCoupon();

    Map<String, String> validateHandling(BindingResult bindingResult);
}
