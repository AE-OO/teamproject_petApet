package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.community.CommunityRepository;
import com.teamproject.petapet.domain.inquired.Inquired;
import com.teamproject.petapet.web.community.dto.CommunityDTO;
import com.teamproject.petapet.web.community.dto.CommunityRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 박채원 22.10.09 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    @Override
    public List<CommunityDTO> getCommunityList() {
        List<Community> communityList = communityRepository.findAll(Sort.by(Sort.Direction.DESC, "communityReport"));
        return communityList.stream().map(list -> CommunityDTO.fromEntityForCommunityListOfAdminPage(list)).collect(Collectors.toList());
    }

    @Override
    public void deleteCommunity(Long communityId) {
        communityRepository.deleteById(communityId);
    }

    @Override
    public void addCommunityReport(Long communityId) {
        communityRepository.addCommunityReport(communityId);
    }

    @Override
    public List<CommunityDTO> getNotice() {
        List<Community> communityList = communityRepository.getNotice();
        return communityList.stream().map(list -> CommunityDTO.fromEntityForNotice(list)).collect(Collectors.toList());
    }

    @Override
    public void registerNotice(CommunityRequestDTO.registerNotice registerNotice) {
        log.info("========== 공지사항 등록 ==========");
        Community community = registerNotice.toEntity();
        communityRepository.save(community);
    }

    @Override
    public CommunityDTO getOneNotice(Long noticeId) {
        Community community = communityRepository.getOne(noticeId);
        CommunityDTO communityDTO = CommunityDTO.fromEntityForNotice(community);
        return communityDTO;
    }

    @Override
    public void updateNotice(CommunityRequestDTO.registerNotice registerNotice) {
        log.info("========== 공지사항 수정 ==========");
        Community community = registerNotice.toEntity();
        communityRepository.updateNotice(community.getCommunityTitle(), community.getCommunityContent(), community.getCommunityId());
    }

    @Override
    public void deleteNotice(Long noticeId) {
        log.info("========== 공지사항 삭제 ==========");
        communityRepository.deleteById(noticeId);
    }

}
