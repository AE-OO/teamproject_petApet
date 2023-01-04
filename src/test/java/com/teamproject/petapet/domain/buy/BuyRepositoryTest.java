package com.teamproject.petapet.domain.buy;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class BuyRepositoryTest {

    @Autowired
    BuyRepository buyRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

    @Test
    public void insertBuyDummies(){
        IntStream.rangeClosed(1, 50).forEach(i -> {
            long productId = (long) (Math.random() * 3) + 1;
            long memberNum = (long) (Math.random() * 30) + 1;

            Product product = Product.builder().productId(productId).build();
            Member member = Member.builder().memberId("member3921").build();

            Buy buy = Buy.builder().buyAddress("Address....").member(member).product(product).build();

            buyRepository.save(buy);
        });
    }

//    @Test
//    void 저장(){
//        Member memberA = memberService.findOne("memberA");
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