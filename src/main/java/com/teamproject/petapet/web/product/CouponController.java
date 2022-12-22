package com.teamproject.petapet.web.product;

import com.teamproject.petapet.domain.product.Coupon;
import com.teamproject.petapet.web.product.coupondtos.CouponDTO;
import com.teamproject.petapet.web.product.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class CouponController {
    private final CouponService couponService;
    @GetMapping("/coupon")
    public String tetetet(Model model){
        Page<CouponDTO> couponList = couponService.findCouponList();
        model.addAttribute("couponList",couponList);
        return "/product/test";
    }
}
