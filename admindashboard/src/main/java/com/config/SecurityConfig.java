package com.config;

import com.security.CustomAuthenticationProvider;
import com.security.JwtAuthFilter;
import com.security.UserAuthenticationEntryPoint;
import com.security.UsernamePasswordAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final CustomAuthenticationProvider userAuthenticationProvider;
    private final UsernamePasswordAuthFilter usernamePasswordAuthFilter;
    private final JwtAuthFilter jwtAuthFilter;
    @Value("${base.url}")
    private String baseUrl;

    public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint,
                          CustomAuthenticationProvider userAuthenticationProvider,
                          JwtAuthFilter jwtAuthFilter,
                          UsernamePasswordAuthFilter usernamePasswordAuthFilter
    ) {
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
        this.usernamePasswordAuthFilter = usernamePasswordAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/v1/admin/signIn", "/v1/admin/signUp").permitAll()
                        .requestMatchers("/ws/**", "/webhook/**", "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
//                .authenticationProvider(userAuthenticationProvider)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(userAuthenticationEntryPoint)
                )
                .addFilterBefore(usernamePasswordAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver() {
//        return new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository(), "/oauth2/authorization");
//    }
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
//    }
//
//    private ClientRegistration googleClientRegistration() {
//        return ClientRegistration.withRegistrationId("google")
//                .clientId("112323394158-l8sa6r7mv69f288oeou8i5e73leefmi8.apps.googleusercontent.com")
//                .clientSecret("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")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
//                .scope("email", "profile", "https://www.googleapis.com/auth/gmail.readonly")
//                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
//                .tokenUri("https://oauth2.googleapis.com/token")
//                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
//                .userNameAttributeName("name")
//                .clientName("Google")
//                .build();
//    }
}