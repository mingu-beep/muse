package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.service.MusicService;
import min.project.muse.web.dto.AddMusicRequest;
import min.project.muse.web.dto.MusicResponse;
import min.project.muse.web.dto.UpdateMusicRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @PostMapping("/musics")
    public String addMusic(@AuthenticationPrincipal PrincipalDetails principal, AddMusicRequest request) {

        request.setWriter(principal.getUsername());
        Music saved = musicService.save(request);


        // 요청한 자원이 성공적으로 생성되었으며 저장한 음악 정보를 응답 객체에 담아 전송
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(saved);
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

    @GetMapping("/musics/{id}")
    public ResponseEntity<MusicResponse> findMusic(@PathVariable("id") long id) {
        Music music = musicService.findById(id);

        return ResponseEntity.ok().body(new MusicResponse(music));
    }

    @DeleteMapping("/musics/{id}")
    public ResponseEntity<Void> deleteMusic(@PathVariable("id") long id) {
        musicService.deleteById(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/musics/{id}")
    public ResponseEntity<Music> updateMusic(@PathVariable("id") long id, @RequestBody UpdateMusicRequest request) {

        Music updateMusic = musicService.update(id, request);

        return ResponseEntity.ok()
                .body(updateMusic);
    }
}
