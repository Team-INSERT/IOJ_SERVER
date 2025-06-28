package com.insert.ioj.global.security.jwt;

import com.insert.ioj.domain.auth.domain.RefreshToken;
import com.insert.ioj.domain.auth.domain.repository.RefreshTokenRepository;
import com.insert.ioj.domain.user.domain.type.Authority;
import com.insert.ioj.global.config.properties.JwtProperties;
import com.insert.ioj.global.error.exception.ErrorCode;
import com.insert.ioj.global.error.exception.IojException;
import com.insert.ioj.global.security.principle.AuthDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    private static final String ACCESS_KEY = "access_token";
    private static final String REFRESH_KEY = "refresh_token";

    public String createAccessToken(String email, Long userId, Authority authority) {
        Date now = new Date();
        return Jwts.builder().signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .setSubject(email)
            .claim("userId", userId)
            .claim("authority", authority)
            .setHeaderParam("typ", ACCESS_KEY)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + jwtProperties.getAccessTime()))
            .compact();
    }

    @Transactional
    public String createRefreshToken(String email) {
        Date now = new Date();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
            .setSubject(email)
            .setHeaderParam("typ", REFRESH_KEY)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + jwtProperties.getRefreshTime()))
            .compact();
        refreshTokenRepository.save(
                new RefreshToken(token, email)
        );
        return token;
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return parseToken(bearer);
    }

    public String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    public UsernamePasswordAuthenticationToken authorization(String token) {
        UserDetails userDetails
            = new AuthDetails(getTokenSubject(token), getTokenUserId(token), getTokenAuthority(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getTokenSubject(String token) {
        return getTokenBody(token).getSubject();
    }

    private Long getTokenUserId(String token) {
        return getTokenBody(token).get("userId", Long.class);
    }

    private Authority getTokenAuthority(String subject) {
        String authorityName = getTokenBody(subject).get("authority", String.class);
        return Authority.valueOf(authorityName);
    }

    private Claims getTokenBody(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new IojException(ErrorCode.EXPIRED_PERIOD_TOKEN);
        } catch (Exception e) {
            throw new IojException(ErrorCode.INVALID_TOKEN);
        }
    }
}
