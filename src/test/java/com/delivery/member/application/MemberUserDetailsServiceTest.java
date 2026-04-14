package com.delivery.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.delivery.member.domain.Member;
import com.delivery.member.domain.Role;
import com.delivery.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberUserDetailsServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberUserDetailsService memberUserDetailsService;

    @Test
    void 존재하는_이메일로_UserDetails_반환() {
        Member member = new Member("test@test.com", "password", "010-1234-5678", Role.CUSTOMER, "서울시");
        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.of(member));

        UserDetails userDetails = memberUserDetailsService.loadUserByUsername("test@test.com");

        assertThat(userDetails.getUsername()).isEqualTo("test@test.com");
        assertThat(userDetails.getAuthorities()).isNotEmpty();
    }

    @Test
    void 존재하지_않는_이메일로_예외_발생() {
        when(memberRepository.findByEmail("none@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberUserDetailsService.loadUserByUsername("none@test.com"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
