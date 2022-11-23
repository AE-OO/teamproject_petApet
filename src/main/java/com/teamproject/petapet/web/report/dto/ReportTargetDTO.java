package com.teamproject.petapet.web.report.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 22.11.23 박채원 작성
 */

@Data
@Builder
public class ReportTargetDTO {
    private Long reportId;
    private String reportReason;
    private String reportReasonDetail;
    private Long targetLongId;
    private String targetStringId;
}
