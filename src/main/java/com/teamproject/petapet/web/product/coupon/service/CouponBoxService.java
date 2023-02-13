package com.teamproject.petapet.web.product.coupon.service;


import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.CouponBox;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponBoxDTO;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface CouponBoxService {

    boolean save(Member member, Coupon coupon);

    boolean duplicateCheckCoupon(Member member, Coupon coupon);

    Page<CouponBoxDTO> findCouponBoxList(String sortStr, String isUsed);

    List<CouponBoxDTO> findAvailableCoupons(Principal principal, String productIds);

    void modifyUsedByIdAndUser(Long couponId, Principal principal);
    Optional<CouponBox> findByIdAndUser(Long couponId, Principal principal);

    Long expirationCoupon();
}
