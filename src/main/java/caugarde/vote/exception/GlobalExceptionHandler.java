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

    // 다른 예외 처리기 추가 가능
}
