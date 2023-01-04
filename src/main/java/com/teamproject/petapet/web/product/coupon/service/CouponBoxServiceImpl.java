package com.teamproject.petapet.web.product.coupon.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.CouponBox;
import com.teamproject.petapet.domain.product.repository.CouponBoxRepository;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponBoxDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.QCouponBoxDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.teamproject.petapet.domain.product.QCoupon.coupon;
import static com.teamproject.petapet.domain.product.QCouponBox.couponBox;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponBoxServiceImpl implements CouponBoxService {
    private final CouponBoxRepository couponBoxRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean save(Member member, Coupon coupon) {
        if (!duplicateCheckCoupon(coupon, member)) {
            CouponBox couponBox = new CouponBox(LocalDateTime.now().plusDays(7L), member, coupon);
            couponBoxRepository.save(couponBox);
            return true;
        }
        return false;
    }

    private boolean duplicateCheckCoupon(Coupon coupon, Member member) {
        return couponBoxRepository.existsByCouponsAndMember(coupon, member);
    }

    @Override
    public boolean duplicateCheckCoupon(Member member, Coupon coupon) {
        return couponBoxRepository.existsByCouponsAndMember(coupon, member);
    }

    @Override
    public Page<CouponBoxDTO> findCouponBoxList(String isImminent, String isUsed) {
        Pageable pageable = PageRequest.of(0, 9);
        List<CouponBoxDTO> couponBoxList = jpaQueryFactory.select(new QCouponBoxDTO(
                        couponBox.couponBoxId,
                        couponBox.isUsed,
                        couponBox.expirationDate,
                        couponBox.member.memberId.stringValue(),
                        couponBox.coupons.couponId,
                        couponBox.coupons.couponName,
                        couponBox.coupons.couponAcceptType.stringValue(),
                        couponBox.coupons.couponDiscRate
                ))
                .from(couponBox)
                .where(isUsed(isUsed), isBetween(isImminent))
                .offset(pageable.getOffset())
                .limit(9)
                .orderBy(new OrderSpecifier(Order.ASC, Expressions.path(Object.class, couponBox, "expirationDate")))
                .fetch();
        Long totalCount = countCouponBox();
        return new PageImpl<>(couponBoxList, pageable, totalCount);
    }

    @Override
    @Transactional
    public Long expirationCoupon() {
        return jpaQueryFactory.update(couponBox)
                .set(couponBox.isUsed, true)
                .where(couponBox.expirationDate.before(LocalDateTime.now()).and(couponBox.isUsed.eq(false)))
                .execute();
    }

    private BooleanExpression isUsed(String isUsed) {
        if (isUsed.equals("false")) {
            return couponBox.isUsed.eq(false);
        }
        return null;
    }

    private Long countCouponBox() {
        return jpaQueryFactory.select(coupon.count())
                .where()
                .from(coupon)
                .fetchFirst();
    }

    private BooleanExpression isBetween(String sortStr) {
        if (sortStr.equals("true")) {
            return couponBox.expirationDate.between(LocalDateTime.now(), LocalDateTime.now().plusDays(3L));
        }
        return null;
    }

}
