package com.toy.community.repository;

import com.toy.community.domain.entity.Board;
import com.toy.community.domain.enums.BoardCategory;
import com.toy.community.domain.enums.MemberRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByCategoryAndMemberMemberRoleNot(BoardCategory category, MemberRole memberRole, PageRequest pageRequest);

    Page<Board> findByCategoryAndTitleAndMemberMemberRoleNot(BoardCategory category, String title, MemberRole memberRole, PageRequest pageRequest);

    @Query("SELECT b FROM Board b JOIN b.member m WHERE b.category = :category AND m.nickname = :nickname AND m.memberRole != :memberRole")
    Page<Board> findByCategoryAndNicknameAndNotMemberRole(
            @Param("category") BoardCategory category,
            @Param("nickname") String nickname,
            @Param("memberRole") MemberRole memberRole,
            PageRequest pageRequest
    );

    List<Board> findByCategoryAndMemberMemberRole(BoardCategory category, MemberRole memberRole);
    //Long countAllByAdminBoards(MemberRole memberRole);
    //Long countAllByCategoryAndNotAdminBoards(BoardCategory category, MemberRole memberRole);
}
