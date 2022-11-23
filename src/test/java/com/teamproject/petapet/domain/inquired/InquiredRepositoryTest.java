package com.teamproject.petapet.domain.inquired;

import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.web.Inquired.service.InquiredService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InquiredRepositoryTest {

    @Autowired
    InquiredRepository inquiredRepository;

    @Autowired
    InquiredService inquiredService;

//    @Test
    public void insertInquiredDummies(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Member member = Member.builder().memberId("memberId" + (i % 30 + 1)).build();

            Inquired inquired = Inquired.builder()
                    .inquiredTitle("Inquired " + i)
                    .inquiredContent("Inquired Content " + i)
                    .inquiredCategory("환불문의")
                    .member(member)
                    .build();

            inquiredRepository.save(inquired);
        });
    }

//    @Test
//    void setCheck() {
//
//        Inquired inquired = new Inquired(1L,true);
//        Long inquiredId = inquired.getInquiredId();
//        boolean checked = inquired.isChecked();
//
//        inquiredRepository.setCheck(checked,inquiredId);
//        Assertions.assertEquals(1L,inquired.getInquiredId());
//    }
}