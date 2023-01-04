package com.teamproject.petapet.web.product.coupon.service;


import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponBoxDTO;
import org.springframework.data.domain.Page;

public interface CouponBoxService {

    boolean save(Member member, Coupon coupon);

    boolean duplicateCheckCoupon(Member member, Coupon coupon);

    Page<CouponBoxDTO> findCouponBoxList(String sortStr, String isUsed);

    Long expirationCoupon();
}
