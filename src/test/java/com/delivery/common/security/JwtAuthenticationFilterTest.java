package com.delivery.common.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void 유효한_토큰이면_SecurityContext에_인증_등록() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie("accessToken", "valid.token"));
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        var authentication = new UsernamePasswordAuthenticationToken(
            "1", null, List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"))
        );

        when(jwtProvider.validateToken("valid.token")).thenReturn(true);
        when(jwtProvider.getAuthentication("valid.token")).thenReturn(authentication);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("1");
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void 쿠키가_없으면_SecurityContext에_인증_미등록() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void 유효하지_않은_토큰이면_SecurityContext에_인증_미등록() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(new Cookie("accessToken", "invalid.token"));
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        when(jwtProvider.validateToken("invalid.token")).thenReturn(false);

        filter.doFilterInternal(request, response, filterChain);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
        verify(filterChain).doFilter(request, response);
    }
}
