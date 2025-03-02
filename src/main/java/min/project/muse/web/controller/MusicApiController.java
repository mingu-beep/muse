package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.service.LikesService;
import min.project.muse.service.MusicService;
import min.project.muse.web.dto.music.ShowMusicResponse;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/musics")
@RestController
public class MusicApiController {

    private final MusicService musicService;
    private final LikesService likesService;

    @GetMapping
    public ResponseEntity<?> loadMusicList(@AuthenticationPrincipal PrincipalDetails principal, @PageableDefault(value = 4) Pageable pageable) {

        List<ShowMusicResponse> body = musicService.loadMusicList(principal, pageable);


        // Music -> User -> Music 으로 순환참조 발생
        return ResponseEntity.ok().body(body);
    }

    // add
    @PostMapping("/{musicId}/likes")
    public ResponseEntity<?> like (@PathVariable("musicId") long musicId, @AuthenticationPrincipal PrincipalDetails principal) {

        likesService.like(musicId, principal.getUser());

        return ResponseEntity.ok().build();
    }

    // delete
    @DeleteMapping("/{musicId}/likes")
    public ResponseEntity<?> unLikes (@PathVariable("musicId") long musicId, @AuthenticationPrincipal PrincipalDetails principal) {

        likesService.unlike(musicId, principal.getUser());

        return ResponseEntity.ok().build();

    }

}
