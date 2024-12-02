package com.operis.operis.subscription.service.adapter.in.rest.helper;

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
