package com.teamproject.petapet.web.report.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.report.Report;
import com.teamproject.petapet.web.report.dto.ReportCommunityDTO;
import com.teamproject.petapet.web.report.dto.ReportMemberDTO;
import com.teamproject.petapet.web.report.dto.ReportProductDTO;

import java.util.List;

public interface ReportService {
    List<Report> getReportCommunityList();
    List<Report> getReportMemberList();
    List<Report> getReportProductList();
    void addProductReport(ReportProductDTO reportProductDTO);
    void addMemberReport(ReportMemberDTO reportMemberDTO);
    void addCommunityReport(ReportCommunityDTO reportCommunityDTO);
    void setResponseStatusCommunity(Long reportId);
    ReportProductDTO getOneReportProduct(Long reportId);
    void refuseReport(Long reportId);
    default Report dtoToEntityProduct(ReportProductDTO reportProductDTO){
        Product product = Product.builder().productId(reportProductDTO.getProductId()).build();

        Report report = Report.builder()
                .reportReason(reportProductDTO.getReportReason())
                .reportReasonDetail(reportProductDTO.getReportReasonDetail())
                .product(product)
                .build();

        return report;
    }

    default ReportProductDTO entityToDTOProduct(Report report){
        ReportProductDTO reportProductDTO = ReportProductDTO.builder()
                .productId(report.getReportId())
                .reportId(report.getReportId())
                .reportReason(report.getReportReason())
                .reportReasonDetail(report.getReportReasonDetail())
                .build();

        return reportProductDTO;
    }

    default Report dtoToEntityCommunity(ReportCommunityDTO reportCommunityDTO){
        Community community = Community.builder().communityId(reportCommunityDTO.getCommunityId()).build();

        Report report = Report.builder()
                .community(community)
                .reportReason(reportCommunityDTO.getReportReason())
                .reportReasonDetail(reportCommunityDTO.getReportReasonDetail())
                .build();

        return report;
    }

    default Report dtoToEntityMember(ReportMemberDTO reportMemberDTO){
        Member member = Member.builder().memberId(reportMemberDTO.getMemberId()).build();

        Report report = Report.builder()
                .member(member)
                .reportReason(reportMemberDTO.getReportReason())
                .reportReasonDetail(reportMemberDTO.getReportReasonDetail())
                .build();

        return report;
    }
}
