package com.teamproject.petapet.web.report.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 박채원 22.10.31 작성
 */

@Data
@Builder
public class ReportProductDTO {
    private Long reportId;
    private String reportReason;
    private String reportReasonDetail;
    private Long productId;
}
