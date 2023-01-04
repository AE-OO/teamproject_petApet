package com.teamproject.petapet.web.product.coupon;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponBoxDTO;
import com.teamproject.petapet.web.product.coupon.coupondtos.CouponListDTO;
import com.teamproject.petapet.web.product.coupon.service.CouponBoxService;
import com.teamproject.petapet.web.product.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/couponBox")
public class CouponBoxRestController {

    private final CouponBoxService couponBoxService;
    private final MemberService memberService;
    private final CouponService couponService;

    @GetMapping("/getCouponList")
    public Page<CouponListDTO> getCouponList(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                             @RequestParam(value = "isActive", defaultValue = "any", required = false) String isActive,
                                             @RequestParam(value = "sort", defaultValue = "couponIdDesc", required = false) String sortStr) {
        return couponService.findCouponList(page, isActive, sortStr);
    }

    @GetMapping("/broughtCouponList")
    public Page<CouponBoxDTO> broughtCouponList(@RequestParam(value = "isImminent", defaultValue = "false", required = false) String sortStr,
                                                @RequestParam(value = "isUsed", defaultValue = "any", required = false) String isUsed) {
        return couponBoxService.findCouponBoxList(sortStr, isUsed);
    }

    @PostMapping("/download")
    public boolean downloadCoupon(@RequestBody Long couponId, Principal principal) {
        Member member = memberService.findOne(principal.getName());
        Coupon coupon = couponService.findById(couponId).orElseThrow(NoSuchElementException::new);
        return couponBoxService.save(member, coupon);
    }

    @GetMapping("/duplicateCheck")
    public boolean duplicateCheckCoupon(@RequestParam("couponId") Long couponId, Principal principal) {
        Member member = memberService.findOne(principal.getName());
        Coupon coupon = couponService.findById(couponId).orElseThrow(NoSuchElementException::new);
        return couponBoxService.duplicateCheckCoupon(member, coupon);
    }
}
