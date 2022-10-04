package com.teamproject.petapet.domain.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

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
}