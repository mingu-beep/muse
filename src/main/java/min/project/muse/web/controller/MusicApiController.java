package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.service.LikesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/musics")
@RestController
public class MusicApiController {

    private final LikesService likesService;

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
