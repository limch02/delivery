package com.delivery.member.application.service;

import com.delivery.common.exception.CommonErrorCode;
import com.delivery.common.exception.CustomException;
import com.delivery.member.entity.Member;
import com.delivery.member.entity.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public SignUpResponse signUp(SignUpRequest request) {
		if (request == null) {
			throw new CustomException(CommonErrorCode.INVALID_INPUT);
		}

		String email = trimToNull(request.email());
		String password = trimToNull(request.password());
		String phoneNum = trimToNull(request.phone_num());
		Role role = request.role();
		String address = trimToNull(request.address());

		validateInputs(email, password, phoneNum);

		if (memberRepository.findByEmail(email).isPresent()) {
			throw new CustomException(CommonErrorCode.DUPLICATE_EMAIL);
		}

		Member member = new Member(
			email,
			password,
			phoneNum,
			role,
			address
		);

		Member saved = memberRepository.save(member);

		return toResponse(saved.getMember_id(), saved.getEmail(), saved.getPhone_num(), saved.getRole(), saved.getAddress());
	}

	private void validateInputs(String email, String password, String phoneNum) {
		if (email == null || password == null || phoneNum == null) {
			throw new CustomException(CommonErrorCode.INVALID_INPUT);
		}

		if (password.length() > 50) {
			throw new CustomException(CommonErrorCode.INVALID_INPUT);
		}

		if (!phoneNum.matches("^\\d{13}$")) {
			throw new CustomException(CommonErrorCode.INVALID_INPUT);
		}
	}

	private SignUpResponse toResponse(Long id, String email, String phoneNum, Role role,String address){
		return new SignUpResponse(id,email,phoneNum,role,address);
	}

	private String trimToNull(String value) {
		if (value == null) {
			return null;
		}
		String trimmed = value.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}
}
