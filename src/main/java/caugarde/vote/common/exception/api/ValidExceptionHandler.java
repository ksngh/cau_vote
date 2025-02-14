package caugarde.vote.common.exception.api;


import java.util.List;
import java.util.stream.Collectors;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResErrorCode;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "ValidExceptionHandler")
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE + 1)
public class ValidExceptionHandler {

    // 요청 본문 유효성 검증 실패 (@Valid, @Validated)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error("Validation error", exception);

        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomApiResponse.ERROR(ResErrorCode.BAD_REQUEST, "Validation failed", errors));
    }

    // 요청 파라미터 유효성 검증 실패 (@RequestParam, @PathVariable)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("Constraint violation", exception);

        List<String> errors = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + " : " + violation.getMessage())
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomApiResponse.ERROR(ResErrorCode.BAD_REQUEST, "Constraint violation", errors));
    }

    // 요청 파라미터 바인딩 실패 (@RequestParam)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException exception) {
        log.error("Bind exception", exception);

        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomApiResponse.ERROR(ResErrorCode.BAD_REQUEST, "Binding failed", errors));
    }

    // 클라이언트가 잘못된 JSON 또는 XML 데이터를 전송
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        log.error("Malformed JSON request", exception);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomApiResponse.ERROR(ResErrorCode.BAD_REQUEST, "Malformed JSON request", null));
    }

    // 필수 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException exception) {
        log.error("Missing request parameter: {}", exception.getParameterName(), exception);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(CustomApiResponse.ERROR(
                        ResErrorCode.BAD_REQUEST,
                        "Missing parameter: " + exception.getParameterName(),
                        null
                ));
    }

}