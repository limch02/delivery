package com.delivery.member.application;

import com.delivery.common.security.JwtProvider;
import com.delivery.member.application.port.PasswordEncoder;
import com.delivery.member.domain.Member;
import com.delivery.member.exception.MemberErrorCode;
import com.delivery.member.exception.MemberException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.delivery.member.application.dto.LoginCommand;
import com.delivery.member.application.dto.MemberResult;
import com.delivery.member.application.dto.SignUpCommand;
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

	public void signUp(SignUpCommand command) {
		memberRepository.findByEmail(command.email())
			.ifPresent(m -> { throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL); });

		Member member = new Member(
			command.email(),
			passwordEncoder.encode(command.password()),
			command.phone_num(),
			command.role(),
			command.address()
		);

		memberRepository.save(member);
	}

	@Transactional(readOnly = true)
	public String login(LoginCommand command) {
		Member member = memberRepository.findByEmail(command.email())
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

		member.validatePassword(passwordEncoder.matches(command.password(), member.getPassword()));

		return jwtProvider.createToken(member.getMember_id(), member.getRole().name());
	}

	@Transactional(readOnly = true)
	public MemberResult getMemberByEmail(String email) {
		Member member = memberRepository.findByEmail(email)
				.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
		return MemberResult.from(member);
	}
}
