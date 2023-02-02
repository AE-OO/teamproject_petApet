package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.CommentRepository;
import com.teamproject.petapet.web.community.dto.CommentDTO;
import com.teamproject.petapet.web.community.dto.CommentRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public void insertComment(String memberId, CommentRequestDTO.InsertDTO insertDTO) {
        commentRepository.updateReplyId(commentRepository.save(insertDTO.toEntity(memberId)).getCommentId());
    }

    @Override
    public void insertReply(String memberId, CommentRequestDTO.InsertReplyDTO insertReplyDTO) {
        commentRepository.save(insertReplyDTO.toEntity(memberId));
    }

    @Override
    public Page<CommentDTO> getCommentList(Long communityId, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 20, Sort.by("replyId").ascending().and(Sort.by("createdDate")));
        return commentRepository.commentList(communityId,pageable).map(c -> CommentDTO.fromEntity(c));
    }

    @Override
    public int totalPages(Long communityId) {
        int totalCount = commentRepository.countCommentByCommunityCommunityId(communityId);
        int totalPage = totalCount / 20;
        if (totalCount % 20 > 0) {
            totalPage++;
        }
        return totalPage;
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void updateComment(CommentRequestDTO.UpdateDTO updateDTO) {
        Comment comment = commentRepository.findById(updateDTO.getCommentId())
                .orElseThrow(() -> new NullPointerException(updateDTO.getCommentId() + "-> 데이터베이스에서 찾을 수 없습니다."));
        comment.update(updateDTO.toEntity());
    }

    @Override
    public Page<CommentDTO> getLoginMemberWritingList(String memberId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("commentId").descending());
        return commentRepository.findAllByMemberMemberId(memberId,pageable)
                .map(comment -> CommentDTO.fromEntityForWritingList(comment));
    }

}
