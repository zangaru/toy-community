package com.toy.community.domain.entity;

import com.toy.community.domain.BaseEntity;
import com.toy.community.domain.enums.BoardCategory;
import com.toy.community.dto.BoardDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //제목
    private String content; //내용

    @Enumerated(EnumType.STRING)
    private BoardCategory category; //카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; //작성자

    @OneToMany(mappedBy = "board", orphanRemoval = true) //게시판에서 삭제된 댓글을 자동으로 제거
    private List<Comment> comments; //댓글
    private Integer commentCnt; //댓글 수

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Like> likes;
    private Integer likeCnt; //좋아요 수

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    private UploadImage uploadImage;

    //게시글 수정 메서드
    public void update(BoardDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }
}
