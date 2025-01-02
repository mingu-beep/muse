package min.project.muse.web.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SaveCommentRequest {

    private long musicId;

    @NotBlank
    private String content;

}
