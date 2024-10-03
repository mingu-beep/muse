package min.project.muse.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.net.MalformedURLException;

@Slf4j
@ResponseBody
@Controller
@RequestMapping("/images")
public class ImageApiController {

    @GetMapping("/{filename}")
    public Resource showImage(@PathVariable("filename") String filename) throws MalformedURLException {

        log.info("filename : {}", filename);

        File file = new File("C://dev/upload/muse/" + filename);
        return new UrlResource("file:" + file.getAbsolutePath());
    }

}
