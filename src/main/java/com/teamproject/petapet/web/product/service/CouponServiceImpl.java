package com.teamproject.petapet.web.product.service;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.repository.CouponRepository;
import com.teamproject.petapet.web.product.coupondtos.CouponDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.teamproject.petapet.domain.product.QCoupon.coupon;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void addCoupon(CouponDTO couponDTO) {
        Coupon coupon = Coupon.convertToEntity(couponDTO);
        couponRepository.save(coupon);
    }

    @Override
    public Page<CouponDTO> findCouponList() {
        List<CouponDTO> ActivatedCoupon = jpaQueryFactory
                .select(Projections.bean(CouponDTO.class
                        , coupon.couponId
                        , coupon.couponBeginDate
                        , coupon.couponEndDate
                        , coupon.couponStock
                        , coupon.couponAcceptType
                        , coupon.couponActive
                        , coupon.couponPriceDisc
                        , coupon.couponPercentDisc,
                        new CaseBuilder()
                                .when(coupon.couponEndDate.before(LocalDateTime.now()))
                                .then(false)
                                .when(coupon.couponBeginDate.before(LocalDateTime.now()))
                                .then(true)
                                .otherwise(false).as("couponActive")
                ))
                .from(coupon)
                .fetch();
        Pageable pageable = PageRequest.of(0, 10);
        log.info("ActivatedCoupon={}",ActivatedCoupon);
        return new PageImpl<>(ActivatedCoupon, pageable, ActivatedCoupon.size());
    }

    @Override
    public void updateCoupon(Long couponId) {
    }

    @Override
    public void activeCoupon(Long couponId) {
//        jpaQueryFactory.update(coupon)
//                .set(coupon.couponActive, true)
//                .where(coupon.couponId.eq(couponId))
//                .execute();
        jpaQueryFactory.select(coupon, new CaseBuilder()
                        .when(coupon.couponBeginDate.after(LocalDateTime.now()))
                        .then(true)
                        .otherwise(false))
                .from(coupon)
                .fetch();
    }
}
