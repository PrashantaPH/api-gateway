package com.api.gateway.filter;

import com.api.gateway.exception.ApiGatewayException;
import com.api.gateway.exception.CustomExpiredJwtException;
import com.api.gateway.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private WebClient webClient;

    @Autowired
    private RouteValidator routeValidator;

    public JwtAuthFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                // header contains token oo not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ApiGatewayException("Missing Auth Header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                    try {
//                    This api call not good practice
//                        var response = webClient.get()
//                                .uri("http://JWT-AUTHENTICATION-SVC/v1/api/authentication/token")
//                                .header(HttpHeaders.AUTHORIZATION, authHeader)
//                                .retrieve()
//                                .bodyToMono(Map.class)
//                                .block();
                        jwtUtil.validateToken(authHeader);
                        String username = jwtUtil.extractUsername(authHeader);
                        request = exchange.getRequest()
                                .mutate()
                                .header("loggedInUser", username)
                                .build();
                    } catch (ExpiredJwtException ex) {
                        throw new CustomExpiredJwtException("JWT token has expired. Please log in again.");
                    } catch (Exception ex) {
                        throw new ApiGatewayException("Technical error occurred while validating token.");
                    }
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config {

    }
}
