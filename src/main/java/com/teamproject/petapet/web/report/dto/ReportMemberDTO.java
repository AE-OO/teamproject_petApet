package com.teamproject.petapet.web.report.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportMemberDTO {
    private Long reportId;
    private String reportReason;
    private String memberId;
}
