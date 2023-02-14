package com.teamproject.petapet.web.dibs;

import com.teamproject.petapet.domain.dibs.DibsProduct;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.dibs.dibsdtos.DibsProductDTO;
import com.teamproject.petapet.web.dibs.service.DibsProductService;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        if (principal == null) return "login";

        Member member = memberService.findOne(principal.getName());
        Product product = productService.findOne(id).orElseThrow(NoSuchElementException::new);

        if (!duplicateDibsProduct(product, member)) {
            dibsProductService.saveDibsProduct(new DibsProduct(id, member, product));
            return "ok";
        }
        return "duplicate";
    }

    @RequestMapping("/remove")
    @ResponseBody
    public String removeDibs(@RequestBody Long id, Principal principal) {
        if (principal == null) {
            return "login";
        }
        DibsProduct dibsProduct = dibsProductService.findOne(id, principal).orElseThrow();
        dibsProductService.removeDibsProduct(dibsProduct);
        return "ok";
    }

    @GetMapping("")
    public String productDibsForm(Principal principal, Model model) {
        Pageable pageable = PageRequest.of(0, 12);
        List<DibsProduct> dibsProducts = dibsProductService.findDibsListByMemberId(principal.getName(), pageable);
        model.addAttribute("dibs", dibsProducts);
        return "/product/dibs/productDibs";
    }

    @PostMapping("/getDibs")
    public ResponseEntity<?> getDibList(Principal principal) {
        List<DibsProduct> dibsProducts = dibsProductService.findDibsListByMemberId(principal.getName(), PageRequest.of(0, 12));
        List<DibsProductDTO> dibsProductDTOS = dibsProducts.stream().map(m -> new DibsProductDTO(m.getProduct().getProductId(),
                m.getProduct().getProductDiv(),
                m.getProduct().getProductImg().get(0).getStoreFileName(),
                m.getProduct().getProductName(),
                m.getProduct().getProductPrice())).collect(Collectors.toList());
        return ResponseEntity.ok().body(dibsProductDTOS);
    }

    private boolean duplicateDibsProduct(Product id, Member member) {
        return dibsProductService.existsDibsProduct(id, member);
    }
}
