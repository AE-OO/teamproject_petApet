package com.teamproject.petapet.web.member.message;

import com.teamproject.petapet.domain.member.MessageRepository;
import com.teamproject.petapet.web.member.message.dto.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;

    @Override
    public void insertMessage(String memberId ,MessageRequestDTO.InsertDTO insertDTO) {
        messageRepository.save(insertDTO.toEntity(memberId));
    }
}
