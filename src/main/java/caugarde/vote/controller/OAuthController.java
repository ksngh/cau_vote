package caugarde.vote.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @PostMapping("/kakao/callback")
    @ResponseBody
    public String kakaoCallback(@AuthenticationPrincipal OAuth2User user) {
        return "Kakao 로그인 성공! 사용자: " + user.getAttribute("nickname") + ", 이메일: " + user.getAttribute("email");
    }
}
