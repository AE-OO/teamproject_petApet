package com.teamproject.petapet.web.report.service;

import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.domain.product.Product;
import com.teamproject.petapet.domain.report.Report;
import com.teamproject.petapet.web.report.dto.*;

import java.util.List;

public interface ReportService {
    abstract List<ReportDTO> getReportCommunityList();
    List<ReportDTO> getReportMemberList();
    List<ReportDTO> getReportProductList();
    void addProductReport(ReportProductDTO reportProductDTO);
    void addMemberReport(ReportMemberDTO reportMemberDTO);
    void addCommunityReport(ReportCommunityDTO reportCommunityDTO);
    void setResponseStatusCommunity(Long reportId);
    ReportTargetDTO getOneReport(Long reportId, String type);
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

    default ReportTargetDTO entityToDTOProduct(Report report){
        ReportTargetDTO reportTargetDTO = ReportTargetDTO.builder()
                .targetLongId(report.getProduct().getProductId())
                .reportId(report.getReportId())
                .reportReason(report.getReportReason())
                .reportReasonDetail(report.getReportReasonDetail())
                .build();

        return reportTargetDTO;
    }

    default ReportTargetDTO entityToDTOMember(Report report){
        ReportTargetDTO reportTargetDTO = ReportTargetDTO.builder()
                .targetStringId(report.getMember().getMemberId())
                .reportId(report.getReportId())
                .reportReason(report.getReportReason())
                .reportReasonDetail(report.getReportReasonDetail())
                .build();

        return reportTargetDTO;
    }

    default ReportTargetDTO entityToDTOCommunity(Report report){
        ReportTargetDTO reportTargetDTO = ReportTargetDTO.builder()
                .targetLongId(report.getCommunity().getCommunityId())
                .reportId(report.getReportId())
                .reportReason(report.getReportReason())
                .reportReasonDetail(report.getReportReasonDetail())
                .build();

        return reportTargetDTO;
    }
}
