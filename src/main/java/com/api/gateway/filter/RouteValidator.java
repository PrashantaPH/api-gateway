package com.api.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> apiEndPoints = List.of(
            "/v1/api/authentication/register",
            "/v1/api/authentication/accesstoken",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> apiEndPoints
                    .stream()
                    .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));

//          OR
//    public Predicate<ServerHttpRequest> isSecured = new Predicate<ServerHttpRequest>() {
//        @Override
//        public boolean test(ServerHttpRequest serverHttpRequest) {
//            for (String uri : apiEndPoints) {
//                if (serverHttpRequest.getURI().getPath().contains(uri)) {
//                    return false;
//                }
//            }
//            return true;
//        }
//    };
}
