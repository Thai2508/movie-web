package com.identity.service;

import com.identity.dto.request.AuthenticationRequest;
import com.identity.dto.request.IntrospectRequest;
import com.identity.dto.response.AuthenticationResponse;
import com.identity.dto.response.IntrospectResponse;
import com.identity.entity.TokenEntity;
import com.identity.entity.UserEntity;
import com.identity.exception.AppException;
import com.identity.exception.ErrorCode;
import com.identity.repository.TokenRepository;
import com.identity.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {

    UserRepository userRepository;
    TokenRepository tokenRepository;

    @NonFinal
    @Value("${jwt.private-key}")
    protected String PRI_KEY;
    @NonFinal
    @Value("${jwt.public-key}")
    protected String PUB_KEY;
    @NonFinal
    @Value("${jwt.access-token}")
    protected Long accessToken;
    @NonFinal
    @Value("${jwt.refresh-token}")
    protected Long refreshToken;

    public AuthenticationResponse authenticated(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_AUTH));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword()))
            throw new AppException(ErrorCode.PASSWORD_AUTH);

        String access_Token = generateAccessToken(user);

        return AuthenticationResponse.builder()
                .authenticated(true)
                .token(access_Token)
                .build();
    }

    public IntrospectResponse validToken(IntrospectRequest request)
            throws JOSEException, ParseException {

        String token = request.getToken();

        verifier(token);

        return IntrospectResponse.builder()
                .valid(true)
                .build();
    }

    private void verifier(String token)  throws JOSEException, ParseException{
        JWSVerifier verifier = new MACVerifier(PUB_KEY);
        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean isSignatureValid = signedJWT.verify(verifier);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean isTokenExpired = expiryTime.before(new Date());

        boolean isValid = isSignatureValid && !isTokenExpired;
        if (!isValid)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        boolean logout = tokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID());
        if (logout)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

//        return signedJWT;
    }

    public void logout(IntrospectRequest request) throws ParseException {
        String token = request.getToken();
        SignedJWT signedJWT = SignedJWT.parse(token);

        String id = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        TokenEntity tokenEntity = TokenEntity.builder()
                .id(id)
                .expiryTime(expiryTime)
                .build();

        tokenRepository.save(tokenEntity);
    }

    private String generateAccessToken(UserEntity user){
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS512)
                .contentType("JWT")
                .build();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issuer("Thai-ne.com")
                .issueTime(new Date())
                .expirationTime(Date.from(
                        Instant.now().plus(accessToken, ChronoUnit.HOURS)
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
//            RSAPrivateKey rsaPrivateKey = RSAKey.parse("{\"kty\":\"RSA\",\"n\":\"" + PUB_KEY + "\",\"e\":\"AQAB\"," +
//                            " \"d\":\""+ PRI_KEY +"\"}")
//                    .toRSAPrivateKey();
            jwsObject.sign(new MACSigner(PUB_KEY.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return jwsObject.serialize();
    }

    private void generateRefreshToken(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        String username = signedJWT.getJWTClaimsSet().getSubject();
        Date isTokenExpired = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (!isTokenExpired.after(new Date()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        generateAccessToken(user);
    }

    private String buildScope(UserEntity user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
//        !CollectionUtils.isEmpty(user.getRoles())
        if(!user.getRoles().isEmpty()) {
            user.getRoles().forEach(roleEntity -> {
                stringJoiner.add("ROLE_"+roleEntity.getName());
                roleEntity.getPermissions().forEach(permissionEntity -> {
                    stringJoiner.add(permissionEntity.getName());
                });
            });
        }
        return stringJoiner.toString();
    }

}
