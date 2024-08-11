package com.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dto.AdminDTO;
import com.services.AuthenticationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    public CustomAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, String name, String permission) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 360000000);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withIssuer(username)
                .withClaim("name", name)
                .withClaim("permission", permission)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT
                .require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);

        AdminDTO user = authenticationService.findByLogin(decoded.getIssuer());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public Authentication validateCredentials(AdminDTO adminDTO) {
        AdminDTO admin = authenticationService.authenticate(adminDTO);
        String token = createToken(admin.getUsername(), admin.getName(), admin.getPermission());
        admin.setToken(token);
        return new UsernamePasswordAuthenticationToken(new AdminDTO(null, admin.getUsername(), admin.getName(), null, null, admin.getPermission(), admin.getToken()), null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println("username: " + username);
        System.out.println("password: " + password);

//        AdminDTO adminDTO = new AdminDTO();
//        adminDTO.setUsername(username);
//        adminDTO.setPassword(password.toCharArray());
//
//        AdminDTO authenticatedAdmin = authenticationService.authenticate(adminDTO);
//
//        if (authenticatedAdmin == null) {
//            throw new BadCredentialsException("Invalid username or password");
//        }
//
//        return new UsernamePasswordAuthenticationToken(authenticatedAdmin, null, Collections.emptyList());
//
        return authentication;
    }
}
