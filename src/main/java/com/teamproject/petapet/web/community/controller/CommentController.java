package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    @GetMapping("/{commentId}")
    public String commentId(@PathVariable("commentId") Long commentId){return "/community/commentMain";}

}
