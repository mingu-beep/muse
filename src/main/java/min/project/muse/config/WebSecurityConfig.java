package min.project.muse.config;

import lombok.RequiredArgsConstructor;
import min.project.muse.service.UserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                // 특정 요청과 일치하는 url에 대한 액세스를 설정
//                .requestMatchers(new AntPathRequestMatcher("/static/**")); // static 리소스에 대해 비활성화
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth //  특정 경로에 대한 인증 인가 설정
                        // 특정 요청과 일치하는 url에 대한 액세스를 설정
                        .requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll() // 누구나 접근이 가능하게 설정한다.
                        .anyRequest().authenticated()) // 위에 설정한 url이외에 요청에 대해서는 인증이 성공한 상태에서 접근 가능
                .formLogin(formLogin -> formLogin // 폼 기반 로그인 설정
                        .loginPage("/login") // 로그인 페이지의 경로를 설정
                        .defaultSuccessUrl("/")) // 로그인이 성공했을 때에 이동할 경로
                .logout(logout -> logout // 로그아웃 설정
                        .logoutSuccessUrl("/login") // 로그아웃이 성공했을 때 이동할 경로를 설정
                        .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정한다.
                )
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .build();
    }

    // 인증 관리자 관련 설정
    // 사용자 정보를 가져올 서비스를 재정의하거나 인증 방법, 예를 들어 LDAP, JDBC 기반 인증 등을 설정할 때 사용
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService); // 사용자 정보 서비스 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder); // 비밀번호를 암호화하기 위한 인코더를 설정한다.
        return new ProviderManager(authProvider);
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
