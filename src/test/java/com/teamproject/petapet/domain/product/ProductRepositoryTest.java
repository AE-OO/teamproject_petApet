package com.teamproject.petapet.domain.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

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

    @Test
    void 상품등록(){
        LongStream.rangeClosed(1,10).forEach(i ->{
//            Product product = new Product("name" + i, 1000L + i, 100L + i, "0", "Div", "content" + i,new Counter(i,0L,0L,productRepository.findById(i).get()));
//            Product product = new Product("name" + i, 1000L + i, 100L + i, "0", ProductType.TOY, "contentcontentcontentcontentcontentcontentcontentcontentcontent" + i);
//            productRepository.save(product);
        });
//        ProductType productType = ProductType.valueOf("FOOD");
//        System.out.println("productType = " + productType);
//        List<Product> food = productRepository.findAllByProductDiv(ProductType.COMPANION_GOODS);
//        int size = food.size();
//        System.out.println("size = " + size);
    }
}