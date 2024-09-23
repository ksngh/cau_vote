package caugarde.vote.common;

import caugarde.vote.model.constant.CustomOAuthUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;

public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 로그인 성공 후 클라이언트에게 JavaScript alert를 보내기 위한 응답 설정
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // JavaScript alert 코드
        out.println("<script>alert('관리자 계정으로 로그인하였습니다.');</script>");
        out.println("<script>window.location.href = '/';</script>"); // 로그인 후 리다이렉트할 URL
        out.flush();
    }
}
