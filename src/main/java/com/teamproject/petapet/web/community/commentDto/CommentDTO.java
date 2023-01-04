package com.teamproject.petapet.web.community.commentDto;

import com.teamproject.petapet.domain.community.Comment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {

    private Long commentId;
    private String commentContent;
    private String modifiedDate;
    private String commentImg;
    private String commentSecret;
    private String memberId;
    private String memberImg;
    public static CommentDTO fromEntity(Comment comment, String modifiedDate) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .modifiedDate(modifiedDate)
                .commentImg(comment.getCommentImg()==null?"0":comment.getCommentImg())
                .commentSecret(comment.getCommentSecret())
                .memberId(comment.getMember().getMemberId())
                .memberImg(comment.getMember().getMemberImg()==null?"0":comment.getMember().getMemberImg())
                .build();
    }
}
