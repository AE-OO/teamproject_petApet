package com.teamproject.petapet.web.member.dto.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDTO {
    private String address;
    private String title;
    private String message;
}
