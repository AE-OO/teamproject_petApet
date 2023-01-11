package com.teamproject.petapet.web.community.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 박채원 22.12.14 작성
 * 장사론 22.12.27 추가
 */

public class CommunityRequestDTO {
    @Data
    public class registerNotice {
        private Long communityId;
        private String communityTitle;
        private String communityContent;
        public Community toEntity(){
            Community community = Community.builder()
                    .communityId(communityId)
                    .communityTitle(communityTitle)
                    .communityContent(communityContent)
                    .communityCategory("공지사항")
                    .member(Member.builder().memberId("admin").build())
                    .build();
            return community;
        }
    }

    //커뮤니티 게시글 작성 DTO
    @Data
    public class InsertDTO {
        @NotBlank(message = "제목 입력은 필수입니다.")
        private String communityTitle;
        @NotBlank(message = "내용 입력은 필수입니다.")
        private String communityContent;
        @NotBlank
        private String communityCategory;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String communitySubCategory;

        public Community toEntity(String memberId) {
            return Community.builder()
                    .communityTitle(communityTitle)
                    .communityContent(communityContent)
                    .communityCategory(communityCategory)
                    .communitySubCategory(communitySubCategory)
                    .member(Member.builder().memberId(memberId).build())
                    .build();
        }
    }

    //커뮤니티 게시물 수정 DTO
    @Data
    public class UpdateDTO {

        @NotNull
        private Long communityId;
        @NotBlank(message = "제목 입력은 필수입니다.")
        private String communityTitle;
        @NotBlank(message = "내용 입력은 필수입니다.")
        private String communityContent;
        @NotBlank
        private String communityCategory;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String communitySubCategory;

        //기존에 있던 게시물 사진 없어졌을 떄 필요
        private List<String> deleteImg;

        public Community toEntity() {
            return Community.builder()
                    .communityTitle(communityTitle)
                    .communityContent(communityContent)
                    .communityCategory(communityCategory)
                    .communitySubCategory(communitySubCategory)
                    .build();
        }

    }
}
