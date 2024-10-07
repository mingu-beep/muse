package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.service.MusicService;
import min.project.muse.web.dto.music.AddMusicRequest;
import min.project.muse.web.dto.music.MusicResponse;
import min.project.muse.web.dto.music.UpdateMusicRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    // Create
    @PostMapping("/musics")
    public String addMusic(@AuthenticationPrincipal PrincipalDetails principal, AddMusicRequest request) {

        Music saved = musicService.save(request, principal.getUser());

        log.info("MUSIC // image : {}", saved.getImage());

        // 요청한 자원이 성공적으로 생성되었으며 저장한 음악 정보를 응답 객체에 담아 전송
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(saved);
        return "redirect:/";
    }

    // Delete
    @ResponseBody
    @DeleteMapping("/musics/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable("id") long musicId) {

        log.info("%%%%%%% delete Music {}", musicId);
        musicService.deleteById(musicId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/musics/{id}")
    public String updatePage(@PathVariable("id") long id, Model model) {
        Music music = musicService.findById(id);

        model.addAttribute("music", music);
        return "updateMusic";
    }

    // Update
    @PutMapping("/musics/{id}")
    public String updateMusic (@PathVariable("id") long musicId, UpdateMusicRequest updateDto) {

        musicService.update(musicId, updateDto);
        return "redirect:/";
    }

    @GetMapping("/musics")
    public ResponseEntity<List<MusicResponse>> findAllMusics() {
        List<MusicResponse> musics = musicService.findAll()
                .stream()
                .map(MusicResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(musics);
    }

}
