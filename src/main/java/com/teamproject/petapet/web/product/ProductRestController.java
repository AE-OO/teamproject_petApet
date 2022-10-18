package com.teamproject.petapet.web.product;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RestController
public class ProductRestController {

    @Value("${editor.img.save.url}")
    private String saveUrl;

    @PostMapping(value = "/product/image", produces = "application/json; charset=utf8")
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();


//        String fileRoot = "/Users/oh/IdeaProjects/teamproject_petApet/src/main/resources/static/img/osh/"; // 외부경로로 저장을 희망할때.

        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

        File targetFile = new File(saveUrl + savedFileName);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            org.apache.commons.io.FileUtils.copyInputStreamToFile(fileStream, targetFile);
            jsonObject.append("url", saveUrl + savedFileName);// 저장할 내부 폴더명 + 파일명
            jsonObject.append("responseCode", "success");

        } catch (IOException e) {
            org.apache.commons.io.FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.append("responseCode", "error");
            e.printStackTrace();
        }
        String jsonString = jsonObject.toString();
        return jsonString;
    }

}