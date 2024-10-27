package caugarde.vote.exception;

import
caugarde.vote.model.dto.response.MessageResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.ok(new MessageResponseDTO("회원 정보가 없습니다."));
    }

    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.ok(new MessageResponseDTO("잘못된 접근입니다."));
    }

}
