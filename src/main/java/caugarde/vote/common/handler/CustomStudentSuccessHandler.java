package caugarde.vote.common.handler;

import caugarde.vote.common.exception.api.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.v2.interfaces.StudentRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomStudentSuccessHandler implements AuthenticationSuccessHandler {

    private final StudentRepository studentRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuthUser oAuth2User = (CustomOAuthUser) authentication.getPrincipal();

        String email = oAuth2User.getName();
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new CustomApiException(ResErrorCode.NOT_FOUND,"해당하는 유저 정보가 없습니다."));

        if (student.getAuthorities().contains(Role.PENDING_USER)) {
            response.sendRedirect("/sign-up");
        } else {
            response.sendRedirect("/");
        }
    }
}
