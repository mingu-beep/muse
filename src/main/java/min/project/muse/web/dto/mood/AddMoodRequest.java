package min.project.muse.web.dto.mood;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import min.project.muse.domain.mood.Mood;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
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
