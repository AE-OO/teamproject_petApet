package com.teamproject.petapet.web.community.dto;

import com.teamproject.petapet.domain.community.Comment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {

    private String commentContent;
    private String commentDate;
    private String memberId;
    private String memberImg;

    public static CommentDTO fromEntity(Comment comment, String commentDate) {
        return CommentDTO.builder()
                .commentContent(comment.getCommentContent())
                .commentDate(commentDate)
                .memberId(comment.getMember().getMemberId())
                .memberImg(comment.getMember().getMemberImg()==null?"0":comment.getMember().getMemberImg())
                .build();
    }
}
