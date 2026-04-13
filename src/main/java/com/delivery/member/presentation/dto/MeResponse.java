package com.delivery.member.presentation.dto;

import com.delivery.member.application.dto.MemberResult;

public record MeResponse(
        String email,
        String phoneNum,
        String address,
        String role
) {
    public static MeResponse from(MemberResult result) {
        return new MeResponse(
                result.email(),
                result.phoneNum(),
                result.address(),
                result.role()
        );
    }
}
