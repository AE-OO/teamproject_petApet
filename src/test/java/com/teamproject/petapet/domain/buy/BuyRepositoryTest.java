package com.teamproject.petapet.domain.buy;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.member.service.MemberService;
import com.teamproject.petapet.web.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuyRepositoryTest {

    @Autowired
    BuyRepository buyRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

//    @Test
//    public void insertBuyDummies(){
//        IntStream.rangeClosed(1, 50).forEach(i -> {
//            long productId = (long) (Math.random() * 30) + 1;
//            long memberNum = (long) (Math.random() * 30) + 1;
//
//            Product product = Product.builder().productId(productId).build();
//            DuplicateMember member = DuplicateMember.builder().memberId("memberId" + memberNum).build();
//
//            Buy buy = Buy.builder().buyAddress("AddressNotBlank....").member(member).product(product).build();
//
//            buyRepository.save(buy);
//        });
//    }

//    @Test
//    void 저장(){
//        DuplicateMember memberA = memberService.findOne("memberA");
//        Product one = productService.findOne(1L);
//        Buy buy = new Buy("addr",
//                memberA,
//                one,
//                1L);
//
//        buyRepository.save(buy);
//        System.out.println("buy = " + buy);
//
//        }
//    }

}