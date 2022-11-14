package com.teamproject.petapet.web.report.service;

import com.teamproject.petapet.domain.report.Report;
import com.teamproject.petapet.domain.report.ReportRepository;
import com.teamproject.petapet.web.report.dto.ReportCommunityDTO;
import com.teamproject.petapet.web.report.dto.ReportMemberDTO;
import com.teamproject.petapet.web.report.dto.ReportProductDTO;
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
    public List<Report> getReportProductList() {
        return reportRepository.getProductReportList();
    }

    @Override
    public void addProductReport(ReportProductDTO reportProductDTO) {
        reportRepository.save(dtoToEntityProduct(reportProductDTO));
    }

    @Override
    public void addMemberReport(ReportMemberDTO reportMemberDTO) {
        reportRepository.save(dtoToEntityMember(reportMemberDTO));
    }

    @Override
    public void addCommunityReport(ReportCommunityDTO reportCommunityDTO) {
        reportRepository.save(dtoToEntityCommunity(reportCommunityDTO));
    }

    @Override
    public void setResponseStatusCommunity(Long reportId) {
        reportRepository.setResponseStatusTrue(reportId);
    }

    @Override
    public ReportProductDTO getOneReportProduct(Long reportId, String type) {
        if(type.equals("product")){
            return entityToDTOProduct(reportRepository.getReportByReportId(reportId));
        }
        return null;
    }

    @Override
    public void refuseReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
