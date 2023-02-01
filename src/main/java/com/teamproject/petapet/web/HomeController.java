
package com.teamproject.petapet.web;


import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.community.service.CommunityService;
import com.teamproject.petapet.web.product.productdtos.ProductListDTO;
import com.teamproject.petapet.web.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CommunityService communityService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/search")
    public String searchForm(@RequestParam String searchContent) {
        return "integratedSearch";
    }

//    @GetMapping("/searchResult")
//    @ResponseBody
//    public ResponseEntity<Page<ProductListDTO.IntegratedSearchDTO>> searchResult(@RequestParam String searchContent) {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Product> page = productService.findPage(searchContent, pageable);
//        Page<ProductListDTO.IntegratedSearchDTO> content = getProductListDTO(page);
//        long totalElements = content.getTotalElements();
//        return ResponseEntity.ok().body(content);
//    }

    @GetMapping("/searchResult")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> searchResult(@RequestParam String searchContent) {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = productService.findPage(searchContent, pageable);
        Page<ProductListDTO.IntegratedSearchDTO> content = getProductListDTO(page);
//        long totalElements = content.getTotalElements();

        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("product",content);
        pageMap.put("community",communityService.getSearchList("titleContent",searchContent,0,10,"communityId"));
        return ResponseEntity.ok().body(pageMap);
    }

    private Page<ProductListDTO.IntegratedSearchDTO> getProductListDTO(Page<Product> productList) {
        Page<ProductListDTO.IntegratedSearchDTO> productListDTOS;
            productListDTOS = productList.map(m -> ProductListDTO.IntegratedSearchDTO.builder().productName(m.getProductName())
                    .productPrice(m.getProductPrice())
                    .productId(m.getProductId())
                    .productDiv(m.getProductDiv().getProductCategory())
                    .productCategory(m.getProductDiv())
                    .productRating(m.getProductRating())
                    .productViewCount(m.getProductViewCount())
                    .build()
            );
        return productListDTOS;
    }

}