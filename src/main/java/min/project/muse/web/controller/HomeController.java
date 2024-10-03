package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.music.Music;
import min.project.muse.service.MusicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MusicService musicService;

    @GetMapping("/")
    public String home(Model model) {

        List<Music> musics = musicService.findAll();

        model.addAttribute("musics", musics);

        return "home";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }
}
