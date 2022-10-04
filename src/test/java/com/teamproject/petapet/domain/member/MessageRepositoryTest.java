package com.teamproject.petapet.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void insertMessageDummies(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            long memberNum = (long)(Math.random() * 30) + 1;
            Member member = Member.builder().memberId("memberId" + (i % 30 + 1)).build();

            Message message = Message.builder()
                    .messageReceiver("memberId" + memberNum)
                    .messageTitle("Message " + i)
                    .messageContent("Message Content " + i)
                    .member(member)
                    .build();

            messageRepository.save(message);
        });
    }

}