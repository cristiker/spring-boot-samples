package com.cristik;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cristik
 */
@Data
@Accessors(chain = true)
public class JwtInfo {
    private String jti;

    private String jwtToken;

    private Integer type;

    private boolean valid;

    private Jws<Claims> claimsJws;

    private Exception exception;
}
