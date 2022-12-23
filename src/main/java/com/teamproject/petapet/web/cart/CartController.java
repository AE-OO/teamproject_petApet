package com.teamproject.petapet.web.cart;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.cart.dto.CartDTO;
import com.teamproject.petapet.web.cart.dto.CartVO;
import com.teamproject.petapet.web.cart.service.CartService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;
    private final ProductService productService;

    /**
     * 회원 장바구니
     * @param principal
     * @return
     * 추가할것 - 로그인한 회원 일치 검증 에러처리
     */
    @GetMapping()
    public String mycart(Principal principal,
                         Model model){

        if(principal != null) {
            String loginMember = checkMember(principal);

            // 세션 유지되면 로그인으로 이동
            List<Cart> carts = cartService.findAll(loginMember);
            model.addAttribute("carts", carts);
            return "mypage/cart";
        }

        return "login";

    }

    // 상품 페이지 -> 장바구니 담기
    @ResponseBody
    @RequestMapping(value = "/add", method = { RequestMethod.POST }, produces = "application/json")
    public void productToCart(@RequestBody CartVO vo, Principal principal){
        Long product = vo.getProduct();
        Long quantity = vo.getQuantity();
        Cart cart = new Cart(
                memberService.findOne(checkMember(principal)),
                productService.findOne(product).orElseThrow(NoSuchElementException::new),
                quantity);

        cartService.addCart(cart);
    }

    // 구매완료 목록 -> 장바구니 재담기 (재구매)
    @ResponseBody
    @RequestMapping(value = "/buyToCart", method = { RequestMethod.POST }, produces = "application/json")
    public void buyToCart(@RequestBody CartVO vo, Principal principal){
        Long product = vo.getProduct();
        Long quantity = vo.getQuantity();
        Cart cart = new Cart(
                memberService.findOne(checkMember(principal)),
                productService.findOne(product).orElseThrow(NoSuchElementException::new),
                quantity);

        cartService.addCart(cart);

    }

    // 장바구니 -> 주문수정 (수량) 미완
    @RequestMapping( value = "/updateCart", method = RequestMethod.POST, produces = "application/json")
    private String updateQuan(@RequestBody CartDTO cartDTO){
        log.info("=> 주문수정 실행");
        cartService.updateQuantity(cartDTO.getCartId(), cartDTO.getQuantity());
        log.info("CartId ={}", cartDTO.getCartId());
        log.info("Quantity ={}" ,cartDTO.getQuantity());
        log.info("=> 주문수정 완료");
        return "redirect:/mypage/cart";
    }

    // 상품 개별삭제
    @ResponseBody
    @RequestMapping(value = "/removeOne" , method = { RequestMethod.POST }, produces = "application/json")
    public void removeCartOne(@RequestBody CartVO vo){
        log.info(">> ={}", vo.getCartId());
        cartService.removeCartOne(vo.getCartId());
    }

    // 상품 전체삭제 - 미완
    @ResponseBody
    @RequestMapping(value = "/removeAll", method = {RequestMethod.POST} , produces = "application/json")
    public void removeCartAll(@RequestBody CartVO vo, Principal principal){
        cartService.removeCartAll(vo.getMemberId());
    }

    // 개별 결제하기
    @ResponseBody
    @RequestMapping(value = "checkout", method = {RequestMethod.POST} , produces = "application/json")
    public void checkoutOne(@RequestBody CartVO vo, Principal principal){
        cartService.removeCartAll(vo.getMemberId());
    }

    // 다중 구매 - 미완

    private String checkMember(Principal principal) {
        return memberService.findOne(principal.getName()).getMemberId();
    }

}
