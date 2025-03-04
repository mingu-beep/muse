package min.project.muse.config.handler;

import lombok.extern.slf4j.Slf4j;
import min.project.muse.config.handler.ex.CustomValidationException;
import min.project.muse.util.Script;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String exampleExceptionHandler(CustomValidationException e) {
        return e.getDetails() == null ? Script.back(e.getMsg()) : Script.back(e.getDetails().toString());
    }
}
