package min.project.muse.web.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.likes.Likes;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.domain.user.User;
import min.project.muse.service.UserService;
import min.project.muse.util.SecurityUtil;
import min.project.muse.web.dto.music.ShowMusicResponse;
import min.project.muse.web.dto.user.AddUserRequest;
import min.project.muse.web.dto.user.UpdateUserProfileRequest;
import min.project.muse.web.dto.user.UserDTO;
import min.project.muse.web.dto.user.UserProfileResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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


    @GetMapping("/user/{userId}")
    public String profile(@PathVariable("userId") long userId, @AuthenticationPrincipal PrincipalDetails principal, Model model) {

        log.info("###### user Id : {}", userId);

        /**
         * Profile page 에서 필요한 정보
         *
         * username, email, nickname, musiclist
         * -> 이 정보만 담는 DTO를 생성하는게 좋다.
         */

        User userEntity = userService.findById(userId);

        List<ShowMusicResponse> dtoList = new ArrayList<>();

        boolean owner = principal != null && principal.getUser().getId() == userId;

        for (Music music : userEntity.getMusicList()) {
            dtoList.add(ShowMusicResponse.builder()
                    .user(music.getUser())
                    .id(music.getId())
                    .title(music.getTitle())
                    .artist(music.getArtist())
                    .moods(Arrays.stream(music.getMoods().split(",")).toList())
                    .image(music.getImage())
                    .owner(owner)
                    .build());
        }

        UserProfileResponse dto = UserProfileResponse.builder()
                .profileImage(userEntity.getProfileImage())
                .userId(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .musicList(dtoList)
                .musicCount(dtoList.size())
                .owner(owner)
                .build();

        model.addAttribute("dto", dto);

        return "profile";
    }

    @PostMapping("/user")
    public String signUp(@Valid AddUserRequest request, BindingResult bindingResult, Model model) {

//        AOP로 처리
//        if (bindingResult.hasErrors()) {
//            StringBuilder sb = new StringBuilder();
//
//            bindingResult.getAllErrors().forEach(objectError -> {
//                FieldError field = (FieldError) objectError;
//                String defaultMessage = field.getDefaultMessage();
//
//                log.error("signUp // field : {}", field.getField());
//                log.error("singUp // message : {}", defaultMessage);
//
//                sb.append("field : " + field.getField());
//                sb.append("message : " + defaultMessage);
//            });
//
//            model.addAttribute("errors", sb.toString());
//
//            return "redirect:/signup";
//
//        }

        log.info("######### AddUserRequest {}", request.toString());
        UserDTO userDTO = userService.save(request);

        return "redirect:/";
    }

    @PutMapping("/user/{userId}")
    public String updateProfile(@PathVariable("userId") long userId
            , @Valid UpdateUserProfileRequest data, BindingResult bindingResult) {

//        AOP로 처리
//        if (bindingResult.hasErrors()) {
//            StringBuilder sb = new StringBuilder();
//
//            bindingResult.getAllErrors().forEach(objectError -> {
//                FieldError field = (FieldError) objectError;
//                String message = field.getDefaultMessage();
//
//                log.error("updateProfile // field : {}", field.getField());
//                log.error("updateProfile // message : {}", message);
//
//            });
//        }

        log.info("##### userId : {}", userId);
        log.info("##### data : {}", data.toString());

        User updateUserProfile = userService.updateUserProfile(userId, data);

        log.info("##### updateUserProfile : {}", updateUserProfile.toString());

        return "redirect:/user/" + userId;

    }

    @ResponseBody
    @PostMapping("/auth/test")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }

}
