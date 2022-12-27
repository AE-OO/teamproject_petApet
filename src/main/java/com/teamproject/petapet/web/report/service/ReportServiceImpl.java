package com.teamproject.petapet.web.report.service;

import com.teamproject.petapet.domain.report.Report;
import com.teamproject.petapet.domain.report.ReportRepository;
import com.teamproject.petapet.web.report.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public List<ReportDTO> getReportCommunityList() {
        List<Report> reportList = reportRepository.getCommunityReportList();
        return reportList.stream().map(list -> ReportDTO.fromEntityForCommunityReport(list)).collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportMemberList() {
        List<Report> reportList = reportRepository.getMemberReportList();
        return reportList.stream().map(list -> ReportDTO.fromEntityForMemberReport(list)).collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportProductList() {
        List<Report> reportList = reportRepository.getProductReportList();
        return reportList.stream().map(list -> ReportDTO.fromEntityForProductReport(list)).collect(Collectors.toList());
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
    public ReportTargetDTO getOneReport(Long reportId, String type) {
        if(type.equals("product")){
            return entityToDTOProduct(reportRepository.getReportByReportId(reportId));
        }else if(type.equals("member")){
            return entityToDTOMember(reportRepository.getReportByReportId(reportId));
        }else{
            return entityToDTOCommunity(reportRepository.getReportByReportId(reportId));
        }
    }

    @Override
    public void refuseReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
}
