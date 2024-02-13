package com.toy.community.auth;

import com.toy.community.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class MemberDetails implements UserDetails {

    private Member member;

    public MemberDetails(Member member) {
        this.member = member;
    }

    // 계정이 가지고 있는 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> {
            return member.getMemberRole().toString();
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getLoginId();
    }

    // 계정 만료 여부
    @Override
    public boolean isAccountNonExpired() {
        return true; //만료 X
    }

    // 계정이 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true; //잠기지 않음
    }

    // 비밀번호 만료 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //만료 X
    }

    // 계정 활성화(사용 가능) 여부
    @Override
    public boolean isEnabled() {
        return true; //활성화
    }
}
