package com.delivery.member.domain;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id", nullable = false, updatable = false)
	private Long member_id;

	@Column(name = "email", nullable = false, length = 100, unique = true)
	private String email;

	@Column(name = "password", nullable = false, length = 50)
	private String password;

	@Column(name = "phone_num", nullable = false, length = 13)
	private String phone_num;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@Column(name = "address")
	private String address;

	public Member(String email, String password, String phoneNum, Role role, String address) {
		this.email = email;
		this.password = password;
		this.phone_num = phoneNum;
		this.role = role;
		this.address = address;
	}

	public void validatePassword(boolean isValid) {
		if (!isValid) {
			throw new com.delivery.member.exception.MemberException(
				com.delivery.member.exception.MemberErrorCode.INVALID_PASSWORD
			);
		}
	}
}
