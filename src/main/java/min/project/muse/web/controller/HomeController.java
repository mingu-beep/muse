package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.service.MusicService;
import min.project.muse.web.dto.music.ShowMusicResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MusicService musicService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

//        List<Music> musics = musicService.findAll(); // **** check ****

        List<ShowMusicResponse> musics = musicService.findMusicList(principalDetails);

        model.addAttribute("musics", musics);

        return "home";
    }

//    @GetMapping("/upload")
//    public String uploadPage() {
//        return "upload";
//    }
}
