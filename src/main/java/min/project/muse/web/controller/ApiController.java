package min.project.muse.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.Track;
import min.project.muse.service.LastApiService;
import min.project.muse.service.PageService;
import min.project.muse.util.ApiResponseParser;
import min.project.muse.web.dto.PageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ApiController {

    private final LastApiService lastApiService;
    private final PageService pageService;

    @GetMapping("/api/last")
    public String get(@RequestParam("track") String track, @RequestParam("page") int page, Model model) {

        log.info("track = {}, page = {}", track, page);
        String response = lastApiService.get(track, page);

        List<Track> trackList = new ArrayList<>();
        int totalNumber = ApiResponseParser.getTracks(response, trackList);
        PageDTO pageDTO = pageService.calcPage(page, totalNumber);

        model.addAttribute("track", track);
        model.addAttribute("trackList", trackList);
        model.addAttribute("pageDTO", pageDTO);

        return "popup";
    }
}
