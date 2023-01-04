package com.teamproject.petapet.web.product.coupon;

import com.teamproject.petapet.web.product.coupon.coupondtos.CouponDTO;
import com.teamproject.petapet.web.product.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/admin/coupon")
    public String couponViewForm(@ModelAttribute("couponDTO") CouponDTO couponDTO,
                                 @RequestParam(value = "page", defaultValue = "1", required = false) Integer page,
                                 @RequestParam(value = "acceptType", defaultValue = "total", required = false) String acceptType,
                                 @RequestParam(value = "isActive", defaultValue = "any", required = false) String isActive,
                                 @RequestParam(value = "sort", defaultValue = "couponIdDesc", required = false) String sortStr,
                                 @RequestParam(value = "searchContent", required = false) String searchContent,
                                 Model model) {
        Page<CouponDTO> couponList = couponService.findCouponList(page, acceptType, isActive, sortStr, searchContent);
        model.addAttribute("couponList", couponList);
        return "/admin/coupon/couponView";
    }

    @GetMapping("/admin/registerCoupon")
    public String registerCouponForm(@ModelAttribute("couponDTO") CouponDTO couponDTO) {
        return "/admin/coupon/registerCoupon";
    }

    @PostMapping("/admin/registerCoupon")
    public String registerCoupon(@Validated @ModelAttribute CouponDTO couponDTO, BindingResult bindingResult) {
        if (couponDTO.getCouponType().equals("percentDisc") && couponDTO.getCouponDiscRate() > 50) {
            bindingResult.addError(new FieldError("couponDTO", "couponDiscRate", couponDTO.getCouponDiscRate(), false, null, null, "5-50의 값을 입력하세요"));
        }

        if (bindingResult.hasErrors()) {
            return "/admin/coupon/registerCoupon";
        }
        couponService.createCoupon(couponDTO);
        return "redirect:/admin/coupon";
    }

    @GetMapping("/member/couponBox")
    public String couponBoxForm() {
        return "/admin/coupon/couponBox";
    }
}
