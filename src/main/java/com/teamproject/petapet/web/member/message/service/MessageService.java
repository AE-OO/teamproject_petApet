package com.teamproject.petapet.web.member.message.service;

import com.teamproject.petapet.web.member.message.dto.MessageDTO;
import com.teamproject.petapet.web.member.message.dto.MessageRequestDTO;
import org.springframework.data.domain.Page;

public interface MessageService {
    void insertMessage(String memberId,MessageRequestDTO.InsertDTO insertDTO);
    Page<MessageDTO> getMessageList(String memberId, String messageReceiver,int pageNum);
    void updateMessageCheck(String memberId, String messageReceiver);
    Page<MessageDTO> getMessageRoomList(String memberId, int pageNum, int pageSize);

}
