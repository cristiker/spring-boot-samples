package com.cristik.test;

import com.google.common.collect.Maps;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author nizhenghua
 * @date 2022/01/24
 */
public class JwtTest {

    public static void main(String[] args) {
        Integer userId = 842;
        String mobile = "18800008899";
        Integer roleId = 842;
        String roleCode = "BW7";
        Integer tenantId = 11249;

        byte[] key = Base64.decodeBase64("NVNQMDl5eERkY2l1R2xWSXFwZFRuNmJmNzlGWkVxTmk=");
        Map<String, Object> claims = Maps.newHashMap();
        claims.put("userId", userId);
        claims.put("mobile", mobile);
        claims.put("selectedTenantId", tenantId);
        claims.put("selectedRoleId", roleId);
        claims.put("selectedRoleCode", roleCode);

        String jwt = Jwts.builder().setId(UUID.randomUUID().toString())
                .setHeaderParam("tokenType", "accessToken")
                .setSubject(String.valueOf(userId))
                .setIssuer("Turbo")
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 365 * 24 * 3600 * 1000))
                .signWith(Keys.hmacShaKeyFor(key), SignatureAlgorithm.HS256).compact();
        String token = "Bearer " + jwt;
        System.out.println(token);

    }
}
