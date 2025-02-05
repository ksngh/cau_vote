package caugarde.vote.common.filter;

import caugarde.vote.common.exception.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import caugarde.vote.common.util.CookieUtil;
import caugarde.vote.common.util.JwtUtil;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.Role;
import caugarde.vote.service.v2.interfaces.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDED_PATHS = List.of("/", "/login");
    private final StudentService studentService;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    private boolean isExcludedFromFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return EXCLUDED_PATHS.stream().anyMatch(requestURI::equals);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (isExcludedFromFilter(request)) {
            filterChain.doFilter(request, response); // 공개 경로는 필터 통과
            return;
        }

        try {
            String token = cookieUtil.getAuthCookie(request);
            validateAuthorizationCookie(token);
            validateToken(token);
            String email = jwtUtil.getEmail(token);
            authenticateUser(email, request);
            filterChain.doFilter(request, response);
        } catch (CustomApiException e) {
            handleException(response, e);
            log.error(e.getMessage(), e);
        }
    }

    private void validateAuthorizationCookie(String token) {
        if (token == null) {
            throw new CustomApiException(ResErrorCode.UNAUTHORIZED, "인증되지 않은 사용자입니다.");
        }
    }

    private void validateToken(String token) {
        if (jwtUtil.isTokenExpired(token)) {
            throw new CustomApiException(ResErrorCode.EXPIRED_TOKEN, "유효하지 않은 토큰입니다.");
        }
    }

    private void authenticateUser(String email, HttpServletRequest request) {
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Student student = studentService.getByEmail(email);
            validatePending(student);
            CustomOAuthUser oAuthUser = new CustomOAuthUser(student);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(oAuthUser, null, oAuthUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private void validatePending(Student student) {
        if (student.getAuthorities().stream()
                .anyMatch(role -> role.equals(Role.PENDING_USER))) {
            throw new CustomApiException(ResErrorCode.PENDING_AUTHORIZED, "사용자 정보를 입력해주세요.");
        }
    }

    private void handleException(HttpServletResponse response, CustomApiException ex) throws IOException {
        response.setStatus(ex.getErrorCode().getHttpStatusCode());
        response.setContentType("application/json; charset=UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errorCode", ex.getErrorCode());
        errorResponse.put("message", ex.getErrorDescription());
        errorResponse.put("status", ex.getErrorCode().getHttpStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
