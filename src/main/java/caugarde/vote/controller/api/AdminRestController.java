package caugarde.vote.controller.api;

import caugarde.vote.model.dto.request.AdminRequestDTO;
import caugarde.vote.model.dto.response.MessageResponseDTO;
import caugarde.vote.model.enums.SuccessMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRestController {

}
