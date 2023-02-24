package com.teamproject.petapet.web.cart;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.exception.NotLoginException;
import com.teamproject.petapet.web.cart.dto.CartVO;
import com.teamproject.petapet.web.cart.service.CartService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
     *
     * @return 추가할것 - 로그인한 회원 일치 검증 에러처리
     */
    @GetMapping()
    public String mycart(Authentication authentication, Model model) {

        if (authentication != null) {
            String loginMember = checkMember(authentication);

            // 세션 유지되면 로그인으로 이동
            List<Cart> carts = cartService.findAll(loginMember);
            model.addAttribute("carts", carts);
            return "mypage/cart";
        }

        return "login";

    }

    // 상품 페이지 -> 장바구니 담기
    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST}, produces = "application/json")
    public void productToCart(@RequestBody CartVO vo, Authentication authentication) {

        String loginMember = checkMember(authentication);
        Long product = vo.getProduct();
        Long quantity = vo.getQuantity();

        Optional<Cart> checkCart = checkCart(loginMember, product);

        if (checkCart.isEmpty()) {
            Cart cart = new Cart(
                    memberService.findOne(loginMember),
                    productService.findOne(product).orElseThrow(NoSuchElementException::new),
                    quantity);
            cartService.addCart(cart);
        } else {
            cartService.updateCart(checkCart.get().getCartId(), quantity);
        }

    }

    @ResponseBody
    @RequestMapping(value = "/buyToCart", method = {RequestMethod.POST}, produces = "application/json")
    public void buyToCart(@RequestBody CartVO vo, Authentication authentication) {

        String loginMember = checkMember(authentication);
        Long product = vo.getProduct();
        Long quantity = vo.getQuantity();
        Cart cart = new Cart(
                memberService.findOne(loginMember),
                productService.findOne(product).orElseThrow(NoSuchElementException::new),
                quantity);

        cartService.addCart(cart);

    }

    // 카트페이지 주문수정 버튼 - 수량 변경 - 미완
    @ResponseBody
    @RequestMapping(value = "/modify", method = {RequestMethod.POST}, produces = "application/json")
    public void modifyCart(@RequestBody CartVO vo) {
        cartService.setQuan(vo.getQuantity(), vo.getCartId());
    }

    @ResponseBody
    @RequestMapping(value = "/success", method = {RequestMethod.POST}, produces = "application/json")
    public void successBuy(@RequestBody CartVO vo) {
        cartService.removeCartOne(vo.getCartId());
    }

    @ResponseBody
    @RequestMapping(value = "/removeOne", method = {RequestMethod.POST}, produces = "application/json")
    public void removeCartOne(@RequestBody CartVO vo) {
        cartService.removeCartOne(vo.getCartId());
    }

    @ResponseBody
    @RequestMapping(value = "/removeAll", method = {RequestMethod.POST}, produces = "application/json")
    public void removeCartAll(@RequestBody CartVO vo, Authentication authentication) {
        String loginMember = checkMember(authentication);
        cartService.removeCartAll(loginMember);
    }

    @ResponseBody
    @RequestMapping(value = "/checkout", method = {RequestMethod.POST}, produces = "application/json")
    public void checkoutOne(@RequestBody CartVO vo, Authentication authentication) {
        String loginMember = checkMember(authentication);
        cartService.removeCartAll(loginMember);
    }

    @ResponseBody
    @GetMapping("/isExist")
    public boolean isExistInCart(Principal principal, @RequestParam("productId") Long productId) {
        if (principal == null) throw new NotLoginException("로그인이 필요한 서비스입니다.");
        Optional<Cart> cart = checkCart(principal.getName(), productId);
        return cart.isPresent();
    }

    private String checkMember(Authentication authentication) {
        if (authentication == null) throw new NotLoginException("로그인이 필요한 서비스입니다.");
        User user = (User) authentication.getPrincipal();
        String role = user.getAuthorities().stream().findAny().orElseThrow(NoSuchElementException::new).getAuthority();
        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_COMPANY")) {
            throw new NotLoginException("일반회원만 가능한 기능입니다.");
        }
        return memberService.findOne(user.getUsername()).getMemberId();
    }


    private Optional<Cart> checkCart(String loginMember, Long product) {
        List<Cart> carts = cartService.findAll(loginMember);
        return carts.stream().filter(cart ->
                cart.getProduct().getProductId().equals(product)
        ).findFirst();
    }
}
