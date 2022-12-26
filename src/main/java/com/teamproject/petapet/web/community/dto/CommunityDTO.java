package com.teamproject.petapet.web.community.dto;

import com.teamproject.petapet.domain.community.Community;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.time.format.DateTimeFormatter;

/**
 * 박채원 22.12.14 작성
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

    public static CommunityDTO fromEntityForNotice(Community community){
        return CommunityDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .communityContent(community.getCommunityContent())
                .build();
    }

    public static CommunityDTO fromEntityForCommunityListOfAdminPage(Community community){
        return CommunityDTO.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getCommunityTitle())
                .memberId(community.getMember().getMemberId())
                .communityCategory(community.getCommunityCategory())
                .communityDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(community.getCommunityDate()))
                .communityReport(community.getCommunityReport())
                .build();
    }
}
