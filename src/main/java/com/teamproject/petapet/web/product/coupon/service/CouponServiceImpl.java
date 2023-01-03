package com.teamproject.petapet.web.product.coupon.service;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.domain.product.ProductType;
import com.teamproject.petapet.domain.product.repository.CouponRepository;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponListDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.QCouponDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.QCouponListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Coupon coupon = new Coupon(couponDTO);
        couponRepository.save(coupon);
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }

    @Override
    public Page<CouponListDTO> findCouponList(Integer page, String isActive, String sortStr) {
        PageRequest pageable = PageRequest.of(page - 1, 9);
        Order direction = getOrder(sortStr);
        String property = getProperty(sortStr);
        List<CouponListDTO> couponDTOList = jpaQueryFactory.select(new QCouponListDTO(coupon.couponId,
                        coupon.couponName,
                        coupon.couponEndDate.stringValue(),
                        coupon.couponAcceptType.stringValue(),
                        coupon.couponActive,
                        coupon.couponDiscRate
                ))
                .from(coupon)
                .where(isActive(isActive), isBetween(sortStr))
                .offset(pageable.getOffset())
                .limit(9)
                .orderBy(getOrderBy(direction, property))
                .fetch();
        long totalCount = countCoupon("total", isActive, "");
        return new PageImpl<>(couponDTOList, pageable, totalCount);
    }

    @Override
    public Page<CouponDTO> findCouponList(Integer page, String acceptType, String isActive, String sortStr, String searchContent) {
        PageRequest pageable = PageRequest.of(page - 1, 9);
        Order direction = getOrder(sortStr);
        String property = getProperty(sortStr);
        List<CouponDTO> couponDTOList = jpaQueryFactory.select(new QCouponDTO(coupon.couponId,
                        coupon.couponName,
                        coupon.couponEndDate.stringValue(),
                        coupon.couponStock,
                        coupon.couponAcceptType,
                        coupon.couponActive,
                        coupon.couponDiscRate,
                        coupon.couponType))
                .from(coupon)
                .where(isAcceptType(acceptType), isActive(isActive), isSearchContent(searchContent))
                .offset(pageable.getOffset())
                .limit(9)
                .orderBy(getOrderBy(direction, property))
                .fetch();
        long totalCount = countCoupon(acceptType, isActive, searchContent);
        return new PageImpl<>(couponDTOList, pageable, totalCount);
    }

    private Order getOrder(String sortStr) {
        Sort sort;
        switch (sortStr) {
            case "stockDesc":
                sort = Sort.by("couponStock").descending();
                break;
            case "stockAsc":
                sort = Sort.by("couponStock");
                break;
            case "endDateDesc":
                sort = Sort.by("couponEndDate").descending();
                break;
            case "endDateAsc":
                sort = Sort.by("couponEndDate");
                break;
            case "couponIdDesc":
                sort = Sort.by("couponId").descending();
                break;
            default:
                sort = Sort.by("couponId");
                break;
        }
        Order direction = null;
        for (Sort.Order order : sort) {
            direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
        }
        return direction;
    }

    private OrderSpecifier<?> getOrderBy(Order order, String property) {
        Path<Object> fieldPath = Expressions.path(Object.class, coupon, property);
        return new OrderSpecifier(order, fieldPath);
    }

    private String getProperty(String sortStr) {
        switch (sortStr) {
            case "couponIdDesc":
            case "couponIdAsc":
                return "couponId";
            case "endDateDesc":
            case "endDateAsc":
                return "couponEndDate";
            case "stockDesc":
            case "stockAsc":
                return "couponStock";
        }
        return "couponId";
    }

    private Long countCoupon(String acceptType, String isActive, String searchContent) {
        return jpaQueryFactory.select(coupon.count())
                .where(isAcceptType(acceptType), isActive(isActive), isSearchContent(searchContent))
                .from(coupon)
                .fetchFirst();
    }

    private BooleanExpression isActive(String isActive) {
        boolean isThatTure = isActive.equals("active");
        if (!isActive.equals("any")) {
            return coupon.couponActive.eq(isThatTure);
        }
        return null;
    }

    private BooleanExpression isSearchContent(String searchContent) {
        if (StringUtils.hasText(searchContent)) {
            return coupon.couponName.contains(searchContent);
        }
        return null;
    }

    private BooleanExpression isAcceptType(String acceptType) {
        if (!acceptType.equals("total")) {
            ProductType productType = ProductType.valueOf(acceptType.toUpperCase());
            return coupon.couponAcceptType.eq(productType);
        }
        return null;
    }

    private BooleanExpression isBetween(String sortStr) {
        if (sortStr.equals("endDateAsc")) {
            return coupon.couponEndDate.between(LocalDateTime.now(), LocalDateTime.now().plusDays(3L));
        }
        return null;
    }

    @Override
    public void updateCoupon(CouponDTO couponDTO) {
        Coupon coupon = couponRepository.findById(couponDTO.getCouponId()).orElseThrow(NoSuchFieldError::new);
        coupon.updateCoupon(couponDTO);
    }

    @Override
    public Long activeCoupon() {
        LocalDateTime now = LocalDateTime.now();
        return jpaQueryFactory.update(coupon)
                .set(coupon.couponActive, false)
                .where(coupon.couponEndDate.before(now).and(coupon.couponActive.eq(true)))
                .execute();
    }

    public Map<String, String> validateHandling(BindingResult bindingResult) {
        Map<String, String> validatorResult = new HashMap<>();
        //필드 에러
        for (FieldError error : bindingResult.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
