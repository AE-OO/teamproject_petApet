package com.teamproject.petapet.web.community.dto;

import com.teamproject.petapet.domain.community.Community;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

/**
 * 박채원 22.12.14 작성
 * 장사론 22.12.27 추가
 */

@Data
@Builder
public class CommunityDTO {
    private Long communityId;
    private String communityTitle;
    private String communityContent;
    private String communityCategory;
    private Long communityReport;
    private String communityDate;
    private String memberId;

    //사론 추가
    private String communitySubCategory;
    private String modifiedDate;
    private int viewCount;
    private int commentListSize;
    private String memberImg;


    public static CommunityDTO fromEntityForNotice(Community community) {
        return CommunityDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .communityContent(community.getCommunityContent())
                .build();
    }

    public static CommunityDTO fromEntityForCommunityListOfAdminPage(Community community) {
        return CommunityDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .memberId(community.getMember().getMemberId())
                .communityCategory(community.getCommunityCategory())
                .communityDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(community.getCreatedDate()))
                .communityReport(community.getCommunityReport())
                .build();
    }

    //커뮤니티 메인
    public static CommunityDTO fromEntityForCommunityMain(Community community, String modifiedDate) {
        return CommunityDTO.builder()
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

    //커뮤니티 게시물
    public static CommunityDTO fromEntityForCommunityPosts(Community community) {
        return CommunityDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .communityContent(community.getCommunityContent())
                .communityCategory(community.getCommunityCategory())
                .communitySubCategory(community.getCommunitySubCategory())
                .modifiedDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(community.getModifiedDate()))
                .viewCount(community.getViewCount())
                .memberId(community.getMember().getMemberId())
                .memberImg(community.getMember().getMemberImg() == null ? "0" : community.getMember().getMemberImg())
                .commentListSize(community.getComment().size())
                .build();
    }

    public static CommunityDTO fromEntityForUpdatePost(Community community) {
        return CommunityDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .communityContent(community.getCommunityContent())
                .communityCategory(community.getCommunityCategory())
                .communitySubCategory(community.getCommunitySubCategory())
                .build();
    }

}
