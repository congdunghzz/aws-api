package ueh.congdunghzz.aws.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ueh.congdunghzz.aws.common.exception.ApplicationException;
import ueh.congdunghzz.aws.security.CustomUserDetail;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@PropertySource(value = {"classpath:application.yml"})
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${app.security.jwtSecret}")
    private String jwtSecret;
    @Value("${app.security.jwtExpiration}")
    private long jwtExpiration;

    private Key getSignInKey(){
        byte[] key = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(key);
    }
    public String generateToken(UserDetails userDetails){
        String username = userDetails.getUsername();
        Map<String, Object> claim = new HashMap<>();
        claim.put("name", ((CustomUserDetail) userDetails).getUser().getName());
        claim.put("iat", System.currentTimeMillis());
        claim.put("exp", new Date(new Date().getTime() + jwtExpiration).getTime());
        return Jwts.builder()
                .subject(username)
                .claims(claim)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpiration))
                .signWith(getSignInKey())
                .compact();
    }

    public Claims extractAllClaims (String token){
        return Jwts.parser()
                .verifyWith((SecretKey) getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String getUsernameFromToken(String token){
        return extractAllClaims(token).getSubject();
    }


    public boolean isValid(String token){
        try{
            Jwts.parser().verifyWith((SecretKey) getSignInKey()).build().parseSignedClaims(token);
            return true;
        }catch (Exception e){
            throw new ApplicationException(HttpStatus.UNAUTHORIZED,"Token is not valid");
        }
    }
}
