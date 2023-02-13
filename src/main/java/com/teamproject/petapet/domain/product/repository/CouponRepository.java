package com.teamproject.petapet.domain.product.repository;

import com.teamproject.petapet.domain.product.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
