package com.teamproject.petapet.web.community.controller;

import com.teamproject.petapet.domain.community.Comment;
import com.teamproject.petapet.domain.community.CommentRepository;
import com.teamproject.petapet.domain.community.Community;
import com.teamproject.petapet.domain.community.CommunityRepository;
import com.teamproject.petapet.domain.community.dto.CommentResponseDto;
import com.teamproject.petapet.domain.community.dto.CommunityResponseDto;
import com.teamproject.petapet.web.community.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "1") int page, Model model) {
        Page<Community> list = communityService.list(page);
        List<CommunityResponseDto> listDto = list.stream()
                .map(CommunityResponseDto::new)
                .collect(Collectors.toList());

        model.addAttribute("list", list);
        model.addAttribute("listDto", listDto);
        model.addAttribute("total", communityService.total());

        model.addAttribute("maxPage", 5);

        return "community/list";
    }

    @GetMapping("/view")
    public String view(@RequestParam Long cId, @RequestParam(required = false, defaultValue = "1") int page, Model model) {
        model.addAttribute("cId", cId);
        model.addAttribute("view", communityService.view(cId));

        List<Comment> commentList = communityService.viewComment(cId);
        List<CommentResponseDto> commentListDto = commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        model.addAttribute("commentListDto", commentListDto);
        model.addAttribute("totalComment", communityService.totalComment(cId));

        return "community/view";
    }
}
