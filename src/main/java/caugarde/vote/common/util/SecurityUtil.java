package caugarde.vote.common.util;

import caugarde.vote.common.exception.api.CustomApiException;
import caugarde.vote.common.response.ResErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomApiException(ResErrorCode.FORBIDDEN,"인증된 사용자가 아닙니다.");
        }
        return authentication.getName();
    }

}