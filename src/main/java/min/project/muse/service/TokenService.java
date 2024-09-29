//package min.project.muse.service;
//
//import lombok.RequiredArgsConstructor;
//import min.project.muse.config.jwt.JwtTokenProvider;
//import min.project.muse.config.jwt.TokenProperties;
//import min.project.muse.domain.refreshToken.RefreshTokenRepository;
//import min.project.muse.domain.user.User;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//
//@RequiredArgsConstructor
//@Service
//public class TokenService {
//
//    private final TokenProperties tokenProperties;
//
//    private final JwtTokenProvider tokenProvider;
//
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final RefreshTokenService refreshTokenService;
//    private final UserService userService;
//
//    /*
//        전달받은 리프레시 토큰으로 토큰 유효성 검사를 진행하고,
//        유효한 토큰일 때 리프레시 토큰으로 사용자 ID를 찾는다.
//        마지막으로 사용자 ID로 사용자를 찾은 후 토큰 제공자의 generateToken() 메서드를 호출해 새로운 액세스 토큰을 생성한다.
//     */
//    public String createNewAccessToken(String refreshToken) {
//        // 토큰 유효성 검사에 실패하면 예외 발생
//        if (!tokenProvider.validToken(refreshToken)) {
//            throw new IllegalArgumentException("Unexpected token");
//        }
//
//        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
//        User user = userService.findById(userId);
//
//        return tokenProvider.generateToken(user, Duration.ofHours(2));
//    }
//
//    private void saveRefreshToken(Long userId, String refreshToken) {
//
//    }
//
//    public String createRefreshToken(User user) {
//
//        String refreshToken = tokenProvider.generateToken(user, tokenProperties.getREFRESH_TOKEN_DURATION());
//
//        return "";
//    }
//
//
//}
