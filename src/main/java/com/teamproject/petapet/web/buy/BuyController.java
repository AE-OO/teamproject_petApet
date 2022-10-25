package com.teamproject.petapet.web.buy;


import com.teamproject.petapet.web.buy.dto.BuyDTO;
import com.teamproject.petapet.web.buy.service.Buyservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

//    @PostMapping("/{memberId}")
//    public String productToBuy(@PathVariable String memberId,
//                               @ModelAttribute() BuyDTO buyDTO){
//        return "mypage/buy";
//    }

//    @PostMapping("/{memberId}")
//    public String cartToBuy(@PathVariable String memberId,
//                            @ModelAttribute() BuyDTO buyDTO){
//        return "mypage/buy";}
}
