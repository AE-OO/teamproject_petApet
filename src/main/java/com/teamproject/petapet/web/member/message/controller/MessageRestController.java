package com.teamproject.petapet.web.member.message.controller;

import com.teamproject.petapet.web.member.message.MessageService;
import com.teamproject.petapet.web.member.message.dto.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
//@CrossOrigin
public class MessageRestController {
    private final MessageService messageService;

    @PostMapping("/send")
    public void messageSave(Principal principal, @RequestBody MessageRequestDTO.InsertDTO insertDTO){
        System.out.println(insertDTO);
    }
}
