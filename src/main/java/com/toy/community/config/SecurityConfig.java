package com.toy.community.config;

import com.toy.community.auth.MyAccessDeniedHandler;
import com.toy.community.auth.MyAuthenticationEntryPoint;
import com.toy.community.auth.MyLoginSuccessHandler;
import com.toy.community.auth.MyLogoutSuccessHandler;
import com.toy.community.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepository memberRepository;

    // 로그인하지 않은 유저들이 접근 가능한 URL
    private static final String[] anonymousMemberUrl = {"/members/login", "/members/join"};

    // 로그인한 유저들이 접근 가능한 URL
    private static final String[] authenticatedMemberUrl = {"/boards/*/edit", "/boards/*/delete", "/likes/**", "/members/myPage/**", "/members/edit", "/members/delete"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(anonymousMemberUrl).anonymous()
                        .requestMatchers(authenticatedMemberUrl).authenticated()
                        .requestMatchers("/boards/free/add").authenticated()
                        .requestMatchers(HttpMethod.POST, "/boards/free/add").authenticated()
                        .requestMatchers("/boards/notice/add").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/boards/notice/add").hasAuthority("ADMIN")
                        .anyRequest().permitAll()
                )
                .exceptionHandling(authenticationManager -> authenticationManager
                        .accessDeniedHandler(new MyAccessDeniedHandler(memberRepository)) //인가 실패
                        .authenticationEntryPoint(new MyAuthenticationEntryPoint()) //인증 실패
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/members/login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .failureUrl("/members/login?fail")
                        .successHandler(new MyLoginSuccessHandler(memberRepository))
                )
                .logout(logout -> logout
                        .logoutUrl("/members/logout")
                        .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(new MyLogoutSuccessHandler())
                )
                .build();
    }
}
