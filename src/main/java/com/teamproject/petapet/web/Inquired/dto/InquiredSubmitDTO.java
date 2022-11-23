package com.teamproject.petapet.web.Inquired.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class InquiredSubmitDTO {


    @NotBlank(message = "글 제목을 입력해주세요")
    private String title;

    @NotBlank(message = "글 내용을 입력하세요")
    private String inquiredContent;

    @NotBlank(message = "이메일을 입력해주세요")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
            message = "이메일 형식이 아닙니다")
    private String email;
//
//    @NotNull
//    @NotEmpty
//    private List<MultipartFile> inquiredImg;

}
