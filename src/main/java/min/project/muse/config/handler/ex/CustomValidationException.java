package min.project.muse.config.handler.ex;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private Map<String, String> details;

    public CustomValidationException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomValidationException(String msg, Map<String, String> details) {
        this.msg = msg;
        this.details = details;
    }

}
