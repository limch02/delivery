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

import com.delivery.common.security.JwtProvider;
import com.delivery.member.application.dto.MemberResult;
import com.delivery.member.application.port.PasswordEncoder;
import com.delivery.member.domain.Member;
import com.delivery.member.domain.Role;
import com.delivery.member.exception.MemberException;
import com.delivery.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private MemberService memberService;

    @Test
    void 존재하는_이메일로_회원_조회_성공() {
        Member member = new Member("test@test.com", "password", "010-1234-5678", Role.CUSTOMER, "서울시");
        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.of(member));

        MemberResult result = memberService.getMemberByEmail("test@test.com");

        assertThat(result.email()).isEqualTo("test@test.com");
    }

    @Test
    void 존재하지_않는_이메일로_회원_조회_예외_발생() {
        when(memberRepository.findByEmail("none@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.getMemberByEmail("none@test.com"))
                .isInstanceOf(MemberException.class);
    }

    @Test
    void 주소_수정_성공() {
        Member member = new Member("test@test.com", "password", "010-1234-5678", Role.CUSTOMER, "서울시");
        when(memberRepository.findByEmail("test@test.com")).thenReturn(Optional.of(member));

        String updated = memberService.updateAddress("test@test.com", "부산시");

        assertThat(updated).isEqualTo("부산시");
        assertThat(member.getAddress()).isEqualTo("부산시");
    }

    @Test
    void 존재하지_않는_회원의_주소_수정_시_예외_발생() {
        when(memberRepository.findByEmail("none@test.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> memberService.updateAddress("none@test.com", "부산시"))
                .isInstanceOf(MemberException.class);
    }
}
