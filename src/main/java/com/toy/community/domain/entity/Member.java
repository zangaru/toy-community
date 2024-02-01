package com.toy.community.domain.entity;

import com.toy.community.domain.enums.MemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId; //로그인할 때 사용하는 아이디
    private String password; //비밀번호
    private String nickname; //닉네임
    private LocalDateTime createdAt; //가입일
    private Integer receivedLikeCnt; //회원이 받은 좋아요 수

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole; //권한

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Board> boards; //작성 게시글

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Comment> comments; //댓글

    @OneToMany(mappedBy = "member", orphanRemoval = true)
    private List<Like> likes; //회원이 누른 좋아요

}
