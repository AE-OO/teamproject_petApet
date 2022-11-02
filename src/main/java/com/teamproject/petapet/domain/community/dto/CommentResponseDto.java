package com.teamproject.petapet.domain.community.dto;

import com.teamproject.petapet.domain.community.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String commentContent;
    private String commentCreatedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
//    private String commentModifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
    private String memberId;
    private Long communityId;

    /* Entity -> Dto */
    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.commentContent = comment.getCommentContent();
        this.commentCreatedDate = comment.getCommentCreatedDate();
        this.memberId = comment.getMember().getMemberId();
        this.communityId = comment.getCommunity().getCommunityId();
    }

}
