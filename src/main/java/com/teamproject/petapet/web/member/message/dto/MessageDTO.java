package com.teamproject.petapet.web.member.message.dto;

import com.teamproject.petapet.domain.member.Message;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class MessageDTO {

    private Long messageId;

    private String messageReceiver;

    private String messageContent;

    private String messageDate;

    private boolean messageCheck;

    private String memberId;

    public static MessageDTO fromEntity(final Message message) {
        return MessageDTO.builder()
                .messageReceiver(message.getMessageReceiver())
                .messageContent(message.getMessageContent())
                .messageDate(message.getMessageDate().toLocalDate().isBefore(LocalDate.now())?
                        DateTimeFormatter.ofPattern("yy.MM.dd").format(message.getMessageDate()):
                        DateTimeFormatter.ofPattern("a hh:mm").format(message.getMessageDate()))
                .messageCheck(message.isMessageCheck())
                .memberId(message.getMember().getMemberId())
                .build();
    }

    public static MessageDTO fromEntityForMessageRoomList(final Message message) {
        return MessageDTO.builder()
                .messageId(message.getMessageId())
                .messageReceiver(message.getMessageReceiver())
                .messageDate(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm").format(message.getMessageDate()))
                .build();
    }

}
