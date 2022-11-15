package com.teamproject.petapet.web.util.payment;

import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.buy.service.BuyService;
import com.teamproject.petapet.web.cart.service.CartService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
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

    @GetMapping("/direct/checkout/{idx}")
    public String getPayment(@PathVariable("idx") Long cartId, Model model){
        Cart cart = cartService.findOne(cartId);
        model.addAttribute("cart", cart);
        log.info("뷰 완료!!");
        return "mypage/checkout";
    }

    @ResponseBody
    @RequestMapping(value = "/checkout", method = { RequestMethod.POST }, produces = "application/json")
    public void buySuccess(@RequestBody PaymentVO vo,Principal principal, HttpServletRequest request, HttpSession httpSession){
        String loginMember = checkMember(principal, request, httpSession);
        Buy buy = new Buy(
                vo.getBuyAddress(),
                memberService.findOne(loginMember),
                productService.findOne(vo.getBuyProduct()),
                vo.getBuyQuantity()
        );
        buyService.addBuy(buy);
    }

    private String checkMember(Principal principal, HttpServletRequest request, HttpSession httpSession) {
        httpSession.setAttribute("loginMember", memberService.findOne(principal.getName()));
        HttpSession session = request.getSession(false);
        Member loginMemberSession = (Member) session.getAttribute("loginMember");
        String loginMember = loginMemberSession.getMemberId();
        return loginMember;
    }

//    @PostMapping("/direct/checkout/{idx})
//    public String doPayment(@PathVariable("idx") Long inquiredId, @ModelAttribute InquiryDTO inquiryDTO){
//
//        inquiredService.setInquiredCheck(inquiredId);
//        log.info("수정 완료!!");
//        return "redirect:/admin/adminPage";
//    }

//
//    // 카트 -> 결제
//    @GetMapping("/direct/checkout")
//    public String payDirect(){
//        return "mypage/checkout";
//    }


//    // 상품 -> 결제
//    @GetMapping("/cart/checkout")
//    public String pay(@ModelAttribute("CartPayVO") CartPayVO vo, Model model){
//
//        Cart cart = new Cart(
//                vo.getCart().getCartId(),
//                vo.getCart().getMember(),
//                vo.getCart().getProduct(),
//                vo.getCart().getQuantity()
//                );
//        log.info("카트 ={}", vo.getCart().getCartId());
//        log.info("멤버 ={}", vo.getCart().getMember().getMemberId());
//        log.info("상품 ={}", vo.getCart().getProduct().getProductId());
//        model.addAttribute("CartPay", cart);
//        return "mypage/checkout";
//    }

//    @ResponseBody
//    @RequestMapping(value = "/checkout", method = { RequestMethod.POST }, produces = "application/json")
//    public String checkout(@RequestBody CheckOutProductVO vo, Principal principal, HttpServletRequest request, HttpSession httpSession, Model model){
//        return "";
//    }
}
