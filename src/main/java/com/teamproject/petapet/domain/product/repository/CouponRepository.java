package com.teamproject.petapet.domain.product.repository;

import com.teamproject.petapet.domain.product.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
