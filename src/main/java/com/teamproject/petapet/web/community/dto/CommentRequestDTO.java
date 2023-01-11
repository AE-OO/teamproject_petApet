package com.teamproject.petapet.web.community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentRequestDTO {

    @Data
    public static class InsertDTO {
        @NotBlank
        private String commentContent;
        @NotBlank
        private Long communityId;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String commentImg;
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

    @Data
    public static class InsertReplyDTO {
        @NotBlank
        private String commentContent;
        @NotNull
        private Long replyId;
        @NotBlank
        private Long communityId;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String commentImg;
        private String commentSecret;

        public Comment toEntity(String memberId) {
            return Comment.builder()
                    .commentContent(commentContent)
                    .community(Community.builder().communityId(communityId).build())
                    .commentImg(commentImg)
                    .commentSecret(commentSecret)
                    .replyId(replyId)
                    .depth(1)
                    .member(Member.builder().memberId(memberId).build())
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {

        @NotNull
        private Long commentId;
        @NotBlank
        private String memberId;
        @NotBlank
        private String commentContent;
//        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String commentImg;
        private String commentSecret;
        private String deleteImg;

        public Comment toEntity() {
            return Comment.builder()
                    .commentContent(commentContent)
                    .commentImg(commentImg)
                    .commentSecret(commentSecret)
                    .build();
        }
    }
}
