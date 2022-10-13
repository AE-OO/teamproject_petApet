package com.teamproject.petapet.web.product;

import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.product.ProductRepository;
import com.teamproject.petapet.domain.product.ProductType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public String productMainPage() {
        return "/product/productMainPage";
    }

    @GetMapping("/{category}")
    public String productList(@PathVariable("category") String category, Model model) {
        category = category.toUpperCase();
        ProductType productType = ProductType.valueOf(category);
        List<Product> productList = productRepository.findAllByProductDiv(productType);
        model.addAttribute("productList",productList);
        return "product/productList";
    }

//    @GetMapping("/{productId}/details")
    @GetMapping("/details")
    public String productDetails(){

        return "/product/productDetails";
    }
}
