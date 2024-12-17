package com.pickfit.pickfit.oauth2.successHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // JWT 토큰 생성
        String token = generateToken(oAuth2User);

        // 프론트엔드로 리디렉션 (토큰 포함)
        getRedirectStrategy().sendRedirect(request, response,
                "http://localhost:3000/login/oauth2/code/google?token=" + token);
    }

    private String generateToken(OAuth2User oAuth2User) {
        // JWT 토큰 생성 로직 구현
        return "generated-jwt-token";
    }
}