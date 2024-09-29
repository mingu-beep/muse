//package min.project.muse.config;
//
//import lombok.RequiredArgsConstructor;
//import min.project.muse.config.jwt.JwtAuthenticationFilter;
//import min.project.muse.config.jwt.JwtTokenProvider;
//import min.project.muse.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
//import min.project.muse.config.oauth.OAuth2SuccessHandler;
//import min.project.muse.config.oauth.OAuth2UserCustomService;
//import min.project.muse.domain.refreshToken.RefreshTokenRepository;
//import min.project.muse.service.OAuth2UserService;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class TokenSecurityConfig {
//
//    private final OAuth2UserCustomService oAuth2UserCustomService;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final RefreshTokenRepository refreshTokenRepository;
//    private final OAuth2UserService oAuth2UserService;
//
//    @Bean
//    public WebSecurityCustomizer configure() throws Exception {
//
//        return (web) -> web.ignoring()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
//
//    // for using JWT
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
//
//        return security
//                // REST API 이므로 basic auth 및 csrf 보안을 사용하지 않음
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                // JWT 를 사용하기 때문에 세션을 사용하지 않음
//                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests(request -> {
//                    // 해당 API 요청에 대해서는 모든 요청을 허가
//                    request.requestMatchers(
//                            new AntPathRequestMatcher("**"),
//                            new AntPathRequestMatcher("/login"),
//                            new AntPathRequestMatcher("/signup")
//                    ).permitAll();
//                    // USER 권한이 있는 경우 요청할 수 있음
//                    request.requestMatchers("/auth/test").hasRole("USER");
//                    // 이 밖의 모든 요청에 대해서는 인증을 필요로 한다는 설정
//                    request.anyRequest().authenticated();
//                })
//                .oauth2Login(oauth2 -> oauth2
//                        .loginPage("/login")
//                        .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint.authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository()))
//                        .userInfoEndpoint(userinfoEndpoint -> userinfoEndpoint.userService(oAuth2UserCustomService))
//                        .successHandler(oAuth2SuccessHandler())
//                )
//                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행한다.
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).build();
//    }
//
//    @Bean
//    public OAuth2SuccessHandler oAuth2SuccessHandler() {
//        return new OAuth2SuccessHandler(jwtTokenProvider,
//                refreshTokenRepository,
//                oAuth2AuthorizationRequestBasedOnCookieRepository(),
//                oAuth2UserService
//        );
//    }
//
//    @Bean
//    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
//        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
//    }
//
//    @Bean
//    public TokenAuthenticationFilter tokenAuthenticationFilter() {
//        return new TokenAuthenticationFilter(jwtTokenProvider);
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//            AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//
//        // BCrypt Encoder 사용
//        // DelegatingPasswordEncoder : 여러 암호화 알고리즘을 지원하며, Spring Security의 기본 권장 알고리즘을 사용하여 비밀번호를 인코딩
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//}
