package com.teamproject.petapet.web.product.coupon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.CouponBox;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.domain.product.repository.CouponBoxRepository;
import com.teamproject.petapet.domain.product.repository.ProductRepository;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponBoxDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponCheckoutDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.QCouponBoxDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.teamproject.petapet.domain.product.QCoupon.coupon;
import static com.teamproject.petapet.domain.product.QCouponBox.couponBox;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponBoxServiceImpl implements CouponBoxService {
    private final CouponBoxRepository couponBoxRepository;
    private final ProductRepository productRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final ObjectMapper mapper;

    @Override
    public List<CouponBoxDTO> findAvailableCoupons(Principal principal, String productIds) {
        try {
            List<CouponCheckoutDTO> acceptTypeAndPrice = Arrays.stream(mapper.readValue(productIds, Long[].class))
                    .map(id -> productRepository.findById(id).orElseThrow())
                    .map(product -> new CouponCheckoutDTO(product.getProductDiv().name(), product.getProductPrice()))
                    .collect(Collectors.toList());

            return acceptTypeAndPrice.stream().map(dto -> jpaQueryFactory.select(new QCouponBoxDTO(
                            couponBox.couponBoxId,
                            couponBox.isUsed,
                            couponBox.expirationDate,
                            couponBox.member.memberId.stringValue(),
                            couponBox.coupons.couponId,
                            couponBox.coupons.couponName,
                            couponBox.coupons.couponAcceptType.stringValue(),
                            couponBox.coupons.couponDiscRate,
                            couponBox.coupons.couponAcceptPrice
                    ))
                    .from(couponBox)
                    .where(couponBox.isUsed.eq(false),
                            couponBox.member.memberId.eq(principal.getName()),
                            couponBox.coupons.couponAcceptType.stringValue().eq(dto.getAcceptType())
                                    .or(couponBox.coupons.couponAcceptType.eq(ProductType.ALL)),
                            couponBox.coupons.couponAcceptPrice.loe(dto.getAcceptPrice()))
                    .fetch()).flatMap(Collection::stream).distinct().collect(Collectors.toList());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void modifyUsedByIdAndUser(Long couponId, Principal principal) {
        jpaQueryFactory.update(couponBox)
                .set(couponBox.isUsed, true)
                .where(couponBox.coupons.couponId.eq(couponId)
                        .and(couponBox.member.memberId.eq(principal.getName())))
                .execute();
    }

    @Override
    public Optional<CouponBox> findByIdAndUser(Long couponId, Principal principal) {
        if (couponId != null && principal != null) {
        return Optional.ofNullable(jpaQueryFactory.select(couponBox)
                .where(couponBox.coupons.couponId.eq(couponId)
                        .and(couponBox.member.memberId.eq(principal.getName())))
                .from(couponBox)

                .fetchFirst());
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public boolean save(Member member, Coupon coupon) {
        if (!duplicateCheckCoupon(coupon, member)) {
            CouponBox couponBox = new CouponBox(LocalDateTime.now().plusDays(7L), member, coupon);
            couponBoxRepository.save(couponBox);
            return true;
        }
        return false;
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
                        couponBox.coupons.couponDiscRate,
                        couponBox.coupons.couponAcceptPrice
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


    private boolean duplicateCheckCoupon(Coupon coupon, Member member) {
        return couponBoxRepository.existsByCouponsAndMember(coupon, member);
    }
}
