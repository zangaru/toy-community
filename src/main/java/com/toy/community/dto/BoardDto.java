package com.toy.community.dto;

import com.toy.community.domain.entity.Board;
import com.toy.community.domain.entity.UploadImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BoardDto {

    private Long id;
    private String memberLoginId;
    private String memberNickname;
    private String title;
    private String content;
    private Integer likeCnt;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private MultipartFile newImage;
    private UploadImage uploadImage;

    //엔터티를 dto로 변환 -> 엔터티의 내부 구조를 외부로 노출하지 않으면서 필요한 정보만 전달 가능
    public static BoardDto of(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .memberLoginId(board.getMember().getLoginId())
                .memberNickname(board.getMember().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .likeCnt(board.getLikes().size())
                .createdAt(board.getCreatedAt())
                .lastModifiedAt(board.getLastModifiedAt())
                .uploadImage(board.getUploadImage())
                .build();
    }
}
