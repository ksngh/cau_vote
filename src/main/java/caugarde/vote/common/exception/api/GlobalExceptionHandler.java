package caugarde.vote.common.exception.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j(topic = "GlobalExceptionHandler")
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomApiResponse<Void>> handleNoResourceFoundException(
            NoResourceFoundException exception) {

        log.warn("Static resource not found: {}", exception.getResourcePath());

        return ResponseEntity
                .status(404)
                .body(CustomApiResponse.ERROR(ResErrorCode.NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomApiResponse<Void>> handleGenericException(Exception exception) {

        log.error("Unhandled exception occurred: {}", exception.getMessage(), exception);

        return ResponseEntity
                .status(500)
                .body(CustomApiResponse.ERROR(ResErrorCode.INTERNAL_SERVER_ERROR, exception.getMessage()));
    }
}
