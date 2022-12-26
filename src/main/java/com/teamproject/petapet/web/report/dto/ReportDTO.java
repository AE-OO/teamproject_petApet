package com.teamproject.petapet.web.report.dto;

import com.teamproject.petapet.domain.report.Report;
import lombok.*;

/**
 * 박채원 22.12.21 작성
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportDTO {
    private Long reportId;
    private Long communityId;
    private Long productId;
    private String memberId;
    private String reportReason;

    public static ReportDTO fromEntityForCommunityReport(Report report){
        return ReportDTO.builder()
                .reportId(report.getReportId())
                .communityId(report.getCommunity().getCommunityId())
                .reportReason(report.getReportReason())
                .build();
    }

    public static ReportDTO fromEntityForProductReport(Report report){
        return ReportDTO.builder()
                .reportId(report.getReportId())
                .productId(report.getProduct().getProductId())
                .reportReason(report.getReportReason())
                .build();
    }

    public static ReportDTO fromEntityForMemberReport(Report report){
        return ReportDTO.builder()
                .reportId(report.getReportId())
                .memberId(report.getMember().getMemberId())
                .reportReason(report.getReportReason())
                .build();
    }
}
