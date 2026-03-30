package com.delivery.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    
    public static LoginResponse of(String accessToken) {
        return new LoginResponse(accessToken);
    }
}
