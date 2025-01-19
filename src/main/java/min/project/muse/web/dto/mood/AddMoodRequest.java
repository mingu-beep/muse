package min.project.muse.web.dto.mood;

import lombok.*;
import min.project.muse.domain.mood.Mood;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AddMoodRequest {

    private String label;
    private String script;

    public Mood toEntity() {
        return Mood.builder()
                .label(label)
                .script(script)
                .build();
    }
}
