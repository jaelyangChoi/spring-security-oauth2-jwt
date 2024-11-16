package org.example.springsecurityoauth2jwt.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;

    @Override
    public Map<String, Object> getAttributes() {
        //서비스마다 형태가 달라서 쓰지 않기로 한다.
        return null;
    }

    //getAttributes 대신 사용할 사용자 구분 값
    public String getUsername() {
        return userDTO.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDTO.getRole();
            }
        });
        return authorities;
    }

    @Override
    public String getName() {
        return userDTO.getName();
    }


}
