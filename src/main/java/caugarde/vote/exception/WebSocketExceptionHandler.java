package caugarde.vote.exception;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.NoSuchElementException;

@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(NullPointerException.class)
    @SendToUser("/topic/errors")
    public String handleNullPointerException(NullPointerException ex) {
        return "로그인 후 이용해주시기 바랍니다.";
    }

    @MessageExceptionHandler(NoSuchElementException.class)
    @SendToUser("/topic/errors")
    public String handleNoSuchElementException(NoSuchElementException ex) {
        return "투표 내역이 없습니다.";
    }

    @MessageExceptionHandler(IllegalArgumentException.class)
    @SendToUser("/topic/errors")
    public String handleIllegalArgumentException(IllegalArgumentException ex) {
        return "투표 인원이 초과되었습니다.";
    }

}
