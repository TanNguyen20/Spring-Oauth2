package com.springoauth.springoauthclient.config;

import com.springoauth.springoauthclient.support.GroupsClaimMapper;
import com.springoauth.springoauthclient.support.NamedOidcUser;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(JwtAuthorizationProperties.class)
public class JwtAuthorizationConfiguration {

    @Bean
    SecurityFilterChain customJwtSecurityChain(HttpSecurity http, JwtAuthorizationProperties props) throws Exception {
        // @formatter:off
        return http
            .cors(Customizer.withDefaults())
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests( r -> r.requestMatchers("/public/**").permitAll().anyRequest().authenticated())
            .oauth2Login(oauth2 -> {
                  oauth2.userInfoEndpoint(ep -> ep.oidcUserService(customOidcUserService(props)));
            })
        .build();
        // @formatter:on
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService(JwtAuthorizationProperties props) {
        final OidcUserService delegate = new OidcUserService();
        final GroupsClaimMapper mapper = new GroupsClaimMapper(
                props.getAuthoritiesPrefix(),
                props.getGroupsClaim(),
                props.getGroupToAuthorities());

        return (userRequest) -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            // Enrich standard authorities with groups 
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            mappedAuthorities.addAll(oidcUser.getAuthorities());
            mappedAuthorities.addAll(mapper.mapAuthorities(oidcUser));

            oidcUser = new NamedOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), oidcUser.getName());

            return oidcUser;
        };
    }



}
