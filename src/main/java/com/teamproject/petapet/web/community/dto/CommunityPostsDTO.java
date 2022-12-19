package com.teamproject.petapet.web.community.dto;

import com.teamproject.petapet.domain.community.Community;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommunityPostsDTO {
    private Long communityId;

    private String communityTitle;
    private String communityContent;

    private String communityCategory;

    private String communitySubCategory;

    private LocalDateTime communityDate;

    private int viewCount;

    private String memberId;

    private String memberImg;

    private int commentListSize;

    public static CommunityPostsDTO fromEntity(Community community) {
        return CommunityPostsDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .communityContent(community.getCommunityContent())
                .communityCategory(community.getCommunityCategory())
                .communitySubCategory(community.getCommunitySubCategory())
                .communityDate(community.getCommunityDate())
                .viewCount(community.getViewCount())
                .memberId(community.getMember().getMemberId())
                .memberImg(community.getMember().getMemberImg()==null?"0":community.getMember().getMemberImg())
                .commentListSize(community.getComment().size())
                .build();
    }

}
