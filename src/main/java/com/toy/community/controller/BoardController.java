package com.toy.community.controller;


import com.toy.community.dto.BoardAddFormDto;
import com.toy.community.domain.enums.BoardCategory;
import com.toy.community.dto.BoardDto;
import com.toy.community.dto.BoardSearchDto;
import com.toy.community.repository.MemberRepository;
import com.toy.community.service.interfaces.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
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


    /*게시글 리스트 페이지*/
    @GetMapping("/{category}")
    public String boardListPage(@PathVariable String category, Model model,
                                @RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false) String sortType,
                                @RequestParam(required = false) String searchType,
                                @RequestParam(required = false) String keyword) {

        BoardCategory boardCategory = BoardCategory.of(category);
        if(boardCategory == null) {
            model.addAttribute("message", "카테고리가 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/");
            return "printMessage";
        }

        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("id").descending());
        if (sortType != null) {
            if (sortType.equals("date")) {
                pageRequest = PageRequest.of(page - 1, 10, Sort.by("createdAt").descending());
            } else if (sortType.equals("like")) {
                pageRequest = PageRequest.of(page - 1, 10, Sort.by("likeCnt").descending());
            } else if (sortType.equals("comment")) {
                pageRequest = PageRequest.of(page - 1, 10, Sort.by("commentCnt").descending());
            }
        }

        model.addAttribute("notices", boardService.getNotice(boardCategory));
        model.addAttribute("category", category);
        model.addAttribute("boards", boardService.getBoardList(boardCategory, pageRequest, searchType, keyword));
        model.addAttribute("boardSearchDto", new BoardSearchDto(sortType, searchType, keyword));

        return "boards/list";
    }



    /*게시글 등록 페이지*/
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

    /*게시글 등록*/
    @PostMapping("/{category}/add")
    public String boardAdd(@PathVariable String category, @ModelAttribute BoardAddFormDto boardAddFormDto, Authentication auth, Model model) throws IOException {
        BoardCategory boardCategory = BoardCategory.of(category);
        if (boardCategory == null) {
            model.addAttribute("message", "카테고리가 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/");
            return "printMessage";
        }

        Long savedBoardId = boardService.addBoard(boardAddFormDto, boardCategory, auth.getName(), auth);
        model.addAttribute("message", savedBoardId + "번 글이 등록되었습니다.");
        model.addAttribute("nextUrl", "/boards/" + category + "/" + savedBoardId);
        return "printMessage";
    }

    /*게시글 상세 페이지*/
    @GetMapping("/{category}/{boardId}")
    public String boardDetailPage(@PathVariable String category, @PathVariable Long boardId, Model model, Authentication auth) {

        if (auth != null) {
            model.addAttribute("loginMemberLoginId", auth.getName());
        }

        BoardDto boardDto = boardService.getBoard(boardId, category);

        if(boardDto == null) {
            model.addAttribute("message", "해당 게시글이 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/boards/" + category);
            return "printMessage";
        }

        model.addAttribute("boardDto", boardDto);

        return "boards/detail";
    }

    @PostMapping("/{category}/{boardId}/edit")
    public String boardEdit(@PathVariable String category, @PathVariable Long boardId,
                            @ModelAttribute BoardDto dto, Model model) throws IOException {
        Long editedBoardId = boardService.editBoard(boardId, category, dto);

        if (editedBoardId == null) {
            model.addAttribute("message", "해당 게시글이 존재하지 않습니다.");
            model.addAttribute("nextUrl", "/boards/" + category);
        } else {
            model.addAttribute("message", editedBoardId + "번 글이 수정 되었습니다.");
            model.addAttribute("nextUrl", "/boards/" + category + "/" + boardId);
        }
        return "printMessage";
    }

    @GetMapping("/{category}/{boardId}/delete")
    public String boardDelete(@PathVariable String category, @PathVariable Long boardId, Model model) throws IOException {

        Long deletedBoardId = boardService.deleteBoard(boardId, category);

        model.addAttribute("message", deletedBoardId == null ? "해당 게시글이 존재하지 않습니다" : deletedBoardId + "번 글이 삭제되었습니다.");
        model.addAttribute("nextUrl", "/boards/" + category);

        return "printMessage";
    }
}
