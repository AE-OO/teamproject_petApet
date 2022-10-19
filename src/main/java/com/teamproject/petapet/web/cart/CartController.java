package com.teamproject.petapet.web.cart;

import com.teamproject.petapet.domain.cart.CartRepository;
import com.teamproject.petapet.web.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    public CartService cartService;

    @GetMapping()
    public String  mycart(){
        return "mypage/cart";
    }
}
