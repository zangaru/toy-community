package com.toy.community.auth;

import com.toy.community.domain.entity.Member;
import com.toy.community.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

@AllArgsConstructor
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    // 로그인 성공 후 호출되는 메소드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3600);//세션 유지 시간 3600초

        Member loginMember = memberRepository.findByLoginId(authentication.getName()).get();

        //이전 페이지로 리다이렉트
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter pw = response.getWriter();

        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null) {
            pw.println("<script>alert('" + loginMember.getNickname() + "님 반갑습니다!'); location.href='" + prevPage + "';</script>");
        } else {
            pw.println("<script>alert('" + loginMember.getNickname() + "님 반갑습니다!'); location.href='/';</script>");
        }
        pw.flush();//데이터 강제 출력
    }
}
