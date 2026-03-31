package com.delivery.member.application.service;

import com.delivery.common.security.JwtProvider;
import com.delivery.member.entity.Member;
import com.delivery.member.entity.Role;
import com.delivery.member.exception.MemberErrorCode;
import com.delivery.member.exception.MemberException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.member.presentation.dto.LoginRequest;
import com.delivery.member.presentation.dto.LoginResponse;
import com.delivery.member.presentation.dto.SignUpRequest;
import com.delivery.member.presentation.dto.SignUpResponse;
import com.delivery.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	public SignUpResponse signUp(SignUpRequest request) {
		memberRepository.findByEmail(request.email())
			.ifPresent(m -> { throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL); });

		Member member = new Member(
			request.email(),
			passwordEncoder.encode(request.password()),
			request.phone_num(),
			request.role(),
			request.address()
		);

		Member saved = memberRepository.save(member);

		return toResponse(saved.getMember_id(), saved.getEmail(), saved.getPhone_num(), saved.getRole(), saved.getAddress());
	}

	@Transactional(readOnly = true)
	public LoginResponse login(LoginRequest request) {
		Member member = memberRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
			throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
		}

		String accessToken = jwtProvider.createToken(member.getEmail());
		return LoginResponse.of(accessToken);
	}

	private SignUpResponse toResponse(Long id, String email, String phoneNum, Role role, String address) {
		return new SignUpResponse(id, email, phoneNum, role, address);
	}
}
