package com.delivery.member.infrastructure;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.delivery.member.application.port.PasswordEncoder;

@Component
public class BcryptPasswordEncoder implements PasswordEncoder {

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	@Override
	public String encode(String rawPassword) {
		return bcrypt.encode(rawPassword);
	}

	@Override
	public boolean matches(String rawPassword, String encodedPassword) {
		return bcrypt.matches(rawPassword,encodedPassword);
	}
}
