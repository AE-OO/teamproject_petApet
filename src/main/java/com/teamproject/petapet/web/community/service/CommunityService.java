package com.teamproject.petapet.web.community.service;

import org.springframework.data.domain.Page;
import com.teamproject.petapet.web.community.dto.CommunityDTO;
import com.teamproject.petapet.web.community.dto.CommunityRequestDTO;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

public interface CommunityService {
    List<CommunityDTO> getCommunityList();
    void deleteCommunity(Long communityId);
    void addCommunityReport(Long communityId);
    void insertCommunity(String memberId, CommunityRequestDTO.InsertDTO insertDTO);
    Page<CommunityDTO> getCommunityList(int pageNum, int pageSize, String communityCategory);
    Long countTodayCommunity(String communityCategory);
    void viewCountPlus(Long communityId);
    CommunityDTO loadCommunityPosts(Long communityId);
    CommunityDTO loadCommunityUpdatePost(String memberId, Long communityId);
    void updateCommunity(String memberId, CommunityRequestDTO.UpdateDTO updateDTO);

    List<CommunityDTO> getNotice();
    List<CommunityDTO> getCommunityMainNotice();
    void registerNotice(CommunityRequestDTO.registerNotice registerNotice);
    CommunityDTO getOneNotice(Long noticeId);
    void updateNotice(CommunityRequestDTO.registerNotice registerNotice);
    void deleteNotice(Long noticeId);
    CommunityDTO getCommunityTitle(Long communityId);
    Page<CommunityDTO> getSearchList(String type, CommunityRequestDTO.SearchDTO searchDTO);
    Page<CommunityDTO> getMemberWritingList(String type, String memberId, int pageNum, int pageSize);
    Page<CommunityDTO> getLoginMemberWritingList(String memberId, int pageNum, int pageSize);
}
