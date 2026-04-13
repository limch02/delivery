package com.delivery.common.cookie;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class CookieProvider {

	@Value("${cookie.secure}")
	private boolean cookieSecure;

	public ResponseCookie createAccessTokenCookie(String token) {
		return ResponseCookie.from("accessToken", token)
			.httpOnly(true)
			.secure(cookieSecure)
			.path("/")
			.maxAge(Duration.ofHours(1))
			.sameSite("Strict")
			.build();
	}
}
