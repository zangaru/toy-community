package com.toy.community.service.interfaces;

import com.toy.community.domain.entity.Board;
import com.toy.community.domain.enums.BoardCategory;
import com.toy.community.dto.BoardAddFormDto;
import com.toy.community.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;


public interface BoardService {
    Page<Board> getBoardList(BoardCategory category, PageRequest pageRequest, String searchType, String keyword);
    List<Board> getNotice(BoardCategory category);
    BoardDto getBoard(Long boardId, String category);
    Long addBoard(BoardAddFormDto formDto, BoardCategory category, String loginId, Authentication auth) throws IOException;
    Long editBoard(Long boardId, String category, BoardDto dto) throws IOException;
}
