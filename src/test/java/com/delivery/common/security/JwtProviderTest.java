// package com.delivery.common.security;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// class JwtProviderTest {
//
//     private static final String SECRET = "test-secret-key-for-jwt-provider-minimum-256-bits!!";
//     private static final long EXPIRATION = 3600000L;
//
//     private JwtProvider jwtProvider;
//
//     @BeforeEach
//     void setUp() {
//         jwtProvider = new JwtProvider(SECRET, EXPIRATION);
//     }
//
//     @Test
//     void 유효한_토큰_검증_성공() {
//         String token = jwtProvider.createToken("test@test.com");
//         assertThat(jwtProvider.validateToken(token)).isTrue();
//     }
//
//     @Test
//     void 잘못된_토큰_검증_실패() {
//         assertThat(jwtProvider.validateToken("invalid.token.value")).isFalse();
//     }
//
//     @Test
//     void 만료된_토큰_검증_실패() {
//         JwtProvider expiredProvider = new JwtProvider(SECRET, -1L);
//         String token = expiredProvider.createToken("test@test.com");
//         assertThat(jwtProvider.validateToken(token)).isFalse();
//     }
//
//     @Test
//     void 토큰에서_이메일_추출_성공() {
//         String email = "test@test.com";
//         String token = jwtProvider.createToken(email);
//         assertThat(jwtProvider.getEmail(token)).isEqualTo(email);
//     }
// }
