package com.teamproject.petapet.web.member.message;

import com.teamproject.petapet.web.member.message.dto.MessageDTO;
import com.teamproject.petapet.web.member.message.dto.MessageRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {
    void insertMessage(String memberId,MessageRequestDTO.InsertDTO insertDTO);
    Page<MessageDTO> getMessageList(String memberId, String messageReceiver,int pageNum);
    void updateMessageCheck(String memberId, String messageReceiver);
    List<String> getReceiverList(String memberId);
}
