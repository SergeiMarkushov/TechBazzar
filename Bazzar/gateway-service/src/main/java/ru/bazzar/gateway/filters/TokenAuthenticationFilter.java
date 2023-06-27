package ru.bazzar.gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenAuthenticationFilter extends AbstractGatewayFilterFactory<TokenAuthenticationFilter.Config> {
    private final JwtDecoder jwtDecoder;

    public TokenAuthenticationFilter(JwtDecoder jwtDecoder) {
        super(TokenAuthenticationFilter.Config.class);
        this.jwtDecoder = jwtDecoder;
    }

    public static class Config {
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header"));
            }
            String token = authorizationHeader.split(" ")[1].trim();
            try {
                Jwt jwt = jwtDecoder.decode(token);
                Map<String, Object> claims = jwt.getClaims();
                String username = (String) claims.get("email");

                Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
                List<String> roles = (List<String>) realmAccess.get("roles");
                Set<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
                return chain.filter(exchange.mutate().request(exchange.getRequest().mutate()
                                .header("Authorization", "Bearer " + token)
                                .header("username", username)
                                .build()).build())
                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
            } catch (JwtException e) {
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
            }
        };
    }
}
