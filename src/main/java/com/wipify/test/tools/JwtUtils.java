package com.wipify.test.tools;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class JwtUtils {

    private static final int expireHourToken = 24;
    //private static final int expireHourRefreshToken = 72;

    private static final String SECRET = "8FEC3E89AE60B3A3679012BCD891A882A1567E78F5685B04235FF250BCE6166E";


    public static String generateAuthToken(String username, Integer id, String issuer, List<String> roles) {
        try {
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer(issuer)
                    .claim("roles", roles)
                    .claim("idUser", id)
                    .expirationTime(Date.from(Instant.now().plusSeconds(expireHourToken * 3600)))
                    .issueTime(new Date())
                    .build();
            Payload payload = new Payload(claims.toJSONObject());

            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                    payload);

            jwsObject.sign(new MACSigner(SECRET));
            return jwsObject.serialize();
        }
        catch (JOSEException e) {
            throw new RuntimeException("Error to create JWT", e);
        }
    }

}
