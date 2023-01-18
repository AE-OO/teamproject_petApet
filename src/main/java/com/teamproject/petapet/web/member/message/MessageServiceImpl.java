package com.teamproject.petapet.web.member.message;

import com.teamproject.petapet.domain.member.MessageRepository;
import com.teamproject.petapet.web.community.dto.CommunityDTO;
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
        messageRepository.save(insertDTO.toEntity(memberId));
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
    public List<String> getReceiverList(String memberId) {
        return messageRepository.getReceiverList(memberId);

    }


}
