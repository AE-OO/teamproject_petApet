package com.teamproject.petapet.web.community.communityDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.teamproject.petapet.domain.community.Community;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CommunityUpdateDTO {

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

    public static CommunityUpdateDTO fromEntity(Community community) {
        return CommunityUpdateDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .communityContent(community.getCommunityContent())
                .communityCategory(community.getCommunityCategory())
                .communitySubCategory(community.getCommunitySubCategory())
                .build();
    }

    public Community toEntity() {
        return Community.builder()
                .communityId(communityId)
                .communityTitle(communityTitle)
                .communityContent(communityContent)
                .communityCategory(communityCategory)
                .communitySubCategory(communitySubCategory)
                .build();
    }

}
