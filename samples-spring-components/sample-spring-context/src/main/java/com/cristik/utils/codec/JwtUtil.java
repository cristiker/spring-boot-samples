package com.cristik.utils.codec;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author cristik
 */
public class JwtUtil {

    /**
     * JWT 加解密类型
     */
    private static final SignatureAlgorithm JWT_ALG = SignatureAlgorithm.HS256;
    /**
     * 生成密钥使用的密码
     */
    private static final String JWT_RULE = "wjtree.xin";
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 构建JWT
     *
     * @param alg      jwt 加密算法
     * @param key      jwt 加密密钥
     * @param iss      jwt 签发者
     * @param sub      jwt 面向的用户
     * @param aud      jwt 接收方
     * @param nbf      jwt 生效日期时间
     * @param iat      jwt 签发时间
     * @param jti      jwt 唯一身份标识
     * @param duration jwt 有效时间，单位：秒
     * @return JWT字符串
     */
    public static JwtInfo buildJwt(SignatureAlgorithm alg, Key key, Map<String, Object> params
            , String sub, String aud, String jti, String iss, Date nbf, Date iat, Integer duration) {
        if (iat == null) {
            iat = new Date();
        }
        DateTime exp = null;
        if (duration != null) {
            exp = (nbf == null ? new DateTime(iat.getTime()).plusSeconds(duration)
                    : new DateTime(nbf).plusSeconds(duration));
        }
        if (jti == null) {
            jti = UUID.randomUUID().toString();
        }
        // JWT公共字段
        JwtBuilder jwtBuilder = Jwts.builder().signWith(alg, key).setSubject(sub).setAudience(aud).setId(jti)
                .setIssuer(iss).setNotBefore(nbf).setIssuedAt(iat).setExpiration(exp != null ? exp.toDate() : null);
        if (params != null && params.size() > 0) {
            for (String s : params.keySet()) {
                Object value = params.get(s);
                jwtBuilder.claim(s, value);
            }
        }
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setJwtToken(jwtBuilder.compact());
        jwtInfo.setJti(jti);
        return jwtInfo;
    }

    /**
     * 构建JWT
     */
    public static JwtInfo buildJwt(String sub, String aud, String jti, String iss, Date nbf, Date iat, Integer duration
            , Map<String, Object> params) {
        return buildJwt(JWT_ALG, generateKey(JWT_ALG, JWT_RULE), params, sub, aud, jti, iss, nbf, iat, duration);
    }

    /**
     * 构建Refresh JWT
     */
    public static JwtInfo buildRefreshJwt(String sub, String aud, String jti, String iss, Date nbf, Date iat, Integer duration
            , Map<String, Object> params) {
        params.put("refresh", true);
        return buildJwt(JWT_ALG, generateKey(JWT_ALG, JWT_RULE), params, sub, aud, jti, iss, nbf, iat, duration);
    }

    /**
     * 构建JWT
     *
     * @param sub jwt 面向的用户
     * @param jti jwt 唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     * @return JWT字符串
     */
    public static JwtInfo buildJwt(Map<String, Object> params, String sub, String jti, Integer duration) {
        return buildJwt(sub, null, jti, null, null, null, duration, params);
    }

    /**
     * 构建JWT
     * <p>使用 UUID 作为 jti 唯一身份标识</p>
     * <p>JWT有效时间 600 秒，即 10 分钟</p>
     *
     * @param sub jwt 面向的用户
     * @return JWT字符串
     */
    public static JwtInfo buildJwt(String sub) {
        return buildJwt(sub, null, UUID.randomUUID().toString(), null, null, null, 600, null);
    }

    /**
     * 解析JWT
     * 可能抛出的异常有
     * {@link ExpiredJwtException}
     * {@link UnsupportedJwtException}
     * {@link MalformedJwtException}
     * {@link SignatureException}
     * {@link IllegalArgumentException}
     */
    public static JwtInfo parseJwt(Key key, String claimsJws) {
        // 解析 JWT 字符串
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setJwtToken(claimsJws);
        try {
            Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(key).parseClaimsJws(claimsJws);
            jwtInfo.setClaimsJws(jwsClaims);
            String jti = jwsClaims.getBody().getId();
            if (jti != null) {
                jwtInfo.setJti(jti);
            }
            jwtInfo.setValid(true);
        } catch (Exception e) {
            jwtInfo.setException(e);
            jwtInfo.setValid(false);
        }
        return jwtInfo;
    }

    /**
     * 使用默认密钥解析JWT
     */
    public static JwtInfo parseJwt(String claimsJws) {
        return parseJwt(generateKey(JWT_ALG, JWT_RULE), claimsJws);
    }

    /**
     * 校验JWT的body是否为空
     */
    public static Boolean checkJwtBodyEmpty(String claimsJws) {
        boolean flag = false;
        try {
            SecretKey key = generateKey(JWT_ALG, JWT_RULE);
            // 获取 JWT 的 payload 部分
            JwtInfo jwtInfo = parseJwt(key, claimsJws);
            if (!jwtInfo.isValid()) {
                return false;
            } else {
                flag = (jwtInfo.getClaimsJws().getBody() != null);
            }
        } catch (Exception e) {
            logger.warn("JWT验证出错，错误原因：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * 校验JWT中sub是否一致
     */
    public static Boolean checkJwtSub(String claimsJws, String sub) {
        return checkJWT(generateKey(JWT_ALG, JWT_RULE), claimsJws, sub);
    }

    public static Boolean checkJWT(Key key, String claimsJws, String sub) {
        boolean flag = false;
        try {
            JwtInfo jwtInfo = parseJwt(key, claimsJws);
            if (!jwtInfo.isValid()) {
                return false;
            }
            Claims claims = jwtInfo.getClaimsJws().getBody();
            flag = sub.equalsIgnoreCase(claims.getSubject());
        } catch (Exception e) {
            logger.warn("JWT验证出错，错误原因：{}", e.getMessage());
        }
        return flag;
    }

    /**
     * 随机生成秘钥生成Key
     *
     * @param alg
     * @return
     */
    private static SecretKey generateKey(SignatureAlgorithm alg) {
        return MacProvider.generateKey(alg);
    }

    /**
     * 自定义秘钥生成Key
     *
     * @param alg
     * @param rule
     * @return
     */
    private static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
        byte[] bytes = Base64.decodeBase64(rule);
        return new SecretKeySpec(bytes, alg.getJcaName());
    }

    public static class JwtInfo {
        private String jti;

        private String jwtToken;

        private Integer type;

        private boolean valid;

        private Jws<Claims> claimsJws;

        private Exception exception;

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public Jws<Claims> getClaimsJws() {
            return claimsJws;
        }

        public void setClaimsJws(Jws<Claims> claimsJws) {
            this.claimsJws = claimsJws;
        }

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        public String getJti() {
            return jti;
        }

        public void setJti(String jti) {
            this.jti = jti;
        }

        public String getJwtToken() {
            return jwtToken;
        }

        public void setJwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
        }
    }
}