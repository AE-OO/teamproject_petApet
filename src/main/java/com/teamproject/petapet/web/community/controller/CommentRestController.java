package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.commentDto.CommentDTO;
import com.teamproject.petapet.web.community.commentDto.CommentInsertDTO;
import com.teamproject.petapet.web.community.service.CommentService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {
    private final CommentService commentService;
    private final FileService fileService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CommentDTO>> getApplyList(@RequestParam Long communityId) {
        if (commentService.totalPages(communityId) == 0) {
            return new ResponseEntity<>(commentService.getCommentPageList(communityId, 0), HttpStatus.OK);
        }
        return new ResponseEntity<>(commentService.getCommentPageList(communityId, commentService.totalPages(communityId)-1), HttpStatus.OK);
    }

    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CommentDTO>> goPage(@RequestParam Long communityId, @RequestParam int pageNum) {
        return new ResponseEntity<>(commentService.getCommentPageList(communityId, pageNum), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public void commentInsert(Principal principal,
                              @RequestPart(name = "commentInsertDTO") CommentInsertDTO commentInsertDTO,
                              @RequestPart(name = "commentImg", required = false) MultipartFile commentImg) throws IOException {
        if (commentImg != null) {
            commentInsertDTO.setCommentImg(fileService.storeFile(commentImg).getStoreFileName());
        }
        commentService.insertComment(principal.getName(), commentInsertDTO);
    }

    @DeleteMapping("/delete")
    public void commentDelete(Principal principal,@RequestParam Long commentId){
        commentService.deleteComment(commentId);
    }

}
