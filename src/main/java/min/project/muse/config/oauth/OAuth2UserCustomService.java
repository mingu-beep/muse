//package min.project.muse.config.oauth;
//
//import lombok.RequiredArgsConstructor;
//import min.project.muse.domain.user.User;
//import min.project.muse.domain.user.UserRepository;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@RequiredArgsConstructor
//@Service
//public class OAuth2UserCustomService  extends DefaultOAuth2UserService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        // 요청을 바탕으로 유저 정보를 담은 객체 반환
//        OAuth2User user = super.loadUser(userRequest);
//        saveOrUpdate(user);
//        return user;
//
//    }
//
//    // 유저가 있으면 업데이트, 없으면 유저 생성
//    private User saveOrUpdate(OAuth2User oAuth2User) {
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//        String nickname = (String) attributes.get("nickname");
//        String username = (String) attributes.get("username");
//        User user = userRepository.findByUsername(username)
//                .map(entity -> entity.update(nickname))
//                .orElse(User.builder()
//                        .username(username)
//                        .nickname(nickname)
//                        .build());
//
//        return userRepository.save(user);
//    }
//}
