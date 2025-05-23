package com.gbsw.gbswhub.domain.jwt.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TokenProvider {

    private final String issuer;
    private final SecretKey key;
    private final io.jsonwebtoken.JwtParser parser;

    public TokenProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.issuer}") String issuer
    ) {
        this.issuer = issuer;
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        this.parser = Jwts.parser().verifyWith(key).build();
    }

    public String generateToken(com.gbsw.gbswhub.domain.user.model.User user, Duration expiredAt, boolean isAccessToken) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiredAt.toMillis());

        return Jwts.builder()
                .header().add("type", "JWT").add("alg", "HS256").and()
                .claims()
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiry)
                .subject(user.getUsername())
                .add("type", isAccessToken ? "A" : "R")
                .add("id", user.getUser_id())
                .add("role", user.getRole().name())
                .and()
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        String type = claims.get("type").toString();
        if (type == null || !type.equals("A")) throw new IllegalArgumentException("");

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        authorities.add(new SimpleGrantedAuthority("club_leader"));
        authorities.add(new SimpleGrantedAuthority("admin"));
        authorities.add(new SimpleGrantedAuthority("mentor"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(claims.getSubject())
                .password("")
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    public Claims getClaims(String token) {
        Jws<Claims> jws = parser.parseSignedClaims(token);
        return jws.getPayload();
    }
}
