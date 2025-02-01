package caugarde.vote.common.filter;

import caugarde.vote.common.util.CookieUtil;
import caugarde.vote.common.util.JwtUtil;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationCookie = cookieUtil.getAuthCookie(request);

        if (authorizationCookie != null) {
            String token = authorizationCookie.substring(7);
            String email = jwtUtil.getEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

//                if (jwtUtil.validateToken(token, email)) {
//                    UsernamePasswordAuthenticationToken authentication =
//                            new UsernamePasswordAuthenticationToken(oAuthUser, null, oAuthUser.getAuthorities());
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
            }
        }
        CustomOAuthUser user = (CustomOAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String jwtToken = jwtUtil.generateToken(user.getName());
        cookieUtil.createCookie(response, jwtToken);

        filterChain.doFilter(request, response);
    }

}