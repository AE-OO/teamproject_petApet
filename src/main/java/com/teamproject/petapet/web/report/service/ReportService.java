package com.teamproject.petapet.web.report.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.report.Report;
import com.teamproject.petapet.web.report.dto.ReportCommunityDTO;
import com.teamproject.petapet.web.report.dto.ReportProductDTO;

import java.util.List;

public interface ReportService {
    List<Report> getReportCommunityList();
    List<Report> getReportMemberList();
    List<Report> getReportProductList();
    void addProductReport(ReportProductDTO reportProductDTO);
    void setResponseStatusCommunity(Long reportId);

    default ReportCommunityDTO entityToDTOCommunity(Report report){
        ReportCommunityDTO reportCommunityDTO = ReportCommunityDTO.builder()
                .reportReason(report.getReportReason())
                .reportReasonDetail(report.getReportReasonDetail())
                .communityId(report.getCommunity().getCommunityId())
                .build();

        return reportCommunityDTO;
    }

    default Report dtoToentityCommunity(ReportCommunityDTO reportCommunityDTO){
        Community community = Community.builder().communityId(reportCommunityDTO.getCommunityId()).build();

        Report report = Report.builder()
                .reportReason(reportCommunityDTO.getReportReason())
                .reportReasonDetail(reportCommunityDTO.getReportReasonDetail())
                .community(community)
                .build();

        return report;
    }

    default Report dtoToEntityProduct(ReportProductDTO reportProductDTO){
        Product product = Product.builder().productId(reportProductDTO.getProductId()).build();

        Report report = Report.builder()
                .reportReason(reportProductDTO.getReportReason())
                .reportReasonDetail(reportProductDTO.getReportReasonDetail())
                .product(product)
                .build();

        return report;
    }
}
