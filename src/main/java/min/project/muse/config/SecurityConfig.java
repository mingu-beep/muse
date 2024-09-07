package min.project.muse.config;

import lombok.RequiredArgsConstructor;
import min.project.muse.config.jwt.JwtAuthenticationFilter;
import min.project.muse.config.jwt.JwtTokenProvider;
import min.project.muse.service.CustomUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public WebSecurityCustomizer configure() throws Exception {

        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        return security
                // REST API 이므로 basic auth 및 csrf 보안을 사용하지 않음
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // JWT 를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                    // 해당 API 요청에 대해서는 모든 요청을 허가
                    request.requestMatchers(
                            new AntPathRequestMatcher("**"),
                            new AntPathRequestMatcher("/login"),
                            new AntPathRequestMatcher("/signup")
                    ).permitAll();
                    // USER 권한이 있는 경우 요청할 수 있음
                    request.requestMatchers("/auth/test").hasRole("USER");
                    // 이 밖의 모든 요청에 대해서는 인증을 필요로 한다는 설정
                    request.anyRequest().authenticated();
                })
                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행한다.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        // BCrypt Encoder 사용
        // DelegatingPasswordEncoder : 여러 암호화 알고리즘을 지원하며, Spring Security의 기본 권장 알고리즘을 사용하여 비밀번호를 인코딩
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
