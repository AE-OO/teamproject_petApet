package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.communityDto.CommunityInsertDTO;
import com.teamproject.petapet.web.community.communityDto.CommunityUpdateDTO;
import com.teamproject.petapet.web.community.service.CommentService;
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
    private final CommentService commentService;
    @GetMapping
    public String communityMain(Model model){
        model.addAttribute("todayPosts",communityService.countTodayCommunity("all"));
        model.addAttribute("community",communityService.getCommunityList(0,20,"all"));
        model.addAttribute("mainCategory","all");
        return "community/communityMain";
    }

    @PostMapping
    public String communityList(String communityCategory,int pageNum,int pageSize,Model model){
        model.addAttribute("mainCategory",communityCategory);
        model.addAttribute("todayPosts",communityService.countTodayCommunity(communityCategory));
        if(!communityService.getCommunityList(pageNum,pageSize,communityCategory).hasContent()){
            model.addAttribute("community",communityService.getCommunityList(0,pageSize,communityCategory));
        }else{
            model.addAttribute("community",communityService.getCommunityList(pageNum,pageSize,communityCategory));
        }
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

    @PostMapping("/update")
    public String updateForm(Principal principal,Long communityId,Model model){
        model.addAttribute("update",communityService.loadCommunityUpdatePost(principal.getName(),communityId));
        return "community/communityUpdate";
    }

    @PostMapping("/update/result")
    public String update(Principal principal, @Valid CommunityUpdateDTO communityUpdateDTO,
                         BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "내용을 다 채우세요");
            return "community/communityUpdate";
        }
        communityService.updateCommunity(principal.getName(), communityUpdateDTO);
        return "redirect:/community/"+communityUpdateDTO.getCommunityId();
    }

    @GetMapping("/{communityId}")
    public String posts(@PathVariable("communityId") Long communityId, Model model){
        communityService.viewCountPlus(communityId);
        model.addAttribute("posts",communityService.loadCommunityPosts(communityId));
        model.addAttribute("comment",commentService.getCommentPageList(communityId,0));
        return "community/communityPosts";
    }

    @GetMapping("/memberPost")
    public String memberPost(Model model){
        return "community/communityMemberPost";
    }
}

