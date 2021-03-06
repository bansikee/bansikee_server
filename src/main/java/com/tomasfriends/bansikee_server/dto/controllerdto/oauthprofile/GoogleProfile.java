package com.tomasfriends.bansikee_server.dto.controllerdto.oauthprofile;

import com.tomasfriends.bansikee_server.domain.LoginMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleProfile implements Profile {
    private String email;
    private String name;
    private String picture;
    private String given_name;
    private int iat;
    private int exp;

    @Override
    public LoginMethod getLoginMethod() {
        return LoginMethod.GOOGLE;
    }
}
