package min.project.muse.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class MultipartFileUtil {

    public static String saveImage(String uploadPath, MultipartFile image)    {

        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + image.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadPath + filename);

        try {
            Files.write(imageFilePath, image.getBytes());
        } catch (IOException e) {
            //
        }

        return filename;

    }
}
