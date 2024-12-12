package com.goormthon.halmang.domain.auth.service;

import com.goormthon.halmang.domain.auth.requestDto.LoginDto;
import com.goormthon.halmang.domain.auth.requestDto.UserFormDto;
import com.goormthon.halmang.domain.auth.responseDto.TokenRes;
import org.springframework.security.core.Authentication;

public interface AuthService {

    public void regist(UserFormDto userFormDto);
    public TokenRes login(LoginDto loginDto);
    public Boolean isExistUser(String userId);

}
