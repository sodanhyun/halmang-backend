package com.goormthon.halmang.domain.auth.controller;

import com.goormthon.halmang.domain.auth.requestDto.LoginDto;
import com.goormthon.halmang.domain.auth.requestDto.UserFormDto;
import com.goormthon.halmang.domain.auth.responseDto.TokenRes;
import com.goormthon.halmang.domain.auth.service.AuthService;
import com.goormthon.halmang.utils.CookieUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.goormthon.halmang.constant.JwtTokenConstant.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostConstruct
    public void init() {
        if(!authService.isExistUser("parent")) {
            authService.regist(UserFormDto.builder()
                    .id("parent")
                    .password("1234")
                    .name("김순자")
                    .type("parent")
                    .build());
        }
        if(!authService.isExistUser("child")) {
            authService.regist(UserFormDto.builder()
                    .id("child")
                    .password("1234")
                    .name("이지은")
                    .type("child")
                    .build());
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<?> signup(@Valid @RequestBody UserFormDto userFormDto) {
        try{
            authService.regist(userFormDto);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        try{
            TokenRes res = authService.login(loginDto);
            CookieUtil.addCookie(response,
                    ACCESS_TOKEN_COOKIE_NAME,
                    res.getAccessToken(),
                    ACCESS_TOKEN_DURATION);
            CookieUtil.addCookie(response,
                    REFRESH_TOKEN_COOKIE_NAME,
                    res.getRefreshToken(),
                    REFRESH_TOKEN_DURATION);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

}