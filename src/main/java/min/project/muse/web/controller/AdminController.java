package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.User;
import min.project.muse.service.MusicService;
import min.project.muse.service.UserService;
import min.project.muse.web.dto.music.ShowBriefMusicInfoResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final MusicService musicService;

    @GetMapping
    public String mainPage (Model model) {

        model.addAttribute("tableData", musicService.findByDate());
        model.addAttribute("chartData", musicService.getChartData());

        return "/admin/main";
    }

    @GetMapping("/users")
    public String userListPage(Model model) {

        model.addAttribute("users", userService.findAll());

        return "/admin/users";
    }

    @GetMapping("/musics")
    public String musicListPage(Model model) {

        model.addAttribute("musics", musicService.getBriefInfo());
        return "/admin/musics";
    }

    @GetMapping("/reports")
    public String reportPage(Model model) {
        Map<String, Integer> res = musicService.countMusicByMood();
        Map<String, List<ShowBriefMusicInfoResponse>> tableData = musicService.findByMoodAll();
        model.addAttribute("data", res);
        model.addAttribute("tableData", tableData);

        return "/admin/reports";
    }


}
