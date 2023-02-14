package com.teamproject.petapet.web.report.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportMemberDTO {
    private Long reportId;
    private String reportReason;
    private String reportReasonDetail;
    private String memberId;
}
