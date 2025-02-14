package caugarde.vote.common.exception.websocket;

import caugarde.vote.common.response.ResCode;
import lombok.Getter;

@Getter
public class CustomWebSocketException extends RuntimeException {
    private final ResCode errorCode;
    private final String errorDescription;

    public CustomWebSocketException(ResCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public CustomWebSocketException(ResCode errorCode, String errorDescription) {
        super(errorDescription);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
