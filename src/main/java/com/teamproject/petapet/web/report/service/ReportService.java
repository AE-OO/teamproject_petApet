package com.teamproject.petapet.web.report.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.report.Report;
import com.teamproject.petapet.web.report.dto.ReportCommunityDTO;

import java.util.List;

public interface ReportService {
    List<Report> getReportCommunityList();
    List<Report> getReportMemberList();
    void setResponseStatusCommunity(Long reportId);

    default ReportCommunityDTO entityToDTOCommunity(Report report){
        ReportCommunityDTO reportCommunityDTO = ReportCommunityDTO.builder()
                .reportReason(report.getReportReason())
                .communityId(report.getCommunity().getCommunityId())
                .build();

        return reportCommunityDTO;
    }

    default Report dtoToentityCommunity(ReportCommunityDTO reportCommunityDTO){
        Community community = Community.builder().communityId(reportCommunityDTO.getCommunityId()).build();

        Report report = Report.builder()
                .reportReason(reportCommunityDTO.getReportReason())
                .community(community)
                .build();

        return report;
    }
}
