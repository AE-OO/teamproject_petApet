package com.teamproject.petapet.web.community.dto;

import com.teamproject.petapet.domain.community.Comment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {

    private Long commentId;
    private String commentContent;
    private String communityTitle;
    private String modifiedDate;
    private String commentImg;
    private String commentSecret;
    private int isDeleted;
    private Long replyId;
    private String memberId;
    private String memberImg;
    public static CommentDTO fromEntity(final Comment comment, String modifiedDate) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .modifiedDate(modifiedDate)
                .commentImg(comment.getCommentImg()==null?"0":comment.getCommentImg())
                .commentSecret(comment.getCommentSecret())
                .isDeleted(comment.getDepth())
                .replyId(comment.getReplyId())
                .memberId(comment.getMember().getMemberId())
                .memberImg(comment.getMember().getMemberImg()==null?"0":comment.getMember().getMemberImg())
                .build();
    }
}
