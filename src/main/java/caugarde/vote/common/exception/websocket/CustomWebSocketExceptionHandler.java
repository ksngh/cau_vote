package caugarde.vote.common.exception.websocket;

import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.CustomWebSocketResponse;
import caugarde.vote.common.response.ResCode;
import caugarde.vote.common.response.ResErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.security.Principal;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class CustomWebSocketExceptionHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageExceptionHandler(CustomWebSocketException.class)
    public void handleCustomWebSocketException(CustomWebSocketException ex, Principal principal) {
        String username = (principal != null) ? principal.getName() : "UNKNOWN_USER";

        log.warn("WebSocket 예외 발생: 사용자={} 메시지={}", username, ex.getMessage());

        ResCode errorCode = ex.getErrorCode();
        // 예외 응답 객체 생성
        CustomWebSocketResponse errorResponse = new CustomWebSocketResponse(
                errorCode,
                ex.getMessage()
        );

        // 클라이언트에게 메시지 전송
        if (principal != null) {
            messagingTemplate.convertAndSendToUser(principal.getName(), "topic/errors", errorResponse);
        }
    }

    @MessageExceptionHandler(Exception.class)
    public void handleGeneralException(Exception ex, Principal principal, HttpServletResponse response) {
        String username = (principal != null) ? principal.getName() : "UNKNOWN_USER";

        log.error("WebSocket 내부 서버 오류: 사용자={} 예외={}", username, ex.getClass().getSimpleName(), ex);

        // 예외 응답 객체 생성
        CustomWebSocketResponse errorResponse = new CustomWebSocketResponse(
                ResErrorCode.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );

        if (principal != null) {
            messagingTemplate.convertAndSendToUser(principal.getName(), "/topic/errors", errorResponse);
        }
    }
}
