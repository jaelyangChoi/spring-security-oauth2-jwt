package org.example.springsecurityoauth2jwt.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.springsecurityoauth2jwt.dto.CustomOAuth2User;
import org.example.springsecurityoauth2jwt.dto.UserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //JWT 에 해당하는 쿠키 찾기
        Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("Authorization"))
                .findFirst();
        if (jwtCookie.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = jwtCookie.get().getValue();

        if (jwtUtil.isExpired(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDTO userDTO = UserDTO.builder()
                .username(jwtUtil.getUsername(jwt))
                .role(jwtUtil.getRole(jwt))
                .build();

        //UserDetails 에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        //스프링 시큐리티 인증 토큰 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}