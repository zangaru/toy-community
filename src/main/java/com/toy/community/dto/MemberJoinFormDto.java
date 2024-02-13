package com.toy.community.dto;

import com.toy.community.domain.entity.Member;
import com.toy.community.domain.enums.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MemberJoinFormDto {//회원가입시 사용하는 DTO

    private String loginId;
    private String password;
    private String passwordCheck;
    private String nickname;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .loginId(loginId)
                .password(encodedPassword)
                .nickname(nickname)
                .memberRole(MemberRole.USER)
                .createdAt(LocalDateTime.now())
                .receivedLikeCnt(0)
                .build();
    }
}
