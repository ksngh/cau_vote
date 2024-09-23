package caugarde.vote.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class AdminAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // JavaScript alert 코드
        out.println("<script>alert('아이디 또는 비밀번호가 틀렸습니다.');</script>");
        out.println("<script>window.location.href = '/admin/login';</script>"); // 로그인 후 리다이렉트할 URL
        out.flush();
    }
}
