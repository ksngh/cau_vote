package caugarde.vote.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {

    private static final String AUTH_COOKIE_NAME = "Authorization";
    private static final int expiration = 60*60*24*7;

    public void createCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, token);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiration);

        response.addCookie(cookie);
    }

    public String getAuthCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AUTH_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(AUTH_COOKIE_NAME, null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}

