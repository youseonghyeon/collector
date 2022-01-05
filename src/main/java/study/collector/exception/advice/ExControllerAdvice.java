package study.collector.exception.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.collector.exception.customexception.BookMarkException;
import study.collector.exception.customexception.ScheduleException;
import study.collector.exception.customexception.UserException;
import study.collector.exception.responsedto.TestErrorResult;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "study.collector")
public class ExControllerAdvice {

    @ExceptionHandler(UserException.class)
    public TestErrorResult userExHandler(UserException ex) {
        log.error("UserException={}", ex);
        return new TestErrorResult("EX", ex.getMessage());
    }

    @ExceptionHandler(BookMarkException.class)
    public TestErrorResult bookmarkExHandler(BookMarkException ex) {
        log.error("BookMarkException={}", ex);
        return new TestErrorResult("EX", ex.getMessage());

    }

    @ExceptionHandler(ScheduleException.class)
    public TestErrorResult scheduleExHandler(ScheduleException ex) {
        log.error("BookMarkException={}", ex);
        return new TestErrorResult("EX", ex.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity overallExHandler(Exception e) {
        log.error("Exception={}", e);
        Map<String, String> response = new HashMap<>();
        response.put("message", e.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
