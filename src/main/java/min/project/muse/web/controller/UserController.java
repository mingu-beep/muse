package min.project.muse.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.JwtToken;
import min.project.muse.service.UserService;
import min.project.muse.util.SecurityUtil;
import min.project.muse.web.dto.AddUserRequest;
import min.project.muse.web.dto.LoginRequest;
import min.project.muse.web.dto.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public String signUp(AddUserRequest request) {

        log.info("######### {}", request.toString());
        UserDTO userDTO = userService.save(request);

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/auth/test")
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }
}
