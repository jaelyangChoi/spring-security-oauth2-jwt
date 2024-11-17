package org.example.springsecurityoauth2jwt.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.springsecurityoauth2jwt.dto.CustomOAuth2User;
import org.example.springsecurityoauth2jwt.jwt.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    //로그인이 성공하면 JWT 발급하여 쿠키로 프론트 측에 전달
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        String username = principal.getUsername();
        String role = principal.getAuthorities().iterator().next().getAuthority();
        String token = jwtUtil.createJwt(username, role, 60 * 60 * 60L);

        response.addCookie(createCookie("Authorization", token));
        response.sendRedirect("http://localhost:3000/");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 ^ 60);
        //cookie.setSecure(true); //https 에서만 사용
        cookie.setPath("/"); //전역에 쿠키가 보인다
        cookie.setHttpOnly(true); //javascript 가 해당 쿠키를 가져가지 못하게
        return cookie;
    }
}
