package min.project.muse.service;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.comment.Comment;
import min.project.muse.domain.comment.CommentRepository;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.User;
import min.project.muse.domain.user.UserRepository;
import min.project.muse.web.dto.comment.SaveCommentRequest;
import min.project.muse.web.dto.comment.ShowCommentResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ShowCommentResponse saveComment(SaveCommentRequest commentDto, long userId) {

        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());

        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        });
        comment.setUser(user);

        Music music = new Music();
        music.setId(commentDto.getMusicId());
        comment.setMusic(music);

        Comment commentEntity = commentRepository.save(comment);

        return ShowCommentResponse.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .createDate(commentEntity.getCreateDate())
                .userId(commentEntity.getUser().getId())
                .username(commentEntity.getUser().getUsername())
                .musicId(commentEntity.getMusic().getId())
                .build();

    }

    @Transactional
    public void deleteComment(long commentId) {
        commentRepository.deleteById(commentId);
    }
}
