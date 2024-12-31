package min.project.muse.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String mainPage () {
        return "/admin/main";
    }

    @GetMapping("/users")
    public String userListPage() {
        return "/admin/users";
    }

    @GetMapping("/musics")
    public String musicListPage() {
        return "/admin/musics";
    }


}
