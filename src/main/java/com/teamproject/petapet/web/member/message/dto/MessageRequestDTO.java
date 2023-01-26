package com.teamproject.petapet.web.member.message.dto;

import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.member.Message;
import lombok.Data;


public class MessageRequestDTO {

    @Data
    public static class InsertDTO {
        private String messageReceiver;
        private String messageContent;

//        private int roomNumber;

        public Message toEntity(String memberId,int roomNumber) {
            Message message = Message.builder()
                    .messageReceiver(messageReceiver)
                    .messageContent(messageContent)
                    .roomNumber(roomNumber)
                    .member(Member.builder().memberId(memberId).build())
                    .build();
            return message;
        }
    }
}
