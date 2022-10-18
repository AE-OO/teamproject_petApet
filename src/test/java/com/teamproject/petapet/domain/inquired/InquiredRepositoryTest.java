package com.teamproject.petapet.domain.inquired;

import com.teamproject.petapet.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InquiredRepositoryTest {

    @Autowired
    InquiredRepository inquiredRepository;

    @Test
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

}