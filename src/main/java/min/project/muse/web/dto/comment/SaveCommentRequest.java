package min.project.muse.web.dto.comment;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SaveCommentRequest {

    private long musicId;
    private String content;

}
