package com.teamproject.petapet.web.community.commentDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentInsertDTO {
    @NotBlank
    private String commentContent;
    @NotBlank
    private Long communityId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String commentImg;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String commentSecret;

    public Comment toEntity(String memberId) {
        return Comment.builder()
                .commentContent(commentContent)
                .community(Community.builder().communityId(communityId).build())
                .commentImg(commentImg)
                .commentSecret(commentSecret)
                .member(Member.builder().memberId(memberId).build())
                .build();
    }
}