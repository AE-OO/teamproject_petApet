package com.teamproject.petapet;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 텍스트 에디터에 이미지를 업로드 했을 때 그 이미지가 바로 보일 수 있도록 하기 위해 생성. By.oh
 * 2022.11.15 수정예정 By.oh
 * 2022.11.22 수정. 모든 url요청에 대한 처리를 ResponseEntity로 return. 추후 현재 클래스 삭제예정 By.oh
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    private String connectPath = "/Users/oh/Desktop/test/file/**";
//    private String connectPath2 = "/Users/seonghyeon/Desktop/test/**";
//    private String resourcePath = "file:///Users/oh/Desktop/test/file/";
//    private String resourcePath2 = "file:////Users/seonghyeon/Desktop/test/";
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler(connectPath)
//                .addResourceLocations(resourcePath);
//        registry.addResourceHandler(connectPath2)
//                .addResourceLocations(resourcePath2);
//    }
}