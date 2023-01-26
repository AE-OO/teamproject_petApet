package com.teamproject.petapet.web.member.message.controller;

import com.teamproject.petapet.web.member.message.service.MessageService;
import com.teamproject.petapet.web.member.message.dto.MessageDTO;
import com.teamproject.petapet.web.member.message.dto.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
        //메세지 읽음
        messageService.updateMessageCheck(principal.getName(),messageReceiver);
        return new ResponseEntity<>(messageService.getMessageList(principal.getName(),messageReceiver,pageNum), HttpStatus.OK);
    }

    @PostMapping("/getMessageRoomList")
    public ResponseEntity<Page<MessageDTO>> getMessageRoomList(Principal principal,
                                                               @RequestParam(defaultValue = "0") int pageNum,
                                                               @RequestParam(defaultValue = "20") int pageSize){
        return new ResponseEntity<>(messageService.getMessageRoomList(principal.getName(), pageNum, pageSize), HttpStatus.OK);
    }



}
