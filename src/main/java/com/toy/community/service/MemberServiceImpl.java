package com.toy.community.service;

import com.toy.community.dto.MemberJoinFormDto;
import com.toy.community.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public BindingResult joinValid(MemberJoinFormDto formDto, BindingResult bindingResult) {

        if (formDto.getLoginId().isEmpty()) {
            bindingResult.addError(new FieldError("formDto", "loginId", "아이디를 입력해 주세요."));
        } else if (formDto.getLoginId().length() > 10) {
            bindingResult.addError(new FieldError("formDto", "loginId", "아이디를 10자 이내로 입력해 주세요."));
        } else if (memberRepository.existsByLoginId(formDto.getLoginId())) {
            bindingResult.addError(new FieldError("formDto", "loginId","이미 존재하는 아이디 입니다."));
        }

        if (formDto.getPassword().isEmpty()) {
            bindingResult.addError(new FieldError("formDto", "password", "비밀번호를 입력해 주세요."));
        }

        if (!formDto.getPassword().equals(formDto.getPasswordCheck())) {
            bindingResult.addError(new FieldError("formDto", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if(formDto.getNickname().isEmpty()) {
            bindingResult.addError(new FieldError("formDto", "nickname", "닉네임을 입력해 주세요."));
        } else if (formDto.getNickname().length() > 10) {
            bindingResult.addError(new FieldError("formDto", "nickname", "닉네임을 10자 이내로 입력해 주세요."));
        } else if (memberRepository.existsByNickname(formDto.getNickname())) {
            bindingResult.addError(new FieldError("formDto", "nickname", "이미 존재하는 닉네임 입니다."));
        }

        return bindingResult;
    }

    @Override
    public void join(MemberJoinFormDto formDto) {
        memberRepository.save(formDto.toEntity(encoder.encode(formDto.getPassword())));
    }
}
