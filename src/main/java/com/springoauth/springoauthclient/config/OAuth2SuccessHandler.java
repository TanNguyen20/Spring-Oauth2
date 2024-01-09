package com.springoauth.springoauthclient.config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Value("${app.frontEndUrl}")
    private String frontEndUrl;


    @Autowired
    private JwtProvider jwtProvider;

    public String authenticateUser() {
        log.info("STARTING CREATING JWT TOKEN");
        // Authenticate the user using the authentication manager
        Authentication authenticated = SecurityContextHolder.getContext().getAuthentication();

        // Generate the JWT token
        String jwtToken = jwtProvider.generateToken(authenticated);
        log.info("CREATED JWT TOKEN SUCCESSFULLY");
        return jwtToken;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        log.info("AUTHENTICATION BY AZURE SUCCESSFULLY");
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        log.info(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId());

        if ("azure-dev".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            String jwtToken = authenticateUser();

            if (jwtToken != null) {
                log.info("SETTING COOKIE");
                ResponseCookie springCookie = ResponseCookie.from("jwtToken", jwtToken)
                        .httpOnly(false)
                        .secure(false)
                        .path("/")
                        .maxAge(86400)
                        .build();
                // Add the JWT token cookie to the response
                response.addHeader(HttpHeaders.SET_COOKIE, springCookie.toString());
            }
            log.info("SETTING REDIRECTING URL");
            this.setAlwaysUseDefaultTargetUrl(true);
            this.setDefaultTargetUrl(frontEndUrl);
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}