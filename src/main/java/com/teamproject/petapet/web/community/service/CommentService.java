package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.web.community.commentDto.CommentDTO;
import com.teamproject.petapet.web.community.commentDto.CommentInsertDTO;
import org.springframework.data.domain.Page;


public interface CommentService {
    void insertComment(String memberId, CommentInsertDTO commentInsertDTO);
    Page<CommentDTO> getCommentPageList(Long communityId, int pageNum);
    int totalPages(Long communityId);
    void deleteComment(Long commentId);

}
