package com.toy.community.service;

import com.toy.community.domain.entity.Board;
import com.toy.community.domain.entity.Member;
import com.toy.community.domain.enums.BoardCategory;
import com.toy.community.dto.BoardAddFormDto;
import com.toy.community.dto.BoardDto;
import com.toy.community.repository.BoardRepository;
import com.toy.community.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    @Override
    public Page<Board> getBoardList(BoardCategory category) {
        return null;
    }

    @Override
    public BoardDto getBoard(Long boardId, String category) {
        return null;
    }

    @Override
    public Long addBoard(BoardAddFormDto formDto, BoardCategory category, String loginId) throws IOException {
        Member loginMember = memberRepository.findByLoginId(loginId)
                .orElse(null);

        if (loginMember == null) {
            throw new IllegalArgumentException("해당하는 멤버가 존재하지 않습니다.");
        }

        Board savedBoard = boardRepository.save(formDto.toEntity(category, loginMember));

        return savedBoard.getId();
    }
}
