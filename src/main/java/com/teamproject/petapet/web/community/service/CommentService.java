package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.web.community.commentDto.CommentDTO;
import com.teamproject.petapet.web.community.commentDto.CommentRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface CommentService {
    void insertComment(String memberId, CommentRequestDTO.InsertDTO insertDTO);
    void insertReply(String memberId, CommentRequestDTO.InsertReplyDTO insertReplyDTO);
    Page<CommentDTO> getCommentPageList(Long communityId, int pageNum);
    int totalPages(Long communityId);
    void deleteComment(Long commentId);
    void updateComment(CommentRequestDTO.UpdateDTO updateDTO);

}
