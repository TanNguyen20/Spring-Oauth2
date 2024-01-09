package com.springoauth.springoauthclient.config;

import com.springoauth.springoauthclient.model.mapper.GroupsClaimMapper;
import com.springoauth.springoauthclient.support.NamedOidcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(JwtAuthorizationProperties.class)
public class JwtAuthorizationConfiguration {

    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Autowired
    private JwtProvider jwtProvider;

//    @Bean
//    @Order(2)
//    SecurityFilterChain customJwtSecurityChain(HttpSecurity http, JwtAuthorizationProperties props) throws Exception {
//        // @formatter:off
//        return http
//                .cors(Customizer.withDefaults())
//                .authorizeHttpRequests(r -> r.anyRequest().authenticated())
//                .oauth2Login(oauth2 -> {
//                    oauth2.userInfoEndpoint(ep -> ep.oidcUserService(customOidcUserService(props)))
//                        .successHandler(oAuth2SuccessHandler);
//                })
//                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").permitAll())
//                .build();
//    }
//
//    @Bean
//    @Order(1) /// <----
//    public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProvider);
//
//        http.cors(Customizer.withDefaults())
//                .securityMatcher("/jwt/**") /// <----
//                .authorizeHttpRequests(author -> author.requestMatchers("/jwt/**").authenticated()
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/error").permitAll()) /// <----
//                .authorizeHttpRequests(r -> r.requestMatchers("/").permitAll())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//
//    }
//
//    private OAuth2UserService<OidcUserRequest, OidcUser> customOidcUserService(JwtAuthorizationProperties props) {
//        final OidcUserService delegate = new OidcUserService();
//        final GroupsClaimMapper mapper = new GroupsClaimMapper(
//                props.getAuthoritiesPrefix(),
//                props.getGroupsClaim(),
//                props.getGroupToAuthorities());
//
//        return (userRequest) -> {
//            OidcUser oidcUser = delegate.loadUser(userRequest);
//            // Enrich standard authorities with groups
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//            mappedAuthorities.addAll(oidcUser.getAuthorities());
//            mappedAuthorities.addAll(mapper.mapAuthorities(oidcUser));
//
//            oidcUser = new NamedOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), oidcUser.getName());
//
//            return oidcUser;
//        };
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.anyRequest().authenticated())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll);
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

}
