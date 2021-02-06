package ru.vyazankin.market.bean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:secured.properties")
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("roles", userDetails.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()));

        Date issueDate = new Date();

        return Jwts.builder()
                .addClaims(claimsMap)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issueDate)
                .setExpiration(new Date(issueDate.getTime() + 60*60*1000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public List<String> getRolesFromToken(String token){
        return getClaimFromToken(
                token,
                (Function<Claims, List<String>>) claims -> claims.get("roles", List.class));
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        return claimResolver.apply(getAllClaimsFromToken(token));
    }
}
