package com.teamproject.petapet.web.product.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.repository.CouponRepository;
import com.teamproject.petapet.web.product.coupondtos.CouponDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.teamproject.petapet.domain.product.QCoupon.coupon;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void createCoupon(CouponDTO couponDTO) {
        Coupon coupon = Coupon.convertToEntity(couponDTO);
        couponRepository.save(coupon);
    }

    @Override
    public Page<CouponDTO> findCouponList() {
        return new PageImpl<>(null,null,0L);
    }

    @Override
    public void updateCoupon(Long couponId) {
    }

    @Override
    public Long activeCoupon() {
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory.update(coupon)
                .set(coupon.couponActive, false)
                .where(coupon.couponEndDate.before(now).and(coupon.couponActive.eq(true)))
                .execute();
    }
}
