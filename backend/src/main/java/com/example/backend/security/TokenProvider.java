package com.example.backend.security;

import com.example.backend.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TokenProvider {

  private static final String SECRET_KEY = "secret";


  public String create(UserEntity userEntity) { // 토큰 생성
    final Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

    return Jwts.builder()
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .setSubject(userEntity.getId())
        .setIssuer("Todo app")
        .setIssuedAt(new Date())
        .setExpiration(expiryDate)
        .compact();
  }

  /*
   * Base64로 디코딩 및 파싱이후, 위조안되었으면 페이로드 리턴
   * */
  public String validateAndGetUserId(String token) {
    final Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }
}
