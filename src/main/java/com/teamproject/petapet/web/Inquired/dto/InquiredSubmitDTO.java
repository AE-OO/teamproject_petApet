package com.teamproject.petapet.web.Inquired.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class InquiredSubmitDTO {

    @NotBlank(message = "글 제목을 입력해주세요")
    private String title;

    @NotBlank(message = "글 내용을 입력하세요")
    private String inquiredContent;

    private String companyId;
    
}
