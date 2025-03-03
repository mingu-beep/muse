package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.domain.user.Role;
import min.project.muse.service.MusicService;
import min.project.muse.web.dto.music.ShowMusicResponse;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MusicService musicService;

    @GetMapping("/")
    public String home() {
        return "redirect:/all";
    }

    // 무드를 선택해서 그 무드만 볼 수 있도록 필터링 기능 추가 요망
    @GetMapping("/all")
    public String all(Model model, @AuthenticationPrincipal PrincipalDetails principal,
                      @RequestParam(name="page", defaultValue = "1") int page) {

        // admin 일 경우 admin
        if(principal != null) {
            if (principal.getUser().getRole().equals(Role.ADMIN)) {
                return "redirect:/admin";
            }
        }

        Pageable pageable = PageRequest.of(page - 1, 4);

        model.addAttribute("menu", "all");
        model.addAttribute("title", "All Musics");

        Page<ShowMusicResponse> musics = musicService.loadMusicList(principal, pageable);
        model.addAttribute("musics", musics);

        model.addAttribute("beforePage", Math.max(page - 1, 1));
        model.addAttribute("nextPage", Math.min(page + 1, musics.getTotalPages()));

        model.addAttribute("startPage", Math.max(page - 4, 1));
        model.addAttribute("endPage", Math.min(page + 4, musics.getTotalPages()));

        return "home";
    }

    @GetMapping("/today")
    public String today(Model model, @AuthenticationPrincipal PrincipalDetails principal,
                        @RequestParam(name="page", defaultValue = "1") int page) {

        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<ShowMusicResponse> musics = musicService.findTodayMusicList(principal, pageable);

        model.addAttribute("menu", "today");
        model.addAttribute("title", "Today Uploaded");
        model.addAttribute("musics", musics);

        model.addAttribute("beforePage", Math.max(page - 1, 1));
        model.addAttribute("nextPage", Math.min(page + 1, musics.getTotalPages()));

        model.addAttribute("startPage", Math.max(page - 4, 1));
        model.addAttribute("endPage", Math.min(page + 4, musics.getTotalPages()));

        return "home";
    }

    @GetMapping("/popular")
    public String popular(Model model, @AuthenticationPrincipal PrincipalDetails principal,
                          @RequestParam(name="page", defaultValue = "1") int page) {

        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<ShowMusicResponse> musics = musicService.findPopularMusicList(principal, pageable);

        model.addAttribute("menu", "popular");
        model.addAttribute("title", "Most Popular");
        model.addAttribute("musics", musics);

        model.addAttribute("beforePage", Math.max(page - 1, 1));
        model.addAttribute("nextPage", Math.min(page + 1, musics.getTotalPages()));

        model.addAttribute("startPage", Math.max(page - 4, 1));
        model.addAttribute("endPage", Math.min(page + 4, musics.getTotalPages()));

        return "home";
    }

}
