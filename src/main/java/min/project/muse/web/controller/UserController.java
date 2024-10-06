package min.project.muse.web.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.domain.user.User;
import min.project.muse.service.MusicService;
import min.project.muse.service.UserService;
import min.project.muse.util.SecurityUtil;
import min.project.muse.web.dto.user.AddUserRequest;
import min.project.muse.web.dto.user.UserDTO;
import min.project.muse.web.dto.user.UserProfileResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

//    @PostMapping("/user")
//    public String signup(AddUserRequest request) {
//        userService.save(request);
//        return "redirect:/login";
//    }


// for JWT
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/login";
//    }

//    @PostMapping("/login")
//    public String login(HttpServletRequest request, HttpServletResponse response, LoginRequest loginRequest) {
//
//        JwtToken jwtToken = userService.login(loginRequest);
//
//        return "redirect:/";
//    }


    @GetMapping("/user")
    public String profile(@RequestParam("userId") long userId, Model model) {

        log.info("###### user Id : {}", userId);

        /**
         * Profile page 에서 필요한 정보
         *
         * username, email, nickname, musiclist
         * -> 이 정보만 담는 DTO를 생성하는게 좋다.
         */

        User userEntity = userService.findById(userId);

        UserProfileResponse dto = UserProfileResponse.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .musicList(userEntity.getMusicList())
                .build();

        model.addAttribute("dto", dto);

        return "profile";
    }

    @PostMapping("/user")
    public String signUp(AddUserRequest request) {

        log.info("######### AddUserRequest {}", request.toString());
        UserDTO userDTO = userService.save(request);

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/auth/test")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }
}
