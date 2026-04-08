package com.delivery.member.application.dto;

import com.delivery.member.domain.Member;

public record MemberResult(
        String email,
        String phoneNum,
        String address,
        String role
) {
    public static MemberResult from(Member member) {
        return new MemberResult(
                member.getEmail(),
                member.getPhone_num(),
                member.getAddress(),
                member.getRole().name()
        );
    }
}
