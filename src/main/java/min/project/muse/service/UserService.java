package min.project.muse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.config.jwt.JwtTokenProvider;
import min.project.muse.domain.JwtToken;
import min.project.muse.domain.user.User;
import min.project.muse.domain.user.UserRepository;
import min.project.muse.web.dto.AddUserRequest;
import min.project.muse.web.dto.LoginRequest;
import min.project.muse.web.dto.UserDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;

    @Transactional
    public JwtToken login(LoginRequest loginRequest) {
        // 1. username, password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false

        log.info("##### trace1 #######");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        log.info("##### trace2 #######");

        // 2. 실제 검증, authenticate() 메서드를 통해 요청된 User 에 대한 검증 진행

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


        log.info("##### trace3 #######");

        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadByUsername 메서드 실행
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);

    }

    public UserDTO save(AddUserRequest dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디 입니다.");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        List<String> roles = new ArrayList<>();
        roles.add("USER");

        return UserDTO.toDTO(userRepository.save(
                dto.toEntity(encodedPassword, roles)
        ));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }
}
