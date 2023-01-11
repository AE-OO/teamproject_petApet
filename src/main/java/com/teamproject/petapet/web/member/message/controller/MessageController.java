package com.teamproject.petapet.web.member.message.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.Socket;

@Controller
@RequestMapping("/message")
public class MessageController extends Socket {

    @GetMapping("/{memberId}")
    public String messageForm(@PathVariable String memberId){
        return "message/messageForm";
    }
}
