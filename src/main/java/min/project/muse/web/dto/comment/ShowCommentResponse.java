package min.project.muse.web.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowCommentResponse {

    private long id;
    private String content;
    private LocalDateTime createDate;

    private long userId;
    private String username;

    private long musicId;

}
