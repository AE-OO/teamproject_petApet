package com.teamproject.petapet.web.member.message.service;

import com.teamproject.petapet.domain.member.MessageRepository;
import com.teamproject.petapet.web.member.message.dto.MessageDTO;
import com.teamproject.petapet.web.member.message.dto.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;

    @Override
    public void insertMessage(String memberId ,MessageRequestDTO.InsertDTO insertDTO) {
        int roomNumber;
        if(messageRepository.existMessageList(memberId,insertDTO.getMessageReceiver())==0){
            roomNumber = messageRepository.maxRoomNumber()+1;
        }else {
            roomNumber = messageRepository.getRoomNumber(memberId,insertDTO.getMessageReceiver());
        }
        messageRepository.save(insertDTO.toEntity(memberId,roomNumber));
    }

    @Override
    public Page<MessageDTO> getMessageList(String memberId, String messageReceiver, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 50, Sort.by("messageId").descending());
        return messageRepository.getMessageList(memberId,messageReceiver,pageable)
                .map(message -> MessageDTO.fromEntity(message));
    }

    @Override
    public void updateMessageCheck(String memberId, String messageReceiver) {
        messageRepository.updateMessageCheck(memberId, messageReceiver);
    }

    @Override
    public Page<MessageDTO> getMessageRoomList(String memberId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("messageId").descending());
        return messageRepository.getMessageRoomList(memberId,pageable).map(m -> MessageDTO.fromEntity(m));
    }

}
