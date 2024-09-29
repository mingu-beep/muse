//package min.project.muse.config.jwt;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.SecurityException;
//import io.netty.handler.codec.base64.Base64;
//import lombok.extern.slf4j.Slf4j;
//import min.project.muse.domain.JwtToken;
//import min.project.muse.domain.user.User;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Component
//public class JwtTokenProvider {
//
//    // Value annotation 을 사용하지 않고 secret_key 등 properties에 설정한 값을 불러옴
//    private final Key key;
//    private final String issuer;
//
//    public JwtTokenProvider(@Value("${jwt.secret_key}") String secretKey, @Value("${jwt.issuer}") String issuer) {
//
//        this.issuer = issuer;
//
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String generateToken(User user, Duration expiredAt) {
//
//        log.info("---------------- generate 1");
//        Date now = new Date();
//        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
//    }
//
//    public String makeToken(Date expiry, User user) {
//        Date now = new Date();
//
//        return Jwts.builder()
//                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 type : JWT
//                .setIssuer(issuer)
//                .setIssuedAt(now)
//                .setExpiration(expiry)
//                .setSubject(user.getUsername())
//                .claim("auth", user.getAuthorities())
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    // User 정보를 저장하고 Access Token, Refresh Token 을 생성하는 메서드
//    public JwtToken generateToken(Authentication authentication) {
//        log.info("---------------- generate 2");
//
//        // 권한 가져오기
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//
//        long now = (new Date()).getTime();
//
//        // Access Token 생성
//        Date accessTokenExpiresIn = new Date(now + 86_400_000);
//        String accessToken = Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim("auth", authorities)
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        // Refresh Token 생성
//        String refreshToken = Jwts.builder()
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        return JwtToken.builder()
//                .grantType("Bearer")
//                .accessToken(accessToken)
//                .refreshToken(refreshToken)
//                .build();
//    }
//
//    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
//    public Authentication getAuthentication(String accessToken) {
//
//        // Jwt 토큰 복호화
//        Claims claims = parseClaims(accessToken);
//
//        if (claims.get("auth") == null) {
//            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
//        }
//
//        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(claims.get("auth").toString().split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .toList();
//
//        // UserDetails 객체를 만들어서 Authentication return
//        // UserDetails: interface, User: UserDetails를 구현한 class
//
//        log.info("claim.subject = {}", claims.getSubject());
//
//        UserDetails principle = new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities); // 구현 필요
//        return new UsernamePasswordAuthenticationToken(principle, "", authorities);
//
//    }
//
//    // 토큰 정보를 확인하는 메서드
//    public boolean validToken(String token) {
//        try {
//
//            Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            log.info("Invalid JWT Token ", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT Token ", e);
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT Token ", e);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty ", e);
//        }
//
//        return false;
//    }
//
//    private Claims parseClaims(String accessToken) {
//        try {
//            return Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(accessToken)
//                    .getBody();
//        } catch (ExpiredJwtException e) {
//            return e.getClaims();
//        }
//    }
//}
