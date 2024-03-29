package com.tomasfriends.bansikee_server.sign.service.oauth2;

import com.tomasfriends.bansikee_server.sign.service.dto.Profile;
import com.tomasfriends.bansikee_server.sign.service.dto.TokenRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface OAuthService {
    Profile getProfile(TokenRequestDto accessTokenDto);
}
