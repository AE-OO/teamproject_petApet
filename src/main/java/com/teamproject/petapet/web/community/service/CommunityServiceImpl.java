package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.community.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 박채원 22.10.09 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{

    private final CommunityRepository communityRepository;

    @Override
    public List<Community> getProductList() {
        return communityRepository.findAll(Sort.by(Sort.Direction.DESC,"communityReport"));
    }

    @Override
    public void deleteCommunity(Long communityId) {
        communityRepository.deleteById(communityId);    }

    @Override
    public void addCommunityReport(Long communityId) {
        communityRepository.addCommunityReport(communityId);
    }
}
