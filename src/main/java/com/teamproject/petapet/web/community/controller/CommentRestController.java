package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.commentDto.CommentDTO;
import com.teamproject.petapet.web.community.commentDto.CommentRequestDTO;
import com.teamproject.petapet.web.community.service.CommentService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
        return new ResponseEntity<>(commentService.getCommentPageList(communityId,
                commentService.totalPages(communityId) == 0 ? 0 : commentService.totalPages(communityId) - 1),
                HttpStatus.OK);
    }

    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CommentDTO>> goPage(@RequestParam Long communityId, @RequestParam int pageNum) {
        return new ResponseEntity<>(commentService.getCommentPageList(communityId, pageNum), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public void commentInsert(Principal principal,
                              @RequestPart(name = "commentInsertDTO") CommentRequestDTO.InsertDTO insertDTO,
                              @RequestPart(name = "commentImg", required = false) MultipartFile commentImg) throws IOException {
        if (commentImg != null) {
            insertDTO.setCommentImg(fileService.storeFile(commentImg).getStoreFileName());
        }
        commentService.insertComment(principal.getName(), insertDTO);
    }

    @PostMapping("/insertReply")
    public void replyInsert(Principal principal,
                              @RequestPart(name = "insertReplyDTO") CommentRequestDTO.InsertReplyDTO insertReplyDTO,
                              @RequestPart(name = "commentImg", required = false) MultipartFile commentImg) throws IOException {
        System.out.println(insertReplyDTO);
        if (commentImg != null) {
            insertReplyDTO.setCommentImg(fileService.storeFile(commentImg).getStoreFileName());
        }
        commentService.insertReply(principal.getName(), insertReplyDTO);
    }

    @DeleteMapping("/delete")
    public void commentDelete(Principal principal,
                              @RequestParam Long commentId, @RequestParam String memberId, @RequestParam(required = false) String commentImg) {
        if (!principal.getName().equals(memberId)) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return;
        }
        if (commentImg != null) {
            fileService.deleteFile(commentImg);
        }
        commentService.deleteComment(commentId);
    }

    @PatchMapping("/update")
    public void commentUpdate(Principal principal,
                              @Valid @RequestPart(name = "commentUpdateDTO") CommentRequestDTO.UpdateDTO updateDTO,
                              @RequestPart(name = "commentUpdateImg", required = false) MultipartFile commentUpdateImg) throws IOException {
        if (!principal.getName().equals(updateDTO.getMemberId())) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return;
        }
        if (updateDTO.getDeleteImg() != null) {
            fileService.deleteFile(updateDTO.getDeleteImg());
            updateDTO.setCommentImg(commentUpdateImg == null ? null : fileService.storeFile(commentUpdateImg).getStoreFileName());
        }
        commentService.updateComment(updateDTO);
        new ResponseEntity<>(HttpStatus.OK);
    }
}
