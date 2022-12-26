package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.web.community.communityDto.CommunityInsertDTO;
import com.teamproject.petapet.web.community.communityDto.CommunityListDTO;
import com.teamproject.petapet.web.community.communityDto.CommunityPostsDTO;
import com.teamproject.petapet.web.community.communityDto.CommunityUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface CommunityService {
    List<Community> getProductList();
    void deleteCommunity(Long communityId);
    void addCommunityReport(Long communityId);
    void insertCommunity(String memberId, CommunityInsertDTO communityInsertDTO);

    Page<CommunityListDTO> getCommunityList(int pageNum,int pageSize, String communityCategory);
    Page<CommunityPostsDTO> getCommunityMemberPost(int pageNum,int pageSize, String memberId);
    Long countTodayCommunity(String communityCategory);
    void viewCountPlus(Long communityId);
    CommunityPostsDTO loadCommunityPosts(Long communityId);
    CommunityUpdateDTO loadCommunityUpdatePost(String memberId, Long communityId);
    void updateCommunity(String memberId,CommunityUpdateDTO communityUpdateDTO);

}
