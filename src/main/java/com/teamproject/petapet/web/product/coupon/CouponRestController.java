package com.teamproject.petapet.web.product.coupon;

import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import com.teamproject.petapet.web.product.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CouponRestController {

    private final CouponService couponService;

    @PostMapping("/admin/coupon/update")
    public ResponseEntity<?> updateCoupon(@Validated @RequestBody CouponDTO couponDTO, BindingResult bindingResult) {

        if (couponDTO.getCouponType().equals("percentDisc") && couponDTO.getCouponDiscRate() > 50 && couponDTO.getCouponDiscRate() < 5) {
            bindingResult.addError(new FieldError("couponDTO", "couponDiscRate", null, false, null, null, "5-50의 값을 입력하세요"));
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> validateResult = couponService.validateHandling(bindingResult);
            return ResponseEntity.badRequest().body(validateResult);
        }

        couponService.updateCoupon(couponDTO);

        return new ResponseEntity<>(HttpStatus.SEE_OTHER);
    }
}
