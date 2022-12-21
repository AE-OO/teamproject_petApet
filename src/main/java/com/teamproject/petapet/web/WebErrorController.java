//package com.teamproject.petapet.web;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 2022.12.8 templates/error 경로에 에러페이지 넣으면 스프링부트가 자동 처리. 따라서, 빈 등록 안함
// */
////@Controller
//public class WebErrorController implements ErrorController {
//    private String ERROR_TEMPLATES_PATH = "/error";
//
//    @RequestMapping(value = "/error")
//    public String handleError(HttpServletRequest request) {
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        if(status != null){
//            int statusCode = Integer.valueOf(status.toString());
//            if(statusCode == HttpStatus.NOT_FOUND.value()){
//                return ERROR_TEMPLATES_PATH + "404";
//            }
//
//            if(statusCode == HttpStatus.FORBIDDEN.value()){
//                return ERROR_TEMPLATES_PATH + "500";
//            }
//
//            if(statusCode == HttpStatus.UNAUTHORIZED.value()){
//                return ERROR_TEMPLATES_PATH + "401";
//            }
//        }
//        return "error";
//    }
//}
