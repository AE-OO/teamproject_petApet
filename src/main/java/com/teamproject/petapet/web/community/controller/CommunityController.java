package com.teamproject.petapet.web.community.controller;


import com.teamproject.petapet.web.community.dto.CommunityInsertDTO;
import com.teamproject.petapet.web.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    @GetMapping
    public String communityMain(Model model){
        model.addAttribute("todayPosts",communityService.countTodayCommunity("all"));
        model.addAttribute("community",communityService.getCommunityList(0,20,"all"));
        return "community/communityMain";
    }
    @PostMapping
    public String communityList(String communityCategory,int pageNum,int pageSize,Model model){
        model.addAttribute("todayPosts",communityService.countTodayCommunity(communityCategory));
        model.addAttribute("community",communityService.getCommunityList(pageNum-1,pageSize,communityCategory));
        return "community/communityMain";
    }

    @GetMapping("/insert")
    public String communityInsertForm(){return "community/communityInsert";}

    @PostMapping("/insert")
    public String communityInsert(Principal principal, @Valid CommunityInsertDTO communityInsertDTO,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
                model.addAttribute("error", "내용을 다 채우세요");
                return "community/communityInsert";
            }
            communityService.insertCommunity(principal.getName(), communityInsertDTO);
            return "redirect:/community";
        }

    @GetMapping("/{communityId}")
    public String posts(@PathVariable("communityId") Long communityId, Model model){
        communityService.viewCountPlus(communityId);
        model.addAttribute("posts",communityService.loadCommunityPosts(communityId));
        return "community/communityPosts";
    }

}

