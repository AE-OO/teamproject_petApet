package com.teamproject.petapet.web.report.service;

import com.teamproject.petapet.domain.report.Report;
import com.teamproject.petapet.domain.report.ReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReportServicImpl implements ReportService{

    private final ReportRepository reportRepository;

    @Override
    public List<Report> getReportCommunityList() {
        return reportRepository.getCommunityReportList();
    }

    @Override
    public List<Report> getReportMemberList() {
        return reportRepository.getMemberReportList();
    }

    @Override
    public void setResponseStatusCommunity(Long reportId) {
        reportRepository.setResponseStatusTrue(reportId);
    }
}
