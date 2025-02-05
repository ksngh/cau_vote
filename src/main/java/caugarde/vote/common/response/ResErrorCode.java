package caugarde.vote.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResErrorCode implements ResCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), 40000, "Invalid Request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), 40100, "Unauthorized"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), 40101, "Expired Token"),
    FORBIDDEN(HttpStatus.FORBIDDEN.value(), 40300, "Access Denied"),
    PENDING_AUTHORIZED(HttpStatus.UNAUTHORIZED.value(), 40301, "Pending User"),
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), 40400, "Not Found"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), 40500, "Method Not Allowed"),
    CONFLICT(HttpStatus.CONFLICT.value(), 40900, "Conflict Occurred"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), 50000, "Internal Server Error"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), 50300, "Service Unavailable"),
    API_CALL_FAILED(HttpStatus.SERVICE_UNAVAILABLE.value(), 50301, "API Call Failed");

    private final Integer httpStatusCode;
    private final Integer code;
    private final String message;
}
