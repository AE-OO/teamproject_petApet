package com.teamproject.petapet.web.community.dto;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import lombok.Data;

/**
 * 박채원 22.12.14 작성
 */

public class CommunityRequestDTO {
    @Data
    public static class registerNotice {
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
}
