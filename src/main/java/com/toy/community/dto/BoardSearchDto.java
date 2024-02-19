package com.toy.community.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardSearchDto {
    private String sortType;
    private String searchType;
    private String keyword;
}
