package com.teamproject.petapet.web.report.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 박채원 22.10.16 작성
 */

@Data
@Builder
public class ReportCommunityDTO {
    private Long reportId;
    private String reportReason;
    private Long communityId;
}
