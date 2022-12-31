package com.teamproject.petapet.web.product.coupon;

import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import com.teamproject.petapet.web.product.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/coupon")
    public String couponViewForm(@ModelAttribute("couponDTO") CouponDTO couponDTO,
                                 @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                 @RequestParam(value = "acceptType", defaultValue = "total", required = false) String acceptType,
                                 @RequestParam(value = "isActive", defaultValue = "any", required = false) String isActive,
                                 @RequestParam(value = "sort", defaultValue = "couponIdDesc", required = false) String sort,
                                 Model model) {
        Page<CouponDTO> couponList = couponService.findCouponList(page, acceptType, isActive, sort);
        model.addAttribute("couponList", couponList);
        return "/admin/coupon/couponView";
    }

    @GetMapping("/registerCoupon")
    public String registerCouponForm(@ModelAttribute("couponDTO") CouponDTO couponDTO) {
        return "/admin/coupon/registerCoupon";
    }

    @PostMapping("/registerCoupon")
    public String registerCoupon(@Validated @ModelAttribute CouponDTO couponDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/admin/coupon/registerCoupon";
        }
        couponService.createCoupon(couponDTO);
        return "redirect:/admin/adminPage";
    }
}
