package com.teamproject.petapet.web.community.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 박채원 22.12.14 작성
 * 장사론 22.12.27 추가
 */

public class CommunityRequestDTO {
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
