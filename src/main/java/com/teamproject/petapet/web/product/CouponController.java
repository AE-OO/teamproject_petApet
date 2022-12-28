package com.teamproject.petapet.web.product;

import com.teamproject.petapet.web.product.coupondtos.CouponDTO;
import com.teamproject.petapet.web.product.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/coupon")
    public String couponViewForm(){
        return "/admin/couponView";
    }

    @GetMapping("/registerCoupon")
    public String registerCouponForm(@ModelAttribute("couponDTO") CouponDTO couponDTO) {
        return "/admin/registerCoupon";
    }

    @PostMapping("/registerCoupon")
    public String registerCoupon(@Validated @ModelAttribute CouponDTO couponDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/registerCoupon";
        }
        couponService.createCoupon(couponDTO);
        return "redirect:/admin/adminPage";
    }
}
