package com.toy.community.service;

import com.toy.community.domain.entity.Board;
import com.toy.community.domain.enums.BoardCategory;
import com.toy.community.dto.BoardAddFormDto;
import com.toy.community.dto.BoardDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.io.IOException;


public interface BoardService {
    //Page<Board> getBoardList(BoardCategory category, PageRequest pageRequest, String searchType, String keyword);
    Page<Board> getBoardList(BoardCategory category);
    BoardDto getBoard(Long boardId, String category);
    Long addBoard(BoardAddFormDto formDto, BoardCategory category, String loginId) throws IOException;
}
