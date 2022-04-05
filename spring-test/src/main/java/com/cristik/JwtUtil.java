package com.cristik;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 默认的加解密类型
     */
    private static final SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    /**
     * 默认的秘钥字符串
     */
    private static final String DEFAULT_JWT_RULE = "wjtree.xin";

    /**
     * 随机生成秘钥生成Key
     *
     * @param alg
     * @return
     */
    private static SecretKey generateKey(SignatureAlgorithm alg) {
        if (alg == null) {
            alg = DEFAULT_SIGNATURE_ALGORITHM;
        }
        return MacProvider.generateKey(alg);
    }

    /**
     * 按指定字符串生成秘钥
     *
     * @param alg
     * @param rule
     * @return
     */
    private static SecretKey generateKey(SignatureAlgorithm alg, String rule) {
        if (StringUtils.isEmpty(rule)) {
            rule = DEFAULT_JWT_RULE;
        }
        if (alg == null) {
            alg = DEFAULT_SIGNATURE_ALGORITHM;
        }
        byte[] bytes = Base64.decodeBase64(rule);

        return new SecretKeySpec(bytes, alg.getJcaName());
    }

    /**
     * 构建JWT
     *
     * @param alg       jwt 加密算法
     * @param key       jwt 加密密钥
     * @param params    jwt 额外信息
     * @param jwtClaims jwt Claims
     * @return JWT字符串
     */
    public static JwtInfo buildJwt(SignatureAlgorithm alg, Key key, Map<String, Object> params, JwtClaims jwtClaims) {
        if (jwtClaims == null) {
            throw new JwtException("jwtClaims不能为空");
        }
        if (jwtClaims.getIat() == null) {
            jwtClaims.setIat(new Date());
        }
        DateTime exp;
        if (jwtClaims.getDuration() != null) {
            if (jwtClaims.getNbf() == null) {
                jwtClaims.setNbf(jwtClaims.getIat());
            }
            exp = new DateTime(jwtClaims.getNbf()).plus(jwtClaims.getDuration());
        } else {
            throw new JwtException("JWT token需要指定有效时间");
        }
        if (jwtClaims.getJti() == null) {
            jwtClaims.setJti(UUID.randomUUID().toString());
        }
        // JWT公共字段
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(alg, key)
                .setSubject(jwtClaims.getSub())
                .setAudience(jwtClaims.getAud())
                .setId(jwtClaims.getJti())
                .setIssuer(jwtClaims.getIss())
                .setNotBefore(jwtClaims.getNbf())
                .setIssuedAt(jwtClaims.getIat())
                .setExpiration(exp.toDate());
        if (params != null && params.size() > 0) {
            for (String s : params.keySet()) {
                Object value = params.get(s);
                jwtBuilder.claim(s, value);
            }
        }
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setJwtToken(jwtBuilder.compact());
        jwtInfo.setJti(jwtClaims.getJti());
        Jws<Claims> jwsClaims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtInfo.getJwtToken());
        jwtInfo.setClaimsJws(jwsClaims);
        return jwtInfo;
    }

    /**
     * 构建refresh token
     */
    public static JwtInfo buildToken(SignatureAlgorithm algorithm, String secretKey, JwtClaims jwtClaims
            , Map<String, Object> params) {
        return buildJwt(algorithm, generateKey(algorithm, secretKey), params, jwtClaims);
    }

    /**
     * 解析JWT Token
     */
    public static JwtInfo parseJwt(SignatureAlgorithm algorithm, String secretKey, String claimsJws) {
        JwtInfo jwtInfo = new JwtInfo().setJwtToken(claimsJws);
        try {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .setSigningKey(generateKey(algorithm, secretKey))
                    .parseClaimsJws(claimsJws);
            String jti = jwsClaims.getBody().getId();
            jwtInfo.setJti(jti).setValid(true).setClaimsJws(jwsClaims);
        } catch (Exception e) {
            jwtInfo.setValid(false).setException(e);
        }
        return jwtInfo;
    }
}