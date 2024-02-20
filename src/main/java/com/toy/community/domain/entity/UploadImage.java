package com.toy.community.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String originalFilename; //원본 파일명
    private String savedFilename; //서버에 저장된 파일명

    @OneToOne(mappedBy = "uploadImage", fetch = FetchType.LAZY)
    private Board board;
}
