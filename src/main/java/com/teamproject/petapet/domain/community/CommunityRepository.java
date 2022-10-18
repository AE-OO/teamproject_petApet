package com.teamproject.petapet.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * 박채원 22.10.01 작성
 */
public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Modifying
    @Transactional
    @Query("update Community c set c.communityReport = c.communityReport + 1 where c.communityId =:communityId")
    public void addCommunityReport(Long communityId);
}
