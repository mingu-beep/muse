package min.project.muse.domain.handler.aop;

import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.handler.ex.CustomValidationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@Aspect // AOP 처리를 위한 애노테이션
public class ValidationAdvice {

    @Pointcut("execution(* min.project.muse.web.controller.*Controller.*(..))")
    public void allController() {
    }

    @Around("allController()")
    public Object doValidation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info("!!!! aop check!");
        Object[] args = proceedingJoinPoint.getArgs();

        for (Object arg : args) {

            if (arg instanceof BindingResult bindingResult) {

                if (bindingResult.hasErrors()) {
                    Map<String, String> errorMap = new ConcurrentHashMap<>();

                    for (FieldError fieldError : bindingResult.getFieldErrors()) {

                        log.error("!!!! error {} {}", fieldError.getField(), fieldError.getDefaultMessage());
                        errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    }
                    throw new CustomValidationException("Validation Fail", errorMap);
                }
            }
        }

        return proceedingJoinPoint.proceed();
    }
}
