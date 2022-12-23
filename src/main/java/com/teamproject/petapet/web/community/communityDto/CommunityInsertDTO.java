package com.teamproject.petapet.web.community.communityDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CommunityInsertDTO {
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
