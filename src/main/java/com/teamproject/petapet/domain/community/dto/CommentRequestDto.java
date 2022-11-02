package com.teamproject.petapet.domain.community.dto;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {
    private Long commentId;
    private String commentContent;
    private String commentCreatedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
//    private String commentModifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
    private Member member;
    private Community community;

    /* DTO -> Entity */
    public Comment toEntity() {
        Comment comment = Comment.builder()
                .commentId(commentId)
                .commentContent(commentContent)
                .commentCreatedDate(commentCreatedDate)
                .member(member)
                .community(community)
                .build();

        return comment;
    }

}
