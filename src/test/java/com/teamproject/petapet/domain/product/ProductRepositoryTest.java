package com.teamproject.petapet.domain.product;

import com.teamproject.petapet.domain.product.repository.ProductRepository;
import com.teamproject.petapet.domain.product.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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
    @Test
    void 카운트테스트(){
//        Long aLong = reviewRepository.countReviewByProduct(productRepository.findById(103L).get());
//        System.out.println("aLong = " + aLong);
        Long avg = reviewRepository.avg(104L);
        System.out.println("avg = " + avg);
    }
//    @Test
//    @Transactional
//    void 상품찾기테스트(){
//        Sort sort = Sort.by("reviewId").descending();
//        Pageable pageable = PageRequest.of(1, 10, sort);
//        Slice<Review> test = reviewRepository.test(103L, pageable);
//        List<Review> content = test.getContent();
//        System.out.println("test = " + test.getContent().size());
//    }
}