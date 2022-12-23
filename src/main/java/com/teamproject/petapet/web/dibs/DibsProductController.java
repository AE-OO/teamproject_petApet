package com.teamproject.petapet.web.dibs;

import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.dibs.service.DibsProductService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product/dibs")
public class DibsProductController {

    private final DibsProductService dibsProductService;
    private final MemberService memberService;
    private final ProductService productService;

    @RequestMapping("/add")
    @ResponseBody
    public String addDibs(@RequestParam("productId") Long id, Principal principal) {
        if (principal == null) {
            return "login";
        }

        Member member = memberService.findOne(principal.getName());
        Product product = productService.findOne(id).orElseThrow(NoSuchElementException::new);

        if (!duplicateDibsProduct(product, member)) {
            dibsProductService.saveDibsProduct(new DibsProduct(id, member, product));
            return "ok";
        }
        return "duplicate";
    }

    private boolean duplicateDibsProduct(Product id, Member member) {
        return dibsProductService.existsDibsProduct(id, member);
    }
}
