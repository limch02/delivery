package com.delivery.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.common.exception.CommonErrorCode;
import com.delivery.common.exception.CustomException;
import com.delivery.common.security.JwtProvider;
import com.delivery.member.domain.Member;
import com.delivery.member.dto.LoginRequest;
import com.delivery.member.dto.LoginResponse;
import com.delivery.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(CommonErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new CustomException(CommonErrorCode.UNAUTHORIZED);
        }

        String accessToken = jwtProvider.createToken(member.getEmail());
        return LoginResponse.of(accessToken);
    }
}
