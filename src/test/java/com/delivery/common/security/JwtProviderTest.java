package com.delivery.common.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtProviderTest {

    private static final String SECRET = "test-secret-key-for-jwt-provider-minimum-256-bits!!";
    private static final long EXPIRATION = 3600000L;

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(SECRET, EXPIRATION);
    }

    @Test
    void 유효한_토큰_검증_성공() {
        String token = jwtProvider.createToken(1L, "CUSTOMER");
        assertThat(jwtProvider.validateToken(token)).isTrue();
    }

    @Test
    void 잘못된_토큰_검증_실패() {
        assertThat(jwtProvider.validateToken("invalid.token.value")).isFalse();
    }

    @Test
    void 만료된_토큰_검증_실패() {
        JwtProvider expiredProvider = new JwtProvider(SECRET, -1L);
        String token = expiredProvider.createToken(1L, "CUSTOMER");
        assertThat(jwtProvider.validateToken(token)).isFalse();
    }

    @Test
    void 토큰에서_인증_정보_추출_성공() {
        String token = jwtProvider.createToken(1L, "CUSTOMER");
        var authentication = jwtProvider.getAuthentication(token);
        assertThat(authentication.getName()).isEqualTo("1");
        assertThat(authentication.getAuthorities())
            .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"));
    }
}
