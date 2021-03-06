package com.tomasfriends.bansikee_server.service.userservice.oauth2;

import com.tomasfriends.bansikee_server.domain.LoginMethod;
import com.tomasfriends.bansikee_server.domain.jwt.JwtProvider;
import com.tomasfriends.bansikee_server.dto.controllerdto.oauthprofile.GoogleProfile;
import com.tomasfriends.bansikee_server.dto.controllerdto.oauthprofile.KakaoProfile;
import com.tomasfriends.bansikee_server.dto.controllerdto.oauthprofile.Profile;
import com.tomasfriends.bansikee_server.dto.servicedto.BansikeeUser;
import com.tomasfriends.bansikee_server.exceptions.NotRegisteredEmailException;
import com.tomasfriends.bansikee_server.repository.loginrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class OauthSignUpService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Autowired
    public OauthSignUpService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    public String signUpWithKakao(KakaoProfile profile) {
        String nickname = profile.getProperties().getNickname();
        String profileImage = profile.getProperties().getProfile_image();
        String email = profile.getKakao_account().getEmail();

        signUpIfNotUser(nickname, email, profileImage, profile);
        BansikeeUser user = userRepository.findByEmail(email).orElseThrow(NotRegisteredEmailException::new);

        return jwtProvider.getJWT(user, user.getRoles());
    }

    public String signUpWithGoogle(GoogleProfile profile) {
        String nickname = profile.getGiven_name();
        String profileImage = profile.getPicture();
        String email = profile.getEmail();

        signUpIfNotUser(nickname, email, profileImage, profile);
        BansikeeUser user = userRepository.findByEmail(email).orElseThrow(NotRegisteredEmailException::new);

        return jwtProvider.getJWT(user, user.getRoles());
    }

    private void signUpIfNotUser(String nickname, String email, String profileImage, Profile profile) {
        Optional<BansikeeUser> userByEmail = userRepository.findByEmail(email);

        if (userByEmail.isPresent()) return ;

        userRepository.save(BansikeeUser.builder()
            .name(nickname)
            .email(email)
            .loginMethod(profile.getLoginMethod().name())
            .profileImageURL(profileImage)
            .roles(Collections.singletonList("ROLE_USER"))
            .build());
    }
}