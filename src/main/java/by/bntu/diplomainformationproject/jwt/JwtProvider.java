package by.bntu.diplomainformationproject.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {
    private static final int EXPIRE_DATE = 30;

    @Value("{jwt.keyword}")
    private String keyWord;

    public String generateToken(String email) {
        Date expireDate = Date.from(LocalDate.now().plusDays(EXPIRE_DATE).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, keyWord)
                .compact();
    }

    public boolean validateToken(String token) {
        Jwts.parser().setSigningKey(keyWord).parseClaimsJws(token);
        return true;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(keyWord).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}