package min.project.muse.config.jwt;

import io.jsonwebtoken.Jwts;
import min.project.muse.domain.user.User;
import min.project.muse.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    // generateToken() 검증 테스트
    @DisplayName("generateToken() : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given : 토큰에 유저 정보를 추가하기 위한 테스트 유저를 만든다.
        User testUser = userRepository.save(User.builder()
                .username("username")
                .email("email")
                .password("password")
                .build());

        // when : 토큰 제공자의 generateToken() 메서드를 호출해 토큰을 만든다.
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then : jjwt 라이브러리를 사용해 토큰을 복호화한다.
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId()); // 토큰을 만들 때 클레임으로 넣어둔 id값이 given 절에서 만든 유저 ID와 동일한지 확인
    }

    // validToken() 검증 테스트
    @DisplayName("validToken() : 만료된 토큰인 때에 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken() {

        // given : 이미 만료된 토큰을 생성한다.
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis())).build()
                .createToken(jwtProperties);

        // when : validToken을 호출해 유효한 토큰인지 검증한다.
        boolean result = tokenProvider.validToken(token);

        // then : 반환값이 false인 것임을 확인한다.
        assertThat(result).isFalse();

    }

    @DisplayName("validToken() : 만료된 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {

        // given : 유효한 토큰을 생성한다. (현재 시간으로 부터 14일 뒤)
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        // when : validToken 메서드를 호출해 유효한 토큰인지 검증
        boolean result = tokenProvider.validToken(token);

        // then : 반환값이 true인 것을 확인한다.
        assertThat(result).isTrue();

    }

    // getAuthentication() 테스트
    @DisplayName("getAuthentication() : 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given : subject에 username을 추가한 토큰을 생성한다.
        String username = "username";
        String token = JwtFactory.builder()
                .subject(username)
                .build()
                .createToken(jwtProperties);

        // when : 토큰 제공자의 getAuthentication() 메서드를 호출해 인증 객체를 반환 받는다.
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then : 반환받은 인증 객체의 유저 이름을 가져와 given 절에서 설정한 subject인 username과 같은지 확인한다.
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(username);
    }

}