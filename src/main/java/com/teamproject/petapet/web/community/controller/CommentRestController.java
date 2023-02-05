package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.dto.CommentDTO;
import com.teamproject.petapet.web.community.dto.CommentRequestDTO;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {
    private final CommentService commentService;
    private final FileService fileService;

    //댓글 메인 List
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CommentDTO>> getCommentList(@RequestParam Long communityId) {
        return ResponseEntity.ok().body(commentService.getCommentList(communityId,
                commentService.totalPages(communityId) == 0 ? 0 : commentService.totalPages(communityId) - 1));
    }

    //댓글 페이지 선택 List
    @PostMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<CommentDTO>> goPage(@RequestParam Long communityId, @RequestParam int pageNum) {
        return ResponseEntity.ok().body(commentService.getCommentList(communityId, pageNum));
    }

    //댓글 저장
    @PostMapping("/insert")
    public void commentInsert(Principal principal,
                              @RequestPart(name = "commentInsertDTO") CommentRequestDTO.InsertDTO insertDTO,
                              @RequestPart(name = "commentImg", required = false) MultipartFile commentImg) throws IOException {
        if (commentImg != null) {
            insertDTO.setCommentImg(fileService.storeFile(commentImg).getStoreFileName());
        }
        commentService.insertComment(principal.getName(), insertDTO);
    }

    //대댓글 저장
    @PostMapping("/insertReply")
    public void replyInsert(Principal principal,
                            @RequestPart(name = "insertReplyDTO") CommentRequestDTO.InsertReplyDTO insertReplyDTO,
                            @RequestPart(name = "commentImg", required = false) MultipartFile commentImg) throws IOException {
        if (commentImg != null) {
            insertReplyDTO.setCommentImg(fileService.storeFile(commentImg).getStoreFileName());
        }
        commentService.insertReply(principal.getName(), insertReplyDTO);
    }

    //댓글 삭제
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

    //댓글 수정
    @PatchMapping("/update")
    public void commentUpdate(Principal principal,
                              @Valid @RequestPart(name = "commentUpdateDTO") CommentRequestDTO.UpdateDTO updateDTO,
                              @RequestPart(name = "commentUpdateImg", required = false) MultipartFile commentUpdateImg) throws IOException {
        if (!principal.getName().equals(updateDTO.getMemberId())) {
            ResponseEntity.badRequest();
            return;
        }
        if (updateDTO.getDeleteImg() != null) {
            fileService.deleteFile(updateDTO.getDeleteImg());
            updateDTO.setCommentImg(commentUpdateImg == null ? null : fileService.storeFile(commentUpdateImg).getStoreFileName());
        }
        commentService.updateComment(updateDTO);
        ResponseEntity.ok();
    }

    //내정보 - 글목록 - 내가 쓴 댓글 List
    @PostMapping("/loginMemberWritingList")
    public ResponseEntity<Page<CommentDTO>> getLoginMemberWritingList(Principal principal,
                                                                      @RequestParam(defaultValue = "0") int pageNum,
                                                                      @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok().body(commentService.getLoginMemberWritingList(principal.getName(), pageNum, pageSize));
    }

    //내정보 - 글목록 - 내가 쓴 댓글 삭제
    @PostMapping("/myWritingDelete")
    public void myWritingDelete(@RequestParam(value = "deleteId[]") List<Long> deleteId,
                                @RequestParam(value = "deleteImg[]") List<String> deleteImg) {
        //내가 쓴 댓글에 이미지가 있다면 로컬에 저장된 이미지 삭제
        deleteImg.forEach(img -> fileService.deleteFile(img));
        deleteId.forEach(id -> commentService.deleteComment(id));
    }

}
