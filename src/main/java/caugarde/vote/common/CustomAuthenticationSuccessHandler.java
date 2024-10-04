package caugarde.vote.common;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.repository.StudentRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
        // 인증 성공 시 수행할 로직
        // 예: 사용자 정보를 로그에 기록하거나, 특정 URL로 리다이렉트

        CustomOAuthUser user = (CustomOAuthUser) authentication.getPrincipal();

        if (user.getIsSignedIn()) {
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/student/signup");
        }

    }
}
