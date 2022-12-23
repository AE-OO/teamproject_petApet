package com.teamproject.petapet.web.community.dto;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CommentInsertDTO {
    @NotBlank
    private String commentContent;
    @NotBlank
    private Long communityId;

    public Comment toEntity(String memberId) {
        return Comment.builder()
                .commentContent(commentContent)
                .community(Community.builder().communityId(communityId).build())
                .commentSecret(false)
                .member(Member.builder().memberId(memberId).build())
                .build();
    }
}