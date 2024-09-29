//package min.project.muse.config;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import min.project.muse.config.jwt.JwtTokenProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class TokenAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtTokenProvider jwtTokenProvider;
////    private final TokenProvider tokenProvider;
//    private final static String HEADER_AUTHORIZATION = "Authorization";
//    private final static String TOKEN_PREFIX = "BEARER ";
//
//
//    /*
//        요청이 오면 헤더값을 비교해서 토큰이 있는지 확인하고 유효 토큰이라면 시큐리티 콘텍스트 홀더에 인증 정보를 저장한다.
//        시큐리티 컨텍스트 : 인증 객체가 저장되는 보관소
//            - 인증 정보가 필요할 때 인증 객체를 꺼내어 사용할 수 있게함.
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        // 요청 헤더의 Authorization 키의 값 조회
//        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
//        // 가져온 값에서 접두사 제거
//        String token = getAccessToken(authorizationHeader);
//        // 가져온 토큰이 유효한지 확인하고, 유효한 떄는 인증 정보 설정
//        if (token != null && jwtTokenProvider.validToken(token)) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        filterChain.doFilter(request, response);
//
//    }
//
//    private String getAccessToken(String authorizationHeader) {
//        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
//            return authorizationHeader.substring(TOKEN_PREFIX.length());
//        }
//
//        return null;
//    }
//}
