package com.teamproject.petapet.domain.report;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportRepositoryTest {

    @Autowired
    ReportRepository reportRepository;

    @Test
    public void insertMemberReportDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long memberNum = (long) (Math.random() * 30) + 1;
            Member member = Member.builder().memberId("memberId" + memberNum).build();

            Report report = Report.builder()
                    .reportReason("The reason is.....")
                    .reportCategory("member")
                    .member(member)
                    .build();

            reportRepository.save(report);
        });
    }

    @Test
    public void insertCommunityReportDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            long communityId = (long) (Math.random() * 30) + 1;
            Community community = Community.builder().communityId(communityId).build();

            Report report = Report.builder()
                    .reportReason("The reason is.....")
                    .reportCategory("community")
                    .community(community)
                    .build();

            reportRepository.save(report);
        });
    }

}