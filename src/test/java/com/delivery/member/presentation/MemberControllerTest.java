package com.delivery.member.presentation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.delivery.common.response.ApiResponse;
import com.delivery.member.application.MemberService;
import com.delivery.member.presentation.dto.AddressModifyRequest;
import com.delivery.member.presentation.dto.AddressModifyResponse;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @Test
    void 주소_수정_성공() {
        UserDetails userDetails = User.builder()
            .username("test@test.com").password("pw").roles("CUSTOMER").build();
        when(memberService.updateAddress("test@test.com", "부산시")).thenReturn("부산시");

        ResponseEntity<ApiResponse<AddressModifyResponse>> response =
            memberController.updateAddress(userDetails, new AddressModifyRequest("부산시"));

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().isSuccess()).isTrue();
        assertThat(response.getBody().getData().address()).isEqualTo("부산시");
    }
}
