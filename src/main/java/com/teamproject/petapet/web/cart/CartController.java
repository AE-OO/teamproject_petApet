package com.teamproject.petapet.web.cart;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.cart.CartRepository;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.cart.dto.CartDTO;
import com.teamproject.petapet.web.cart.service.CartService;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;

    @GetMapping()
    public String mycart(String memberId, Model model, HttpSession httpSession, HttpServletRequest request){
//        httpSession.setAttribute("loginMember", memberService.findOne("memberA").get());
//        HttpSession session = request.getSession(false);
//        Member loginMemberSession = (Member) session.getAttribute("loginMember");
//        String loginMember = loginMemberSession.getMemberId();
        String dd = "memberA";
//        log.info("loginMember={}",loginMember);

        List<Cart> carts = cartService.findAll(dd);
        log.info("carts >> ={}" , carts);
        model.addAttribute("carts",carts);
        return "mypage/cart-test";
    }

    @PostMapping("/add")
    public String productToCart(@Validated @ModelAttribute("cartDTO") CartDTO cartDTO,
                                BindingResult bindingResult){

        return "mypage/cart";
    }
}
