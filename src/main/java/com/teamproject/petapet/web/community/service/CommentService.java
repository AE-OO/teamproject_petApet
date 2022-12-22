package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.web.community.dto.CommentDTO;
import com.teamproject.petapet.web.community.dto.CommentInsertDTO;

import java.util.List;

public interface CommentService {
    void insertComment(String memberId, CommentInsertDTO commentInsertDTO);
    List<CommentDTO> getCommentList(Long communityId);
}
