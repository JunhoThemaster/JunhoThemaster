package project.ticketlink.Util;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.access.expiration}")
    private long ACCESS_TOKEN_VALIDITY;

    @Value("${jwt.refresh.expiration}")
    private long REFRESH_TOKEN_VALIDITY;


    public String generateToken(String Id) {
        byte [] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        SecretKeySpec spec = new SecretKeySpec(decodedKey,  SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(createClaims(Id))
                .setSubject(Id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS256 ,spec)
                .compact();
    }

    private Claims createClaims(String Id) {
        Claims claims = Jwts.claims();
        claims.put("Id", Id);
        // 필요한 다른 클레임 추가
        return claims;
    }

    public String extractId(String token) {
        // Base64로 인코딩된 비밀 키를 디코딩
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);

        // 디코딩된 키를 SecretKeySpec 객체로 변환
        SecretKeySpec spec = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS256.getJcaName());

        // JWT 파서 설정 및 토큰 파싱
        Claims claims = Jwts.parser()
                .setSigningKey(spec) // SecretKeySpec 객체 사용
                .parseClaimsJws(token)
                .getBody();

        System.out.println(claims.get("Id" ,String.class));

        return claims.get("Id", String.class);
    }

    private boolean isTokenExpired(String token){
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        SecretKeySpec spec = new SecretKeySpec(decodedKey,  SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parser()
                .setSigningKey(spec)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    public boolean validateToken(String token , String Id) {
        final String tokenUserid = extractId(token);
        return (tokenUserid.equals(Id) && !isTokenExpired(token));
    }

    public boolean verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(SECRET_KEY)) // 비밀 키로 서명 검증
                    .parseClaimsJws(token)
                    .getBody();
            return true; // 토큰이 유효함
        } catch (SignatureException e) {
            // 서명이 올바르지 않음
            System.out.println("Invalid JWT signature: " + e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            // 토큰이 만료됨
            System.out.println("Expired JWT token: " + e.getMessage());
            return false;
        } catch (JwtException e) {
            // 기타 JWT 관련 예외 처리
            System.out.println("JWT error: " + e.getMessage());
            return false;
        }
    }

        // Refresh token 생성 메소드 추가
        public String generateRefreshToken(String Id) {

            byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
            SecretKeySpec spec = new SecretKeySpec(decodedKey,  SignatureAlgorithm.HS256.getJcaName());
            return Jwts.builder()
                    .setClaims(createClaims(Id))
                    .setSubject(Id)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY * 1000))
                    .signWith(SignatureAlgorithm.HS256,spec)
                    .compact();
        }




}
