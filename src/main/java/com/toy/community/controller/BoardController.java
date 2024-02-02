package com.toy.community.controller;


import com.toy.community.dto.BoardAddFormDto;
import com.toy.community.domain.enums.BoardCategory;
import com.toy.community.repository.MemberRepository;
import com.toy.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;

    @GetMapping("/{category}/add")
    public String boardAddPage(@PathVariable String category, @ModelAttribute BoardAddFormDto boardAddFormDto, Model model) {
        BoardCategory boardCategory = BoardCategory.of(category);

        if (boardCategory == null) {
            model.addAttribute("message", "카테고리가 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/");
            return "printMessage";
        }

        model.addAttribute("category", category);

        return "boards/add";
    }

    @PostMapping("/{category}/add")
    public String boardAdd(@PathVariable String category, @ModelAttribute BoardAddFormDto boardAddFormDto, Model model) throws IOException {
        BoardCategory boardCategory = BoardCategory.of(category);
        if (boardCategory == null) {
            model.addAttribute("message", "카테고리가 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/");
            return "printMessage";
        }

        /*테스트용 멤버*/
        String testId = "testUser";

        Long savedBoardId = boardService.addBoard(boardAddFormDto, boardCategory, testId);
        model.addAttribute("message", savedBoardId + "번 글이 등록되었습니다.");
        model.addAttribute("nextUrl", "/boards/" + category + "/" + savedBoardId);
        return "printMessage";
    }

}
