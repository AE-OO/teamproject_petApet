package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.domain.community.dto.CommentRequestDto;
import com.teamproject.petapet.web.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /* CREATE */
    @PostMapping("/community/{communityId}/comment")
    public ResponseEntity commentSave(@PathVariable Long communityId, @RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.commentSave("memberId1", communityId, dto));
    }

}
