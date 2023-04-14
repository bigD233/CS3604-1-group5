package com.example.whitecommunity.utils;

import java.util.*;
import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {
    private static final long EXP_TIME = 24 * 60 * 60 * 1000;

    public static String sign(int id, String password) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + EXP_TIME;
        Date now = new Date(nowMillis);
        Date expDate = new Date(expMillis);

        Algorithm algorithm = Algorithm.HMAC256(password);

        String token = JWT.create()
                .withClaim("id", id)
                .withIssuedAt(now)
                .withExpiresAt(expDate)
                .sign(algorithm);

        return token;
    }

    public static int getId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Integer res = jwt.getClaim("id").asInt();
            if (res == null) {
                return -1;
            }
            return res.intValue();
        } catch (JWTDecodeException e) {
            return -1;
        }
    }

    public static boolean verify(String token, int id, String password) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(password);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("id", id)
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
