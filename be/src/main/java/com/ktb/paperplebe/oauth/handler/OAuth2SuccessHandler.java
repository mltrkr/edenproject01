package com.ktb.paperplebe.oauth.handler;

import com.ktb.paperplebe.auth.config.jwt.JwtUtil;
import com.ktb.paperplebe.auth.service.TokenService;
import com.ktb.paperplebe.common.service.CookieService;
import com.ktb.paperplebe.oauth.OAuth2CustomUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${app.properties.frontendDomain}")
    private String frontendDomain;

    private final JwtUtil jwtUtil;
    private final CookieService cookieService;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("OAuth2 Login 성공!");

        try {
            OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();

            String accessToken = jwtUtil.generateAccessToken(oAuth2User.getUserId(), oAuth2User.getMemberRole());
            String refreshToken = jwtUtil.generateRefreshToken(oAuth2User.getUserId());
            tokenService.saveRefreshToken(refreshToken, oAuth2User.getUserId());
            log.info("accessToken: " + accessToken);
            log.info("refreshToken: " + refreshToken);

            ResponseCookie accessTokenCookie = cookieService.createCookie(JwtUtil.ACCESS_TOKEN, accessToken);
            ResponseCookie refreshTokenCookie = cookieService.createCookie(JwtUtil.REFRESH_TOKEN, refreshToken);

            response.addHeader(SET_COOKIE, accessTokenCookie.toString());
            response.addHeader(SET_COOKIE, refreshTokenCookie.toString());

            log.info("frontendDomain: " + frontendDomain);
            response.sendRedirect(frontendDomain);
        } catch (Exception e) {
            log.error("OAuth2 Login 성공 후 예외 발생 : {}", e.getMessage());
        }
    }
}
