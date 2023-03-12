package com.darglk.blogcommons.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.keycloak.common.util.Base64;

import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

public class JwtParser {
    public static Jws<Claims> parseToken(String token, String jwtKey) throws Exception {
        if (token != null && token.startsWith("Bearer ")) {
            byte[] byteKey = Base64.decode(jwtKey.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");

            var key = kf.generatePublic(X509publicKey);
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace("Bearer ", Strings.EMPTY));
        }
        return null;
    }
}
