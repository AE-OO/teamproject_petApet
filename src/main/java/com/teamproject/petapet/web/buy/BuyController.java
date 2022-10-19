package com.teamproject.petapet.web.buy;


import com.teamproject.petapet.web.buy.service.Buyservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/buy")
@RequiredArgsConstructor
public class BuyController {

    public Buyservice buyservice;

    @GetMapping()
    public String BuyList(){
        return "mypage/buy";
    }
}
