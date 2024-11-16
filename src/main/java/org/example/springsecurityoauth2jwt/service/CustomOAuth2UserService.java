package org.example.springsecurityoauth2jwt.service;

import lombok.RequiredArgsConstructor;
import org.example.springsecurityoauth2jwt.dto.*;
import org.example.springsecurityoauth2jwt.entity.UserEntity;
import org.example.springsecurityoauth2jwt.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

        //OAuth2User에 담긴 응답 값이 형식이 서비스마다 상이하기 때문에 응답 인터페이스를 만들어 구현
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if(registrationId.equals("naver")){
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleRespnose(oAuth2User.getAttributes());
        }else
            return null;

        //리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디 값을 만듦
        String username = oAuth2Response.getProvider() + " "+oAuth2Response.getProviderId();



        //DB 저장
        String role = "ROLE_USER";
        UserEntity foundUser = userRepository.findByUsername(username);
        if(foundUser == null){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setName(oAuth2Response.getName());
            userEntity.setEmail(oAuth2Response.getEmail());
            userEntity.setRole(role);
            userRepository.save(userEntity);
        }else{
            role = foundUser.getRole();
            foundUser.setName(oAuth2Response.getName());
            foundUser.setEmail(oAuth2Response.getEmail());
            userRepository.save(foundUser);
        }

        //응답
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .name(oAuth2Response.getName())
                .role(role)
                .build();

        return new CustomOAuth2User(userDTO);
    }
}
