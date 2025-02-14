package caugarde.vote.common.exception.api;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "CustomApiExceptionHandler")
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class CustomApiExceptionHandler {

    @ExceptionHandler(value = CustomApiException.class)
    public ResponseEntity<CustomApiResponse<Void>> apiException(CustomApiException customApiException) {
        log.error("Custom API Exception", customApiException);

        ResCode errorCode = customApiException.getErrorCode();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(CustomApiResponse.ERROR(errorCode, customApiException.getErrorDescription()));
    }

}
