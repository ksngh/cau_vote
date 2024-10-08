package caugarde.vote.exception;

import caugarde.vote.model.dto.response.MessageResponseDTO;
import caugarde.vote.model.enums.SuccessMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.ok(new MessageResponseDTO("회원 정보가 없습니다."));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        // 로그를 남기거나 추가적인 처리를 할 수 있습니다.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 후 이용 가능합니다.");
    }

    // 다른 예외 처리기 추가 가능
}
