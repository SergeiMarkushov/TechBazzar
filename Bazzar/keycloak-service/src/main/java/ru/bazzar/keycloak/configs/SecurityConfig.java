package ru.bazzar.keycloak.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("client.url")
    private String clientUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests()
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer(jwtConfigurer -> {
                    jwtConfigurer.jwt(jwtCustomizer -> {
                        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
                        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
                            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
                            List<String> roles = (List<String>) realmAccess.get("roles");

                            return roles.stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .map(it -> (GrantedAuthority) it)
                                    .toList();
                        });

                        jwtCustomizer.jwtAuthenticationConverter(authenticationConverter);
                    });
                })
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(clientUrl));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
