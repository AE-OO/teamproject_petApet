package com.teamproject.petapet.web.report.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 박채원 22.10.16 작성
 */

@Data
@NoArgsConstructor
public class ReportCommunityDTO {
    private Long reportId;
    private String reportReason;
    private String reportReasonDetail;
    private Long communityId;
}
