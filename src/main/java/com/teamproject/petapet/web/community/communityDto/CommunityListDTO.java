package com.teamproject.petapet.web.community.communityDto;

import com.teamproject.petapet.domain.community.Community;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class CommunityListDTO {
    private Long communityId;

    private String communityTitle;

    private String communityCategory;

    private String communitySubCategory;

    private String modifiedDate;

    private int viewCount;

    private String memberId;

    private int commentListSize;

    public static CommunityListDTO fromEntity(Community community, String modifiedDate) {
        return CommunityListDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .communityCategory(community.getCommunityCategory())
                .communitySubCategory(community.getCommunitySubCategory())
                .modifiedDate(modifiedDate)
                .viewCount(community.getViewCount())
                .memberId(community.getMember().getMemberId())
                .commentListSize(community.getComment().size())
                .build();
    }

}
