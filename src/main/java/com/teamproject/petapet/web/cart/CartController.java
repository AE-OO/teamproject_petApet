package com.teamproject.petapet.web.cart;

import com.teamproject.petapet.domain.cart.Cart;
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
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;

    /**
     * 회원 장바구니
     * @param principal
     * @param request
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping()
    public String mycart(Principal principal,
                         HttpServletRequest request,
                         HttpSession httpSession,
                         Model model){

        if(Principal.class.isInstance(principal)) {
            String memberId = principal.getName();
            httpSession.setAttribute("loginMember", memberService.findOne(memberId).get());
//            model.addAttribute("memberId", memberId);
            HttpSession session = request.getSession(false);
            Member loginMemberSession = (Member) session.getAttribute("loginMember");
            String loginMember = loginMemberSession.getMemberId();

            // 세션 유지되면 로그인으로 이동
            List<Cart> carts = cartService.findAll(loginMember);
            model.addAttribute("carts", carts);
            return "mypage/cart";
        }

        return "login";
    }

    // 상품 페이지 -> 장바구니 담기
    @PostMapping("/add")
    public String productToCart(@Validated @ModelAttribute("cartDTO") CartDTO cartDTO,
                                BindingResult bindingResult){

        return "mypage/cart";
    }


}
