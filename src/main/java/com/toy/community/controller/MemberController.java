package com.toy.community.controller;

import com.toy.community.dto.MemberJoinFormDto;
import com.toy.community.dto.MemberLoginFormDto;
import com.toy.community.service.interfaces.BoardService;
import com.toy.community.service.interfaces.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final BoardService boardService;

    // 회원가입 페이지
    @GetMapping("/join")
    public String joinPage(Model model) {
        MemberJoinFormDto memberJoinFormDto = MemberJoinFormDto.builder().build();
        model.addAttribute("memberJoinFormDto", memberJoinFormDto);
        return "members/join";
    }

    // 회원가입
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute MemberJoinFormDto formDto, BindingResult bindingResult, Model model) {
        if (memberService.joinValid(formDto, bindingResult).hasErrors()) {
            return "members/join";
        }

        memberService.join(formDto);
        model.addAttribute("message", "회원가입에 성공했습니다!\n로그인 후 사용 가능합니다!");
        model.addAttribute("nextUrl", "/members/login");
        return "printMessage";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(Model model, HttpServletRequest request) {

        // 로그인 성공 시 이전 페이지로 redirect 되게 하기 위해 세션에 저장
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login") && !uri.contains("/join")) {
            request.getSession().setAttribute("prevPage", uri);
        }

        model.addAttribute("memberLoginFromDto", new MemberLoginFormDto());
        return "members/login";
    }
}
