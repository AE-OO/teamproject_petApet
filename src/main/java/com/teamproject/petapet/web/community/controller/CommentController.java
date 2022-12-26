package com.teamproject.petapet.web.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

//    @GetMapping("/{communityId}")
//    public String CommentMain(@PathVariable("communityId") String communityId, Model model){
//        model.addAttribute("communityId",communityId);
//        return "community/commentMain";
//    }

}
