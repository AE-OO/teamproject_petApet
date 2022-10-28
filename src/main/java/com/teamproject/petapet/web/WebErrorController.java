package com.teamproject.petapet.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * 장사론 22.10.27 작성
 */
@Controller
public class WebErrorController implements ErrorController {

    private String ERROR_TEMPLATES_PATH = "/errors/";

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if(status != null){
            int statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()){
                return ERROR_TEMPLATES_PATH + "404";
            }

            if(statusCode == HttpStatus.FORBIDDEN.value()){
                return ERROR_TEMPLATES_PATH + "500";
            }
//            HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
//            model.addAttribute("code", status.toString());
//            model.addAttribute("msg", httpStatus.getReasonPhrase());
//            model.addAttribute("timestamp", new Date());
//            return "errorPage";
        }
        return "error";
    }


}
