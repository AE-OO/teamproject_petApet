package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.community.CommunityRepository;
import com.teamproject.petapet.web.community.dto.CommunityInsertDTO;
import com.teamproject.petapet.web.community.dto.CommunityListDTO;
import com.teamproject.petapet.web.community.dto.CommunityPostsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 박채원 22.10.09 작성
 */

@Service
@Log4j2
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepository;

    @Override
    public List<Community> getProductList() {
        return communityRepository.findAll(Sort.by(Sort.Direction.DESC, "communityReport"));
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
    public void insertCommunity(String memberId, CommunityInsertDTO communityInsertDTO) {
        communityRepository.save(communityInsertDTO.toEntity(memberId));
    }

//    @Override
//    public Page<CommunityListDTO> getCommunityList(int pageNum, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("communityId").descending());
//        return communityRepository.findAll(pageable).map(
//                community -> CommunityListDTO.fromEntity(community, dateFormat(community)));
//    }

    @Override
    public Page<CommunityListDTO> getCommunityList(int pageNum, int pageSize, String communityCategory) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("communityId").descending());
        if(communityCategory.equals("all")){
            return communityRepository.findAll(pageable).map(
                community -> CommunityListDTO.fromEntity(community, dateFormat(community)));
        }
        return communityRepository.getCommunityList(communityCategory, pageable).map(
                community -> CommunityListDTO.fromEntity(community, dateFormat(community)));
    }

    public String dateFormat(Community community) {
        if (community.getCommunityDate().toLocalDate().isBefore(LocalDate.now())) {
            return community.getCommunityDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        } else {
            return community.getCommunityDate().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

//    @Override
//    public Long countTodayCommunity() {
//        return communityRepository.countTodayCommunity();
//    }

    @Override
    public Long countTodayCommunity(String communityCategory) {
        if(communityCategory.equals("all")){
            return communityRepository.countTodayCommunity();
        }
        return communityRepository.countTodayCommunity(communityCategory);
    }

    @Override
    public void viewCountPlus(Long communityId) {
        communityRepository.viewCountPlus(communityId);
    }

    @Override
    public CommunityPostsDTO loadCommunityPosts(Long communityId) {
        return CommunityPostsDTO.fromEntity(communityRepository.findById(communityId)
                .orElseThrow(()-> new NullPointerException(communityId +"-> 데이터베이스에서 찾을 수 없습니다.")));
    }
}
