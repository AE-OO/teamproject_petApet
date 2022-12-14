package com.teamproject.petapet.domain.community;

import com.teamproject.petapet.domain.inquired.Inquired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 박채원 22.10.01 작성
 */
public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Modifying
    @Transactional
    @Query("update Community c set c.communityReport = c.communityReport + 1 where c.communityId =:communityId")
    void addCommunityReport(Long communityId);

    @Query("select c from Community c where c.communityCategory = '공지사항'")
    public List<Community> getNotice();

    @Modifying
    @Transactional
    @Query("update Community c set c.communityTitle =:title, c.communityContent =:content where c.communityId =:noticeId")
    public void updateNotice(String title, String content, Long noticeId);
}
