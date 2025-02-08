package com.operis.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.operis.dto.LoginResponseDTO;
import com.operis.dto.UserAccountCredentialsPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${code.secret}")
    private String jwtSecret;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserAccountCredentialsPayload credentialsPayload) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentialsPayload.email(), credentialsPayload.password()));
            User user = (User) authentication.getPrincipal();
            String token = generateToken(user.getUsername());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (JOSEException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    private String generateToken(String subject) throws JOSEException {
        JWSSigner signer = new MACSigner(jwtSecret);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(subject)
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 heure d'expiration
                .build();
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
}
