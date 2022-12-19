package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.web.community.commentDto.CommentDTO;
import com.teamproject.petapet.web.community.commentDto.CommentInsertDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    void insertComment(String memberId, CommentInsertDTO commentInsertDTO);
    Page<CommentDTO> getCommentPageList(Long communityId, int pageNum);
}
