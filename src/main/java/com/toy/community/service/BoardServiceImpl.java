package com.toy.community.service;

import com.toy.community.domain.entity.Board;
import com.toy.community.domain.entity.Member;
import com.toy.community.domain.entity.UploadImage;
import com.toy.community.domain.enums.BoardCategory;
import com.toy.community.domain.enums.MemberRole;
import com.toy.community.dto.BoardAddFormDto;
import com.toy.community.dto.BoardDto;
import com.toy.community.repository.BoardRepository;
import com.toy.community.repository.MemberRepository;
import com.toy.community.service.interfaces.BoardService;
import com.toy.community.service.interfaces.UploadImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final UploadImageService uploadImageService;

    @Override
    public Page<Board> getBoardList(BoardCategory category, PageRequest pageRequest, String searchType, String keyword) {
        if(searchType != null && keyword != null) {
            if (searchType.equals("title")) {
                return boardRepository.findByCategoryAndTitleAndMemberMemberRoleNot(category, keyword, MemberRole.ADMIN, pageRequest);
            } else {
                return boardRepository.findByCategoryAndNicknameAndNotMemberRole(category, keyword, MemberRole.ADMIN, pageRequest);
            }
        }
        return boardRepository.findByCategoryAndMemberMemberRoleNot(category, MemberRole.ADMIN, pageRequest);
    }

    public List<Board> getNotice(BoardCategory category) {
        return boardRepository.findByCategoryAndMemberMemberRole(category, MemberRole.ADMIN);
    }

    @Override
    public BoardDto getBoard(Long boardId, String category) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);

        // boardId에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면
        if (optionalBoard.isEmpty() || !optionalBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        return BoardDto.of(optionalBoard.get()); //board 객체를 dto로 변환
    }

    @Transactional
    @Override
    public Long addBoard(BoardAddFormDto formDto, BoardCategory category, String loginId, Authentication auth) throws IOException {
        Member loginMember = memberRepository.findByLoginId(loginId)
                .orElse(null);


        UploadImage uploadImage = uploadImageService.saveImage(formDto.getUploadImage());

        Board savedBoard = boardRepository.save(formDto.toEntity(category, loginMember, uploadImage));

        if(uploadImage != null) {
            savedBoard.setUploadImage(uploadImage);
        }

        return savedBoard.getId();
    }

    @Transactional
    @Override
    public Long editBoard(Long boardId, String category, BoardDto dto) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        //id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면
        if(optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        Board board = optBoard.get();
        //게시글에 이미지가 있었으면 삭제
        if (board.getUploadImage() != null) {
            board.setUploadImage(null);
        }

        UploadImage uploadImage = uploadImageService.saveImage(dto.getNewImage());
        if(uploadImage != null) {
            board.setUploadImage(uploadImage);
        }
        board.update(dto);

        return board.getId();
    }

    @Transactional
    @Override
    public Long deleteBoard(Long boardId, String category) throws IOException {
        Optional<Board> optBoard = boardRepository.findById(boardId);

        //id에 해당하는 게시글이 없거나 카테고리가 일치하지 않으면 삭제 실패
        if(optBoard.isEmpty() || !optBoard.get().getCategory().toString().equalsIgnoreCase(category)) {
            return null;
        }

        Board board = optBoard.get();
        UploadImage uploadImage = board.getUploadImage();

        if (uploadImage != null) {
            uploadImageService.deleteImage(uploadImage); // 이미지 삭제
            board.setUploadImage(uploadImage);
        }

        boardRepository.deleteById(boardId);
        return boardId;
    }
}
