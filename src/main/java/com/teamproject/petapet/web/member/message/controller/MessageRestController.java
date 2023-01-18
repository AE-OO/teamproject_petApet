package com.teamproject.petapet.web.member.message.controller;

import com.teamproject.petapet.web.community.dto.CommunityDTO;
import com.teamproject.petapet.web.member.message.MessageService;
import com.teamproject.petapet.web.member.message.dto.MessageDTO;
import com.teamproject.petapet.web.member.message.dto.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageRestController {
    private final MessageService messageService;

    @PostMapping("/send")
    public void messageSave(Principal principal, @RequestBody MessageRequestDTO.InsertDTO insertDTO) {
        messageService.insertMessage(principal.getName(), insertDTO);
    }

    @PostMapping("/getMessageList")
    public ResponseEntity<Page<MessageDTO>> getMessageList(Principal principal,
                                                           String messageReceiver,
                                                           @RequestParam(defaultValue = "0") int pageNum){
        //리스트를 불러올 때 메세지 읽음으로 표시
        messageService.updateMessageCheck(principal.getName(),messageReceiver);
        return new ResponseEntity<>(messageService.getMessageList(principal.getName(),messageReceiver,pageNum), HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<List<String>> test(Principal principal) {
        return new ResponseEntity<>(messageService.getReceiverList(principal.getName()), HttpStatus.OK);
    }
}
