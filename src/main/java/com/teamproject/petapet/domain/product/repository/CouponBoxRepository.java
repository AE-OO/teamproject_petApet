package com.teamproject.petapet.domain.product.repository;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.CouponBox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponBoxRepository extends JpaRepository<CouponBox, Long> {

    boolean existsByCouponsAndMember(Coupon coupon, Member member);
}
