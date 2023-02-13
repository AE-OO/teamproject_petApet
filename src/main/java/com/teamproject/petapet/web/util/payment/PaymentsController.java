package com.teamproject.petapet.web.util.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.buyproduct.BuyProduct;
import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.exception.NotLoginException;
import com.teamproject.petapet.web.buy.service.BuyService;
import com.teamproject.petapet.web.buyproduct.BuyProductService;
import com.teamproject.petapet.web.cart.dto.CartCheckoutDTO;
import com.teamproject.petapet.web.cart.service.CartService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.coupon.service.CouponBoxService;
import com.teamproject.petapet.web.product.service.ProductService;
import com.teamproject.petapet.web.util.email.service.EmailService;
import com.teamproject.petapet.web.util.payment.dto.PaymentDTO;
import com.teamproject.petapet.web.util.payment.dto.PaymentRequestDTO;
import com.teamproject.petapet.web.util.payment.dto.PaymentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PaymentsController {
    private final MemberService memberService;
    private final ProductService productService;
    private final BuyService buyService;
    private final CartService cartService;
    private final EmailService emailService;
    private final CouponBoxService couponBoxService;
    private final BuyProductService buyProductService;

    @GetMapping("/cart/checkout")
    public String checkoutByCartsForm(@RequestParam String carts, Model model, Authentication authentication) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Long> cartIds = Arrays.asList(mapper.readValue(carts, Long[].class));
            Member member = memberService.findOne(checkMember(authentication));
            List<CartCheckoutDTO> cartCheckoutDTOList = getCartCheckoutDTOS(cartIds, member);
            long totalPrice = cartCheckoutDTOList.stream().mapToLong(CartCheckoutDTO::getCartTotalPrice).sum();
            model.addAttribute("cartCheckoutDTOList", cartCheckoutDTOList);
            model.addAttribute("member", member);
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("localDate", LocalDate.now());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return "checkout/cartListCheckout";
    }

    @PostMapping("/cart/request/checkout")
    @ResponseBody
    @Transactional
    public ResponseEntity<PaymentRequestDTO> checkoutByCarts(@RequestBody PaymentDTO paymentDTO, Authentication authentication) {
        Member member = memberService.findOne(checkMember(authentication));
        List<Cart> cartList = paymentDTO.getCartIds().stream().map(cartId -> cartService.findOne(cartId, member)).collect(Collectors.toList());
        List<Product> productList = cartList.stream().map(cart -> productService.findOne(cart.getProduct().getProductId()).orElseThrow()).collect(Collectors.toList());
        // 총 결제 금액
        Long totalAmount = cartList.stream().mapToLong(Cart::getTotalPrice).sum() - getCouponDiscRate(paymentDTO.getCouponId(), authentication);
        // 총 상품 개수
        Long totalQuantity = cartList.stream().mapToLong(Cart::getQuantity).sum();
        return ResponseEntity.ok().body(
                new PaymentRequestDTO(
                        getProductName(productList),
                        totalAmount,
                        member.getMemberName(),
                        member.getMemberPhoneNum(),
                        member.getMemberEmail(),
                        member.getMemberAddress(),
                        totalQuantity,
                        member.getMemberId(),
                        paymentDTO.getProductIds(),
                        paymentDTO.getCartIds(),
                        paymentDTO.getCouponId()));

    }

    @PostMapping("/cart/checkout/success")
    @Transactional
    @ResponseBody
    public void addBuy(@RequestBody PaymentVO.PaymentResponseDTO responseDTO, Authentication authentication) {
        Member member = memberService.findOne(checkMember(authentication));

        List<Cart> cartList = responseDTO.getBuyCartIds().stream()
                .map(cartId -> cartService.findOne(cartId, member))
                .collect(Collectors.toList());

        if (responseDTO.getCouponId() != null) couponBoxService.modifyUsedByIdAndUser(responseDTO.getCouponId(), authentication);

        List<BuyProduct> buyProducts = new ArrayList<>();

        for (Cart cart : cartList) {
            BuyProduct buyProduct = buyProductService.save(new BuyProduct(cart.getProduct(),
                    cart.getProduct().getProductPrice(), cart.getQuantity()));
            cart.getProduct().subtractStock(cart.getQuantity());
            cart.getProduct().increaseSellCount(cart.getQuantity());
            buyProducts.add(buyProduct);
        }
        Buy buy = new Buy(responseDTO.getUid(),
                responseDTO.getBuyAddress(),
                member,
                buyProducts,
                responseDTO.getTotalPrice()
        );
        buyService.addBuy(buy);
        emailService.sendEmailMessage(responseDTO.getBuyerEmail(), buy.getBuyId());
    }

    @GetMapping("/direct/checkout")
    public String directCheckoutForm(@RequestParam("productId") Long productId,
                              @RequestParam("quantity") Long quantity,
                              Model model, Principal principal) {

        Product product = productService.findOne(productId).orElseThrow(NoSuchElementException::new);
        String loginMember = checkMember(principal);
        Member member = memberService.findOne(loginMember);
        model.addAttribute("quantity", quantity);
        model.addAttribute("product", product);
        model.addAttribute("member", member);
        model.addAttribute("localDate", LocalDate.now());

        return "checkout/directCheckout";
    }

    @PostMapping("/direct/request/checkout")
    @ResponseBody
    @Transactional
    public ResponseEntity<PaymentRequestDTO> directCheckout(@RequestBody PaymentDTO.DirectPaymentDTO paymentDTO, Authentication authentication) {

        Member member = memberService.findOne(checkMember(authentication));
        List<Product> productList = List.of(productService.findOne(paymentDTO.getProductId()).orElseThrow());
        Long totalAmount = productList.stream().mapToLong(product -> product.getProductPrice()*paymentDTO.getQuantity()).sum() - getCouponDiscRate(paymentDTO.getCouponId(), authentication);
        Long totalQuantity = paymentDTO.getQuantity();
        return ResponseEntity.ok().body(
                new PaymentRequestDTO(
                        getProductName(productList),
                        totalAmount,
                        member.getMemberName(),
                        member.getMemberPhoneNum(),
                        member.getMemberEmail(),
                        member.getMemberAddress(),
                        totalQuantity,
                        member.getMemberId(),
                        Collections.singletonList(paymentDTO.getProductId()),
                       null,
                        paymentDTO.getCouponId()));
    }

    @PostMapping("/direct/checkout/success")
    @Transactional
    @ResponseBody
    public void addBuyByDirect(@RequestBody PaymentVO.PaymentResponseDTO responseDTO, Authentication authentication) {
        Member member = memberService.findOne(checkMember(authentication));

        if (responseDTO.getCouponId() != null) couponBoxService.modifyUsedByIdAndUser(responseDTO.getCouponId(), authentication);

        List<BuyProduct> buyProducts = new ArrayList<>();
        for (Long aLong : responseDTO.getBuyProducts()) {
            Product product = productService.findOne(aLong).orElseThrow();
            BuyProduct buyProduct = new BuyProduct(product, product.getProductPrice(), responseDTO.getBuyQuantity());
            buyProducts.add(buyProduct);
        }
        Buy buy = new Buy(responseDTO.getUid(),
                responseDTO.getBuyAddress(),
                member,
                buyProducts,
                responseDTO.getTotalPrice()
        );
        buyService.addBuy(buy);
        emailService.sendEmailMessage(responseDTO.getBuyerEmail(), buy.getBuyId());
    }
    @GetMapping("/direct/checkout/checkLogin")
    @ResponseBody
    public String checkLoginByRest(Authentication authentication) {
        if (authentication == null) {
            return "로그인이 필요한 서비스 입니다.";
        }
        User user = (User) authentication.getPrincipal();
        String role = user.getAuthorities().stream().findAny().orElseThrow(NoSuchElementException::new).getAuthority();
        if (role.equals("ROLE_ADMIN") || role.equals("ROLE_COMPANY")) {
            return "일반 회원만 주문이 가능합니다.";
        }
        return "ok";
    }

    private String checkMember(Principal principal) {
        return memberService.findOne(principal.getName()).getMemberId();
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

    private List<CartCheckoutDTO> getCartCheckoutDTOS(List<Long> cartIds, Member member) {
        return cartIds.stream().map(i -> cartService.findOne(i, member))
                .map(cart -> new CartCheckoutDTO(
                        cart.getCartId(),
                        cart.getProduct().getProductId(),
                        cart.getProduct().getProductName(),
                        cart.getProduct().getProductPrice(),
                        cart.getQuantity(),
                        cart.getProduct().getProductPrice() * cart.getQuantity()))
                .collect(Collectors.toList());
    }

    private Long getCouponDiscRate(Long couponId, Authentication authentication) {
        if (couponId == null) return 0L;
        return couponBoxService.findByIdAndUser(couponId, authentication).orElseThrow().getCoupons().getCouponDiscRate();
    }

    private String getProductName(List<Product> productList) {
        return productList.size() == 1 ? productList.get(0).getProductName() : productList.get(0).getProductName() + " 외 " + (productList.size() - 1) + "건";
    }
}
