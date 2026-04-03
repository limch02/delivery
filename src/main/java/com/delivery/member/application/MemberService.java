package com.delivery.member.application;

import com.delivery.common.security.JwtProvider;
import com.delivery.member.infrastructure.PasswordEncoder;
import com.delivery.member.domain.Member;
import com.delivery.member.domain.Role;
import com.delivery.member.exception.MemberErrorCode;
import com.delivery.member.exception.MemberException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.member.application.dto.LoginCommand;
import com.delivery.member.application.dto.LoginResult;
import com.delivery.member.application.dto.SignUpCommand;
import com.delivery.member.application.dto.SignUpResult;
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

	public SignUpResult signUp(SignUpCommand command) {
		memberRepository.findByEmail(command.email())
			.ifPresent(m -> { throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL); });

		Member member = new Member(
			command.email(),
			passwordEncoder.encode(command.password()),
			command.phone_num(),
			command.role(),
			command.address()
		);

		Member saved = memberRepository.save(member);

		return toResult(saved.getMember_id(), saved.getEmail(), saved.getPhone_num(), saved.getRole(), saved.getAddress());
	}

	@Transactional(readOnly = true)
	public LoginResult login(LoginCommand command) {
		Member member = memberRepository.findByEmail(command.email())
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		if (!passwordEncoder.matches(command.password(), member.getPassword())) {
			throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
		}

		String accessToken = jwtProvider.createToken(member.getEmail());
		return new LoginResult(accessToken);
	}

	private SignUpResult toResult(Long id, String email, String phoneNum, Role role, String address) {
		return new SignUpResult(id, email, phoneNum, role, address);
	}
}
