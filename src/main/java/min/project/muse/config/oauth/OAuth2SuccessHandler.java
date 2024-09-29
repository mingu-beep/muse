//package min.project.muse.config.oauth;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import min.project.muse.config.jwt.JwtTokenProvider;
//import min.project.muse.config.jwt.TokenProperties;
//import min.project.muse.domain.refreshToken.RefreshToken;
//import min.project.muse.domain.refreshToken.RefreshTokenRepository;
//import min.project.muse.domain.user.User;
//import min.project.muse.service.OAuth2UserService;
//import min.project.muse.util.CookieUtil;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import java.io.IOException;
//import java.time.Duration;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    public static final String REDIRECT_PATH = "/";
//
//    private TokenProperties tokenProperties = new TokenProperties();
//
//    private final JwtTokenProvider tokenProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final OAuth2AuthorizationRequestBasedOnCookieRepository auth2AuthorizationRequestRepository;
//    private final OAuth2UserService oAuth2UserService;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//
//        User user = oAuth2UserService.findByUsername((String) oAuth2User.getAttributes().get("username"));
//
//        // 리프레시 토큰 생성 -> 저장 -> 쿠키에 저장
//        String refreshToken = tokenProvider.generateToken(user, tokenProperties.getREFRESH_TOKEN_DURATION());
//        saveRefreshToken(user.getId(), refreshToken);
//        addRefreshTokenToCookie(request, response, refreshToken);
//
//        // 액세스 토큰 생성 -> 패스에 액세스 토큰 추가
//        String accessToken = tokenProvider.generateToken(user, tokenProperties.getACCESS_TOKEN_DURATION());
//        String targetUrl = getTargetUrl(accessToken);
//
//        // 인증 관련 설정값, 쿠키 제거
//        clearAuthenticationAttributes(request, response);
//
//        // 리다이렉트
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
//
//    }
//
//    // 생성된 리프레시 토큰을 전달받아 데이터베이스에 저장
//    private void saveRefreshToken(Long userId, String newRefreshToken) {
//        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
//                .map(entity -> entity.update(newRefreshToken))
//                .orElse(new RefreshToken(userId, newRefreshToken));
//
//        refreshTokenRepository.save(refreshToken);
//    }
//
//    // 생성된 리프레시 토큰을 쿠키에 저장
//    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
//        int cookieMaxAge = (int) tokenProperties.getREFRESH_TOKEN_DURATION().toSeconds();
//        CookieUtil.deleteCookie(request, response, tokenProperties.getREFRESH_TOKEN_COOKIE_NAME());
//        CookieUtil.addCookie(response, tokenProperties.getREFRESH_TOKEN_COOKIE_NAME(), refreshToken, cookieMaxAge);
//    }
//
//    // 인증 관련 설정값, 쿠키 제거
//    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
//        super.clearAuthenticationAttributes(request);
//        auth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
//    }
//
//    // 액세스 토큰을 패스에 추가
//    private String getTargetUrl(String token) {
//        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
//                .queryParam("token", token)
//                .build()
//                .toUriString();
//    }
//}
