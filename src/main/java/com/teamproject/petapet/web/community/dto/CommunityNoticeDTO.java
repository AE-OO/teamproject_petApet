package com.teamproject.petapet.web.community.dto;

import lombok.*;

/**
 * 박채원 22.12.12 작성
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommunityNoticeDTO {
    private Long communityId;
    private String communityTitle;
    private String communityContent;
    private String communityCategory;
    private String memberId;
}
