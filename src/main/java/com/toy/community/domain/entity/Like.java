package com.toy.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "\"like\"")
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //좋아요 누른 회원

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board; //좋아요 받은 게시글
}
