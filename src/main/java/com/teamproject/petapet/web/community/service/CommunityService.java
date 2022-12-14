package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.web.community.dto.CommunityDTO;
import com.teamproject.petapet.web.community.dto.CommunityRequestDTO;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface CommunityService {
    List<Community> getCommunityList();
    void deleteCommunity(Long communityId);
    void addCommunityReport(Long communityId);
    List<Community> getNotice();
    void registerNotice(CommunityRequestDTO.registerNotice registerNotice);
    CommunityDTO getOneNotice(Long noticeId);
    void updateNotice(CommunityRequestDTO.registerNotice registerNotice);
    void deleteNotice(Long noticeId);
}
