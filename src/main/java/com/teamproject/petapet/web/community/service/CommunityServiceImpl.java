package com.teamproject.petapet.web.community.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.community.CommunityRepository;
import com.teamproject.petapet.web.community.communityDto.CommunityInsertDTO;
import com.teamproject.petapet.web.community.communityDto.CommunityListDTO;
import com.teamproject.petapet.web.community.communityDto.CommunityPostsDTO;
import com.teamproject.petapet.web.community.communityDto.CommunityUpdateDTO;
import com.teamproject.petapet.web.community.dto.CommunityDTO;
import com.teamproject.petapet.web.community.dto.CommunityRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public void insertCommunity(String memberId, CommunityInsertDTO communityInsertDTO) {
        communityRepository.save(communityInsertDTO.toEntity(memberId));
    }

    @Override
    public Page<CommunityListDTO> getCommunityList(int pageNum, int pageSize, String communityCategory) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("communityId").descending());
        if (communityCategory.equals("all")) {
            return communityRepository.findAll(pageable).map(
                    community -> CommunityListDTO.fromEntity(community, dateFormat(community)));
        }
        return communityRepository.findAllByCommunityCategory(communityCategory, pageable).map(
                community -> CommunityListDTO.fromEntity(community, dateFormat(community)));
    }

    //회원 작성글 보기
    @Override
    public Page<CommunityPostsDTO> getCommunityMemberPost(int pageNum, int pageSize, String memberId) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("communityId").descending());
        return communityRepository.findAllByMember(memberId,pageable)
                .map(community -> CommunityPostsDTO.fromEntity(community));
    }

    public String dateFormat(Community community) {
        if (community.getModifiedDate().toLocalDate().isBefore(LocalDate.now())) {
            return community.getModifiedDate().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        } else {
            return community.getModifiedDate().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
    }

    @Override
    public Long countTodayCommunity(String communityCategory) {
        if (communityCategory.equals("all")) {
            return communityRepository.countTodayCommunity();
        }
        return communityRepository.countTodayCommunity(communityCategory);
    }

    @Override
    public void viewCountPlus(Long communityId) {
        communityRepository.viewCountPlus(communityId);
    }


    @Override
    public CommunityPostsDTO loadCommunityPosts(Long communityId){
        return CommunityPostsDTO.fromEntity(communityRepository.findById(communityId)
                .orElseThrow(() -> new NullPointerException(communityId + "-> 데이터베이스에서 찾을 수 없습니다.")));
    }

    @Override
    public CommunityUpdateDTO loadCommunityUpdatePost(String memberId, Long communityId) {
        return CommunityUpdateDTO.fromEntity(communityRepository.findByCommunityIdAndMemberMemberId(communityId,memberId)
                .orElseThrow(()->new NullPointerException(communityId+","+memberId+"-> 데이터베이스에서 찾을 수 없습니다.")));
    }

    @Override
    @Transactional
    public void updateCommunity(String memberId, CommunityUpdateDTO communityUpdateDTO) {
        Community community = communityRepository.findById(communityUpdateDTO.getCommunityId())
                .orElseThrow(()->new NullPointerException(communityUpdateDTO.getCommunityId()+","+memberId+"-> 데이터베이스에서 찾을 수 없습니다."));
        community.update(communityUpdateDTO.getCommunityTitle(),communityUpdateDTO.getCommunityContent(),
                communityUpdateDTO.getCommunityCategory(),communityUpdateDTO.getCommunitySubCategory());
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
