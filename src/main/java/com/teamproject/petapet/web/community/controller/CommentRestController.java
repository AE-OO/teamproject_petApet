package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.commentDto.CommentDTO;
import com.teamproject.petapet.web.community.commentDto.CommentInsertDTO;
import com.teamproject.petapet.web.community.service.CommentService;
import com.teamproject.petapet.web.product.fileupload.FileService;
import com.teamproject.petapet.web.product.fileupload.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {
    private final CommentService commentService;
    private final FileService fileService;

    @PostMapping
    public List<CommentDTO> commentList(@RequestParam Long communityId){
        return commentService.getCommentPageList(communityId,0).toList();
    }

    @PostMapping("/insert")
    public void commentInsert(Principal principal,
                              @RequestPart(name = "commentInsertDTO") CommentInsertDTO commentInsertDTO,
                              @RequestPart(name = "commentImg",required = false) MultipartFile commentImg) throws IOException {
        if(commentImg != null){
            commentInsertDTO.setCommentImg(fileService.storeFile(commentImg).getStoreFileName());
        }
        commentService.insertComment(principal.getName(),commentInsertDTO);
    }
}
