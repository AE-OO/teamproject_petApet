package com.teamproject.petapet.web.util.payment;

import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.buy.service.BuyService;
import com.teamproject.petapet.web.cart.service.CartService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import com.teamproject.petapet.web.util.email.service.EmailService;
import com.teamproject.petapet.web.util.payment.dto.PaymentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentsController {
    private final MemberService memberService;
    private final ProductService productService;
    private final BuyService buyService;
    private final CartService cartService;

    private final EmailService emailService;


    // cart 페이지에서 checkout 페이지로 이동
    @GetMapping("/cart/checkout/{idx}")
    public String getPayment1(@PathVariable("idx") Long cartId, Model model){
        Cart cart = cartService.findOne(cartId);
        model.addAttribute("cart", cart);
        log.info("뷰 완료!!");
        return "mypage/cartCheckout";
    }

    // productList 페이지에서 checkout 페이지로 이동
    @GetMapping("/direct/checkout/{idx}")
    public String getPayment2(@PathVariable("idx") Long productId, Model model,
                              Principal principal, HttpServletRequest request, HttpSession httpSession){
        Product product = productService.findOne(productId);
        String loginMember = checkMember(principal, request, httpSession);
        Member member = memberService.findOne(loginMember);
        model.addAttribute("product", product);
        model.addAttribute("member", member);
        log.info("뷰 완료!!");
        return "mypage/directCheckout";
    }

    @ResponseBody
    @RequestMapping(value = "/checkout", method = { RequestMethod.POST }, produces = "application/json")
    public void buySuccess(@RequestBody PaymentVO vo,Principal principal, HttpServletRequest request, HttpSession httpSession, Model model) throws Exception {
        String loginMember = checkMember(principal, request, httpSession);
        Buy buy = new Buy(
                vo.getBuyAddress(),
                memberService.findOne(loginMember),
                productService.findOne(vo.getBuyProduct()),
                vo.getBuyQuantity()
        );
        buyService.addBuy(buy);
        log.info("구매 정보 저장 완료");
        emailService.sendEmailMessage(vo.getBuyerEmail(), buy.getBuyId());
        log.info("메일 전송 완료");
    }

    @ResponseBody
    @RequestMapping(value = "/checkout2", method = { RequestMethod.POST }, produces = "application/json")
    public void buySuccess2(@RequestBody PaymentVO vo,Principal principal, HttpServletRequest request, HttpSession httpSession, Model model) throws Exception {
        String loginMember = checkMember(principal, request, httpSession);
        Buy buy = new Buy(
                vo.getBuyAddress(),
                memberService.findOne(loginMember),
                productService.findOne(vo.getBuyProduct()),
                1L
        );
        buyService.addBuy(buy);
        log.info("구매 정보 저장 완료");
        emailService.sendEmailMessage(vo.getBuyerEmail(), buy.getBuyId());
        log.info("메일 전송 완료");
    }

    private String checkMember(Principal principal, HttpServletRequest request, HttpSession httpSession) {
        httpSession.setAttribute("loginMember", memberService.findOne(principal.getName()));
        HttpSession session = request.getSession(false);
        Member loginMemberSession = (Member) session.getAttribute("loginMember");
        String loginMember = loginMemberSession.getMemberId();
        return loginMember;
    }

}
