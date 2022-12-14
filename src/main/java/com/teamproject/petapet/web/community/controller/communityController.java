package com.teamproject.petapet.web.community.controller;


import com.teamproject.petapet.web.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class communityController {

    private final CommunityService communityService;

    @GetMapping("/community")
    public String communityMain(){return "community/communityMain";}


}

