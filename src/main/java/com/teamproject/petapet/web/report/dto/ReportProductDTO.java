package com.teamproject.petapet.web.report.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 박채원 22.10.31 작성
 */

@Data
@NoArgsConstructor
public class ReportProductDTO {
    private Long reportId;
    private String reportReason;
    private String reportReasonDetail;
    private Long productId;
}
