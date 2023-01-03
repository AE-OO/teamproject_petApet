package com.teamproject.petapet.web.product.coupon.service;

import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponListDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Optional;

public interface CouponService {

    void createCoupon(CouponDTO couponDTO);

    Optional<Coupon> findById(Long id);

    Page<CouponListDTO> findCouponList(Integer page, String isActive, String sortStr);

    Page<CouponDTO> findCouponList(Integer page, String acceptType, String isActive, String sort, String searchContent);

    void updateCoupon(CouponDTO couponDTO);

    Long activeCoupon();

    Map<String, String> validateHandling(BindingResult bindingResult);
}
