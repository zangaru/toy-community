package com.toy.community.dto;

import com.toy.community.domain.entity.Board;
import com.toy.community.domain.entity.Member;
import com.toy.community.domain.enums.BoardCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class BoardAddFormDto {

    private String title;
    private String content;
    private MultipartFile uploadImage;

    public Board toEntity(BoardCategory category, Member member) {
        return Board.builder()
                .member(member)
                .category(category)
                .title(title)
                .content(content)
                .likeCnt(0)
                .commentCnt(0)
                .build();
    }

}
