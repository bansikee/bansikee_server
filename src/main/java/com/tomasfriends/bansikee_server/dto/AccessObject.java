package com.tomasfriends.bansikee_server.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Builder
public class AccessObject {

    @NotBlank
    private final String tokenType;

    @NotBlank
    private String accessToken;

    @Positive
    private Integer expiresIn;

    @NotBlank
    private String refreshToken;

    @Positive
    private Integer refreshTokenExpiresIn;

    private String scope;
}
