package com.teamproject.petapet.web.member.message;

import com.teamproject.petapet.web.member.message.dto.MessageRequestDTO;

public interface MessageService {

    void insertMessage(String memberId,MessageRequestDTO.InsertDTO insertDTO);
}
