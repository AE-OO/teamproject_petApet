package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.web.community.dto.CommentDTO;
import com.teamproject.petapet.web.community.dto.CommentRequestDTO;
import org.springframework.data.domain.Page;


public interface CommentService {
    void insertComment(String memberId, CommentRequestDTO.InsertDTO insertDTO);
    void insertReply(String memberId, CommentRequestDTO.InsertReplyDTO insertReplyDTO);
    Page<CommentDTO> getCommentPageList(Long communityId, int pageNum);
    int totalPages(Long communityId);
    void deleteComment(Long commentId);
    void updateComment(CommentRequestDTO.UpdateDTO updateDTO);

    Page<CommentDTO> getLoginMemberWritingList(String memberId, int pageNum, int pageSize);

}
