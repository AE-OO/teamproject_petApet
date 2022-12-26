package com.teamproject.petapet.domain.dibs;

import com.teamproject.petapet.domain.dibs.repository.DibsProductRepository;
import com.teamproject.petapet.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class DibsProductRepositoryTest {

    @Autowired
    DibsProductRepository dibsProductRepository;

    @Test
    public void insertDibsProductDummies(){
        IntStream.rangeClosed(1, 50).forEach(i -> {
            long productId = (long) (Math.random() * 30) + 1;
            long memberNum = (long) (Math.random() * 30) + 1;

            Product product = Product.builder().productId(productId).build();
            Member member = Member.builder().memberId("memberId" + memberNum).build();

            DibsProduct dibsProduct = DibsProduct.builder().member(member).product(product).build();

            dibsProductRepository.save(dibsProduct);
        });
    }

}