package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.dto.CommentDTO;
import com.teamproject.petapet.web.community.dto.CommentInsertDTO;
import com.teamproject.petapet.web.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping
    public List<CommentDTO> commentList(@RequestParam Long communityId){
        return commentService.getCommentList(communityId);
    }

    @PostMapping("/insert")
    public void write(Principal principal, @RequestBody CommentInsertDTO commentInsertDTO){
        commentService.insertComment(principal.getName(),commentInsertDTO);
    }
}
