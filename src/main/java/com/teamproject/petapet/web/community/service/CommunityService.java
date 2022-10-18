package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Community;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface CommunityService {
    List<Community> getProductList();
    void deleteCommunity(Long communityId);
    void addCommunityReport(Long communityId);
}
