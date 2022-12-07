package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.domain.product.repository.ProductRepository;
import com.teamproject.petapet.domain.product.repository.ReviewRepository;
import com.teamproject.petapet.web.product.productdtos.ProductListDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void insertProductDummies(){
        IntStream.rangeClosed(1,30).forEach(i ->{
            Product product = Product.builder()
                    .productName("product " + i)
                    .productPrice(10000L)
                    .productContent(i + "번 상품은 아주 유용한 상품입니다.")
                    .productStatus("판매중")
                    .build();

            productRepository.save(product);
        });
    }

    @Test
    public void deleteProductCascadeCounter(){
        productRepository.deleteById(1L);
    }
    
    @Test
    void 검색테스트(){
        Pageable pageable = PageRequest.of(0,10);
        Slice<Product> result = productRepository.findAllByProductNameContainsAndProductDiv("트", null, pageable);
        Slice<ProductListDTO> map = result.map(m -> ProductListDTO.builder().productId(m.getProductId())
                .productName(m.getProductName()).build());
        map.stream().forEach(i-> System.out.println("i.getProductName() = " + i.getProductName()));
    }
}