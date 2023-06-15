package com.wipify.test.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Vincenzo Racca
 */
public abstract class JwtUtils {

    private static final int expireHourToken = 24;
    private static final int expireHourRefreshToken = 72;

    private static final String SECRET = "FBA898697394CDBC534E7ED86A97AA59F627FE6B309E0A21EEC6C9B130E0369C";


    public static String createAccessToken(String username, String issuer, List<String> roles) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer(issuer)
                    .claim("roles", roles)
                    .expirationTime(Date.from(Instant.now().plusSeconds(expireHourToken * 3600)))
                    .issueTime(new Date())
                    .build();

            Payload payload = new Payload(claims.toJSONObject());

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                    payload);

            jwsObject.sign(new MACSigner(SECRET));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error to create JWT", e);
        }
    }

    public static String createRefreshToken(String username) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(username)
                    .expirationTime(Date.from(Instant.now().plusSeconds(expireHourRefreshToken * 3600)))
                    .build();

            Payload payload = new Payload(claims.toJSONObject());

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                    payload);

            jwsObject.sign(new MACSigner(SECRET));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error to create JWT", e);
        }
    }

    public static UsernamePasswordAuthenticationToken parseToken(String token) throws JOSEException, ParseException,
            BadJOSEException {

        byte[] secretKey = SECRET.getBytes();
        SignedJWT signedJWT = SignedJWT.parse(token);
        signedJWT.verify(new MACVerifier(secretKey));
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();

        JWSKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.HS256,
                new ImmutableSecret<>(secretKey));
        jwtProcessor.setJWSKeySelector(keySelector);
        jwtProcessor.process(signedJWT, null);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        String username = claims.getSubject();
        var roles = (List<String>) claims.getClaim("roles");
        var authorities = roles == null ? null : roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    @Configuration
    @RequiredArgsConstructor
    public static class SecurityConfig {
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
            return authConfig.getAuthenticationManager();
        }


        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
            http
                    .cors().and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeHttpRequests((authz) -> authz
                            .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/articles/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/article/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/user/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/confirmation/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/youtubeVideo/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/youtubevideo/**").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/article/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/article/**").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.POST, "/article/**").hasAuthority("ROLE_ADMIN")
                            .anyRequest().authenticated()
                    )
                    .addFilter(new CustomAuthenticationFilter(authenticationManager))
                    .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .headers().cacheControl();
            return http.build();
        }
    }
}
