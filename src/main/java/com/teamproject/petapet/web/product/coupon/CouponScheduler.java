package com.teamproject.petapet.web.product.coupon;

import com.teamproject.petapet.web.product.coupon.service.CouponBoxService;
import com.teamproject.petapet.web.product.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final CouponService couponService;
    private final CouponBoxService couponBoxService;

    //    @Scheduled(cron = "0 0 0 * * *") // 매일 00시 실행
//    @Scheduled(cron = "*/5 * * * * *") // 5초마다 실행 (테스트용)
    public void updateCouponSchedule() {
        couponService.activeCoupon();
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void updateCouponBoxSchedule() {
        couponBoxService.expirationCoupon();
    }
}
