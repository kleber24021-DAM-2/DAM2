package org.quevedo.server.ee.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.NoArgsConstructor;
import org.quevedo.server.ee.utils.EEConst;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor
public class JWTUtils {
    private Key generatedKey;

    @Inject
    public JWTUtils(
            @Named(EEConst.JWT) Key generatedKey) {
        this.generatedKey = generatedKey;
    }

    public String getOK2(String username, String group) throws NoSuchAlgorithmException {
        return Jwts.builder()
                .setSubject(EEConst.JWT_SUBJECT)
                .setIssuer(EEConst.JWT_ISSUER)
                .setExpiration(Date
                        .from(LocalDateTime.now().plusMinutes(5).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim(EEConst.USER, username)
                .claim(EEConst.GROUP, group)
                .signWith(generatedKey).compact();
    }

    public Jws<Claims> verifyToken(String token) throws NoSuchAlgorithmException {
        return Jwts.parserBuilder()
                .setSigningKey(generatedKey)
                .build()
                .parseClaimsJws(token);
    }

}
