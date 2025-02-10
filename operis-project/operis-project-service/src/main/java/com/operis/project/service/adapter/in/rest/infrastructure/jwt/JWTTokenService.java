package com.operis.project.service.adapter.in.rest.infrastructure.jwt;

import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JWTTokenService {

    private final JwtTokenUtil jwtTokenUtil;

    public String extractUserEmail(String authorizationHeader) {
        JWTClaimsSet jwtClaimsSet = jwtTokenUtil.parseToken(authorizationHeader.substring(7));
        return jwtClaimsSet.getSubject();
    }
}
