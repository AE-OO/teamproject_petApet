package com.teamproject.petapet.web.buy;


import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.buy.dto.BuyVO;
import com.teamproject.petapet.web.buy.service.BuyService;
import com.teamproject.petapet.web.buyproduct.BuyProductDTO;
import com.teamproject.petapet.web.buyproduct.BuyProductService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/buy")
@RequiredArgsConstructor
public class BuyController {

    private final BuyService buyService;
    private final BuyProductService buyProductService;
    private final MemberService memberService;
    private final ProductService productService;


    // 나의 주문 목록
    @GetMapping()
    public String myBuy(Principal principal, Model model){

        if(principal != null) {
            String loginMember = checkMember(principal);
            List<Buy> buyList = buyService.findAll(loginMember);
            model.addAttribute("buyList", buyList);
            model.addAttribute("curDate", LocalDateTime.now());
            return "mypage/buy";
        }

        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = { RequestMethod.POST }, produces = "application/json")
    public void productToBuy(@RequestBody BuyVO vo, Principal principal){

        Member member = memberService.findOne(checkMember(principal));
        Long product = vo.getProduct();
        Long quantity = vo.getQuantity();

        Buy buy = new Buy(
                member.getMemberAddress(),
                member,
                productService.findOne(product).orElseThrow(NoSuchElementException::new),
                quantity);

        buyService.addBuy(buy);

    }

    private String checkMember(Principal principal) {
        return memberService.findOne(principal.getName()).getMemberId();
    }

    //박채원 22.12.16 추가 (이하 3개 메소드)
    @ResponseBody
    @GetMapping("/getTotalSalesVolPerMonth")
    public List<Integer> getTotalSalesVolPerMonth(Principal principal){
        System.out.println("이름 : " + principal.getName());
        System.out.println(buyService.getTotalSalesVolPerMonth(principal.getName()));
        return buyService.getTotalSalesVolPerMonth(principal.getName());
    }
    
    @ResponseBody
    @GetMapping("/getSalesVolbyProduct")
    public List<List<String>> getSalesVolbyProduct(Principal principal){
        return buyService.getSalesVolbyProduct(principal.getName());
    }

    @ResponseBody
    @GetMapping("/getSalesVolbyProductPerMonth/{productId}")
    public List<Integer> getSalesVolbyProductPerMonth(@PathVariable("productId") Long productId){
        return buyService.getSalesVolbyProductPerMonth(productId);
    }

    //박채원 22.12.26 추가 (이하 2개 메소드)
    @ResponseBody
    @GetMapping(value = "/manageSales", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BuyProductDTO>> getSalesList(Principal principal){
        return new ResponseEntity<>(buyService.getCompanyPageSalesList(principal.getName()), HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/getMonthlySales")
    public List<Integer> getMonthlySales(Principal principal){
        return buyService.getMonthlySales(principal.getName());
    }
}
