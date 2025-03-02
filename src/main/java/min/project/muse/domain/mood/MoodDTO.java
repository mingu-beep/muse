package min.project.muse.domain.mood;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MoodDTO {

    long id;
    String label;
    String script;

}
