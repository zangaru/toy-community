package com.toy.community.domain.entity;

import com.toy.community.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; //내용

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자

    @ManyToOne(fetch = FetchType.LAZY) //여러개의 댓글이 하나의 게시판에 속함
    private Board board; //댓글 달린 게시판
}
