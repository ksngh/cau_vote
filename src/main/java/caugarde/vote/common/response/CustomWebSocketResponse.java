package caugarde.vote.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CustomWebSocketResponse {

    private Integer code;
    private String message;

    public CustomWebSocketResponse(ResCode resCode, String message) {
        this.code = resCode.getCode();
        this.message = message;
    }

}
