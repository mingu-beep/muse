package min.project.muse.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.comment.Comment;
import min.project.muse.domain.comment.CommentRepository;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.service.CommentService;
import min.project.muse.web.dto.comment.SaveCommentRequest;
import min.project.muse.web.dto.comment.ShowCommentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> saveComments(@RequestBody SaveCommentRequest commentDto,
                                          @AuthenticationPrincipal PrincipalDetails principal) {

        ShowCommentResponse responseDto = commentService.saveComment(commentDto, principal.getUserId());

        return ResponseEntity.ok(responseDto);

    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable("commentId") long commentId, @RequestBody SaveCommentRequest commentDto) {

        log.info("comment update !!!!!");

        ShowCommentResponse dto = commentService.updateComment(commentId, commentDto);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComments(@PathVariable("commentId") long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.ok().build();
    }
}
