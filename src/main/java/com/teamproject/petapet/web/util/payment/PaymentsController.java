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
import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        model.addAttribute("localDate", LocalDate.now());
        log.info("getPayment1 뷰 완료!!");
        return "mypage/cartCheckout";
    }

    // productList or productDetail 페이지에서 direct checkout 페이지 뷰
//    @RequestMapping(value = "/direct/checkout", method = {RequestMethod.GET})
////    public String getPayment2(@RequestParam("productId") Long productId, @RequestParam("quantity") Long quantity, Model model,
////    @ResponseBody
//    @GetMapping("/cart/checkout")
//    public String getPayment3(@RequestParam String str) {
////        JSONArray objects = new JSONArray(str);
////        String s = objects.toString();
////        JSONObject jsonObject = new JSONObject();
////        String s = jsonObject.optString(str);
////        log.info("osh= {}",s);
//        return "";
//    }

    // productList 페이지에서 checkout 페이지로 이동
    @GetMapping("/direct/checkout")
    public String getPayment2(@RequestParam("productId") Long productId,@RequestParam("quantity") Long quantity, Model model,
                              Principal principal){
        Product product = productService.findOne(productId).orElseThrow(NoSuchElementException::new);
        String loginMember = checkMember(principal);
        Member member = memberService.findOne(loginMember);
        model.addAttribute("quantity", quantity);
        model.addAttribute("product", product);
        model.addAttribute("member", member);
        model.addAttribute("localDate", LocalDate.now());
        log.info("뷰 완료!!");

        log.info("수량 ={}", quantity);
        return "mypage/directCheckout";
    }

    @GetMapping("/direct/checkout/{idx}")
    public String getPayment4(@PathVariable("idx") Long productId,@RequestParam("quantity") Long quantity, Model model,
                              Principal principal){
        Product product = productService.findOne(productId).orElseThrow(NoSuchElementException::new);
        String loginMember = checkMember(principal);
        Member member = memberService.findOne(loginMember);
        model.addAttribute("quantity", quantity);
        model.addAttribute("product", product);
        model.addAttribute("member", member);
        model.addAttribute("localDate", LocalDate.now());
        log.info("뷰 완료!!");

        log.info("수량 ={}", quantity);
        return "mypage/directCheckout";
    }
//    @ResponseBody
//    @RequestMapping(value = "/direct/checkout/{idx}", method = { RequestMethod.POST }, produces = "application/json")
//    public String buySuccess3(@RequestBody PaymentVO vo , @PathVariable("idx") Long productId, Principal principal, Model model) {
//        String loginMember = checkMember(principal);
//        Long getProduct = vo.getBuyProduct();
//        Member member = memberService.findOne(loginMember);
//        Product product = productService.findOne(getProduct).orElseThrow(NoSuchElementException::new);
//        model.addAttribute("product", product);
//        model.addAttribute("member", member);
//        return "mypage/directCheckout";
//    }


    // mail 전송 1
    @ResponseBody
    @RequestMapping(value = "/checkout", method = { RequestMethod.POST }, produces = "application/json")
    public void buySuccess(@RequestBody PaymentVO vo,Principal principal, Model model) throws Exception {
        String loginMember = checkMember(principal);
        Long currentQuantity = productService.compareStock(vo.getBuyProduct());
        Long buyQuantity = vo.getBuyQuantity();
        long remainQuantity = currentQuantity - buyQuantity;
        Buy buy = new Buy(
                vo.getUid(),
                vo.getBuyAddress(),
                memberService.findOne(loginMember),
                productService.findOne(vo.getBuyProduct()).orElseThrow(NoSuchElementException::new),
                vo.getBuyQuantity()
        );

        if ( currentQuantity >= buyQuantity){
            buyService.addBuy(buy);
            productService.updateProductStatus("판매중",remainQuantity, vo.getBuyProduct());
            log.info("구매 정보 저장 완료");
            emailService.sendEmailMessage(vo.getBuyerEmail(), buy.getBuyId());
            log.info("메일 전송 완료");
        } else {
            log.info("품절 및 재고 부족");
        }
    }

    // mail 전송 2
    @ResponseBody
    @RequestMapping(value = "/checkout2", method = { RequestMethod.POST }, produces = "application/json")
    public void buySuccess2(@RequestBody PaymentVO vo,Principal principal, Model model) throws Exception {
        String loginMember = checkMember(principal);
        Long currentQuantity = productService.compareStock(vo.getBuyProduct());
        Long buyQuantity = vo.getBuyQuantity();
        long remainQuantity = currentQuantity - buyQuantity;
        Buy buy = new Buy(
                vo.getUid(),
                vo.getBuyAddress(),
                memberService.findOne(loginMember),
                productService.findOne(vo.getBuyProduct()).orElseThrow(NoSuchElementException::new),
                vo.getBuyQuantity()
        );

        if ( currentQuantity >= buyQuantity){
            buyService.addBuy(buy);
            productService.updateProductStatus("판매중",remainQuantity, vo.getBuyProduct());
            log.info("구매 정보 저장 완료");
            emailService.sendEmailMessage(vo.getBuyerEmail(), buy.getBuyId());
            log.info("메일 전송 완료");
        } else {
            log.info("품절 및 재고 부족");
        }
    }

    private String checkMember(Principal principal) {
        return memberService.findOne(principal.getName()).getMemberId();
    }
}
