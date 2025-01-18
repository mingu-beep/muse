package min.project.muse.service;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.mood.Mood;
import min.project.muse.domain.mood.MoodRepository;
import min.project.muse.web.dto.mood.AddMoodRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MoodService {

    private final MoodRepository moodRepository;

    // CRUD
    public void addMood(AddMoodRequest req) {
        moodRepository.save(req.toEntity());
    }

    public List<Mood> findAll() {
        return moodRepository.findAll();
    }

    public void deleteMood(long id) {
        moodRepository.deleteById(id);
    }

}
