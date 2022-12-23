package com.teamproject.petapet.domain.community;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 박채원 22.10.01 작성
 */
public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Modifying
    @Transactional
    @Query("update Community c set c.communityReport = c.communityReport + 1 where c.communityId =:communityId")
    void addCommunityReport(Long communityId);

    @Query(value = "select count(*) from Community c where date_format(modifiedDate,'%Y-%m-%d') = curdate()", nativeQuery = true)
    Long countTodayCommunity();

    @Query(value = "select count(*) from Community c where date_format(modifiedDate,'%Y-%m-%d') = curdate() and communityCategory =:communityCategory",nativeQuery = true)
    Long countTodayCommunity(String communityCategory);


    @Transactional
    @Modifying
    @Query("update Community c set c.viewCount=c.viewCount+1 where c.communityId=:communityId")
    void viewCountPlus(Long communityId);

//    @Query(value = "select c from Community c where c.communityCategory = :communityCategory "
//            ,countQuery = "select count(c) FROM Community c WHERE c.communityCategory = :communityCategory")
//    Page<Community> getCommunityList(String communityCategory, Pageable pageable);

    Page<Community> findAllByCommunityCategory(String communityCategory, Pageable pageable);
    Page<Community> findAllByMember(String memberId, Pageable pageable);

    @Query("select c from Community c where c.communityId=:communityId and c.member.memberId=:memberId")
    Optional<Community> loadupdateCommunityMemberPost (Long communityId, String memberId);




}
