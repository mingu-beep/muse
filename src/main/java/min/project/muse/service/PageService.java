package min.project.muse.service;

import min.project.muse.web.dto.PageDTO;
import org.springframework.stereotype.Service;

@Service
public class PageService {

    public PageDTO calcPage(int now, int totalNum) {

        PageDTO pageDTO = new PageDTO();

        pageDTO.setNow(now);
        pageDTO.setMax(totalNum / 10 + 1);

        pageDTO.setStart(Math.max(now - 4, 1));
        pageDTO.setEnd(Math.min(now + 4, pageDTO.getMax()));

        return pageDTO;
    }
}
