package com.toy.community.service.interfaces;

import com.toy.community.dto.MemberJoinFormDto;
import org.springframework.validation.BindingResult;

public interface MemberService {
    BindingResult joinValid(MemberJoinFormDto formDto, BindingResult bindingResult);
    void join(MemberJoinFormDto formDto);
}
