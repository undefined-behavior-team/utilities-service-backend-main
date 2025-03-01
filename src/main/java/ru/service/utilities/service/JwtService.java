package ru.service.utilities.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.service.utilities.entity.AdminUser;
import ru.service.utilities.entity.Client;
import ru.service.utilities.entity.Role;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret-key.public}")
    private String jwtPrivateKey;
    @Value("${jwt.secret-key.public}")
    private String jwtPublicKey;

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractType(String token) {
        return extractClaim(token, v -> v.get("type")).toString();
    }
    public Role extractRole(String token){
        return extractClaim(token, v -> {
            String z = v.get("role").toString();
            if(z.equals(Role.CLIENT.name())) return Role.CLIENT;
            if(z.equals(Role.ADMIN.name())) return Role.ADMIN;
            if(z.equals(Role.HOMEOWNER.name())) return Role.HOMEOWNER;
            return Role.UNKNOWN;
        });
    }
    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        long live = 2L * 60 * 60 * 1000;
        if (userDetails instanceof AdminUser customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("role", customUserDetails.getRole());
        } else if (userDetails instanceof Client customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("role", Role.CLIENT);
            live = 10L * 60 * 60 * 1000;
        }
        claims.put("type", "access");

        long issuedAt = System.currentTimeMillis();

        return generateToken(claims, userDetails, new Date(issuedAt), new Date(issuedAt + live));
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        long issuedAt = System.currentTimeMillis();
        Date expiration = new Date(issuedAt + 30L * 24 * 60 * 60 * 1000);

        return generateToken(claims, userDetails, new Date(issuedAt), expiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, Date issuedAt, Date expiration) {
        return Jwts.builder()
                .claims(extraClaims).subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(getSigningKey()).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
