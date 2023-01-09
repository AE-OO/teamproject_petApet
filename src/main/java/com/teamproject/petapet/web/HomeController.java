
package com.teamproject.petapet.web;


import com.teamproject.petapet.domain.buy.BuyRepository;
import com.teamproject.petapet.web.buy.service.BuyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";}


}