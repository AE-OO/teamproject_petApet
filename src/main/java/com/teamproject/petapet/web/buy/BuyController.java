package com.teamproject.petapet.web.buy;


import com.teamproject.petapet.domain.buy.Buy;
import com.teamproject.petapet.domain.cart.Cart;
import com.teamproject.petapet.domain.member.Member;
import com.teamproject.petapet.web.buy.dto.BuyDTO;
import com.teamproject.petapet.web.buy.dto.BuyVO;
import com.teamproject.petapet.web.buy.service.Buyservice;
import com.teamproject.petapet.web.cart.dto.CartVO;
import com.teamproject.petapet.web.cart.service.CartService;
import com.teamproject.petapet.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/buy")
@RequiredArgsConstructor
public class BuyController {

    private final Buyservice buyService;
    private final MemberService memberService;

    private final CartService cartService;


    @GetMapping()
    public String myBuy(Principal principal,
                         HttpServletRequest request,
                         HttpSession httpSession,
                         Model model){

        if(Principal.class.isInstance(principal)) {
            String loginMember = checkMember(principal, request, httpSession);

            List<Buy> buyList = buyService.findAll(loginMember);
            model.addAttribute("buyList", buyList);
            return "mypage/buy";
        }

        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = { RequestMethod.POST }, produces = "application/json")
    public void productToBuy(@RequestBody BuyVO vo, Principal principal, HttpServletRequest request, HttpSession httpSession){

        String loginMember = checkMember(principal, request, httpSession);
        Date date = new Date();
        Long product = vo.getProduct();
        Long quantity = vo.getQuantity();
        String memberAddress = memberService.findAddr(loginMember).getMemberAddress();


        Buy buy = new Buy();

        buyService.addCartToBuy(buy);

    }

    @ResponseBody
    @RequestMapping(value = "/add", method = { RequestMethod.POST }, produces = "application/json")
    public void cartToBuy(@RequestBody BuyVO vo, Principal principal, HttpServletRequest request, HttpSession httpSession) {

        String loginMember = checkMember(principal, request, httpSession);
        Date date = new Date();
        Long product = vo.getProduct();
        Long quantity = vo.getQuantity();
        String memberAddress = memberService.findAddr(loginMember).getMemberAddress();


        Buy buy = new Buy();

        buyService.addCartToBuy(buy);

    }


    private String checkMember(Principal principal, HttpServletRequest request, HttpSession httpSession) {
        httpSession.setAttribute("loginMember", memberService.findOne(principal.getName()));
        HttpSession session = request.getSession(false);
        Member loginMemberSession = (Member) session.getAttribute("loginMember");
        String loginMember = loginMemberSession.getMemberId();
        return loginMember;
    }
}
