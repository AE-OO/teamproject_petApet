package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.web.community.dto.CommunityRequestDTO;
import com.teamproject.petapet.web.community.service.CommunityService;
import com.teamproject.petapet.web.product.fileupload.FileService;
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

    private final FileService fileService;
    @GetMapping
    public String communityMain(){ return "community/communityMain";}

    @GetMapping("/search")
    public String search(@RequestParam String type,@RequestParam String searchContent){
        return "community/communitySearch";
    }

    @GetMapping("/insert")
    public String communityInsertForm(){return "community/communityInsert";}

    @PostMapping("/insert")
    public String communityInsert(Principal principal, @Valid CommunityRequestDTO.InsertDTO insertDTO,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
                model.addAttribute("error", "내용을 다 채우세요");
                return "community/communityInsert";
            }
            communityService.insertCommunity(principal.getName(), insertDTO);
            return "redirect:/community";
        }

    @PostMapping("/update")
    public String updateForm(Principal principal,Long communityId,Model model){
        model.addAttribute("update",communityService.loadCommunityUpdatePost(principal.getName(),communityId));
        return "community/communityUpdate";
    }

    @PostMapping("/update/result")
    public String update(Principal principal, @Valid CommunityRequestDTO.UpdateDTO updateDTO,
                         BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "내용을 다 채우세요");
            return "community/communityUpdate";
        }
        if(updateDTO.getDeleteImg() != null){
            updateDTO.getDeleteImg().forEach(img -> fileService.deleteFile(img));
        }
        communityService.updateCommunity(principal.getName(), updateDTO);
        return "redirect:/community/"+updateDTO.getCommunityId();
    }

    @GetMapping("/{communityId}")
    public String posts(@PathVariable("communityId") Long communityId,Model model){
        communityService.viewCountPlus(communityId);
        model.addAttribute("posts",communityService.loadCommunityPosts(communityId));
        return "community/communityPosts";
    }
    @GetMapping("/memberWriting/{memberId}")
    public String memberPost(@PathVariable String memberId){
        return "community/communityMemberWriting";
    }

    @GetMapping("/memberProfile/{memberId}")
    public String memberProfile(@PathVariable String memberId){return "community/communityMemberProfile";}
}

