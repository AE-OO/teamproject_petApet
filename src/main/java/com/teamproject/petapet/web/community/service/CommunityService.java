package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.web.community.dto.CommunityInsertDTO;
import com.teamproject.petapet.web.community.dto.CommunityListDTO;
import com.teamproject.petapet.web.community.dto.CommunityPostsDTO;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

/**
 * 박채원 22.10.09 작성
 */

public interface CommunityService {
    List<Community> getProductList();
    void deleteCommunity(Long communityId);
    void addCommunityReport(Long communityId);

    void insertCommunity(String memberId, CommunityInsertDTO communityInsertDTO);

//    Page<CommunityListDTO> getCommunityList(int pageNum,int pageSize);
    Page<CommunityListDTO> getCommunityList(int pageNum,int pageSize, String communityCategory);

//    Long countTodayCommunity();
    Long countTodayCommunity(String communityCategory);
    void viewCountPlus(Long communityId);

    CommunityPostsDTO loadCommunityPosts(Long communityId);

}
