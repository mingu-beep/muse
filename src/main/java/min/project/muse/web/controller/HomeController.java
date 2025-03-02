package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.domain.user.Role;
import min.project.muse.service.MusicService;
import min.project.muse.web.dto.music.ShowBriefMusicInfoResponse;
import min.project.muse.web.dto.music.ShowMusicResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MusicService musicService;

    // 무드를 선택해서 그 무드만 볼 수 있도록 필터링 기능 추가 요망
    @GetMapping("/")
    public String all(Model model, @AuthenticationPrincipal PrincipalDetails principal) {


        model.addAttribute("menu", "home");
        model.addAttribute("title", "All Musics");
        model.addAttribute("musics", musicService.findMusicList(principal));

        return "home";
    }

    @GetMapping("/today")
    public String home(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        // admin 일 경우 admin 
        if(principalDetails != null) {
            if (principalDetails.getUser().getRole().equals(Role.ADMIN)) {
                return "redirect:/admin";
            }
        }

        List<ShowMusicResponse> musics = musicService.findTodayMusicList(principalDetails);

        model.addAttribute("menu", "today");
        model.addAttribute("title", "Today Uploaded");
        model.addAttribute("musics", musics);

        return "home";
    }

    @GetMapping("/popular")
    public String popular(Model model, @AuthenticationPrincipal PrincipalDetails principal) {

        List<ShowMusicResponse> popularMusicList = musicService.findPopularMusicList(principal);

        model.addAttribute("menu", "popular");
        model.addAttribute("title", "Most Popular");
        model.addAttribute("musics", popularMusicList);

        return "home";
    }

}
