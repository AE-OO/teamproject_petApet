package com.teamproject.petapet.web.product.coupon;

import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponBoxDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import com.teamproject.petapet.web.product.coupon.service.CouponBoxService;
import com.teamproject.petapet.web.product.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CouponRestController {

    private final CouponService couponService;
    private final CouponBoxService couponBoxService;
    private final MemberService memberService;

    @PostMapping("/admin/coupon/update")
    public ResponseEntity<Map<String, String>> updateCoupon(@Validated @RequestBody CouponDTO couponDTO, BindingResult bindingResult) {
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

    @PostMapping("/checkout/getAvailableCoupons")
    public ResponseEntity<List<CouponBoxDTO>> getAvailableCoupons(@RequestBody String productIds, Principal principal) {
        return ResponseEntity.ok().body(couponBoxService.findAvailableCoupons(principal, productIds));
    }
}
