package com.goormthon.halmang.domain.auth.service.impl;

import com.goormthon.halmang.auth.jwt.JwtTokenProvider;
import com.goormthon.halmang.domain.auth.requestDto.LoginDto;
import com.goormthon.halmang.domain.auth.requestDto.UserFormDto;
import com.goormthon.halmang.domain.auth.responseDto.TokenRes;
import com.goormthon.halmang.domain.auth.service.AuthService;
import com.goormthon.halmang.domain.auth.service.RefreshTokenService;
import com.goormthon.halmang.entity.User;
import com.goormthon.halmang.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenService refreshTokenService;

    public void regist(UserFormDto userFormDto) {
        if (userRepository.findById(userFormDto.getId()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }
        User member = User.createByOwn(userFormDto, new BCryptPasswordEncoder());
        userRepository.save(member);
    }

    public TokenRes login(LoginDto loginDto) throws EntityNotFoundException {
        User user = userRepository.findById(loginDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getId(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        String accessToken = tokenProvider.createJwtToken(authentication.getName(), "access");
        String refreshToken = tokenProvider.createJwtToken(authentication.getName(), "refresh");
        refreshTokenService.updateOrSaveByUser(user, refreshToken);
        return TokenRes.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userRole(user.getUserRole())
                .userType(user.getUserType())
                .build();
    }

    @Override
    public Boolean isExistUser(String userId) {
        return userRepository.findById(userId).orElse(null) != null;
    }

    public void invalidRefreshToken(Authentication authentication) {
        refreshTokenService.invalidByUserId(authentication.getName());
    }

}
