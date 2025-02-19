package caugarde.vote.controller.v2.api;


import caugarde.vote.common.response.CustomApiResponse;
import caugarde.vote.common.response.ResSuccessCode;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.dto.student.auth.AuthInfo;
import caugarde.vote.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/v2/api")
@RequiredArgsConstructor
public class AuthApiController {

    @GetMapping("/auth")
    public CustomApiResponse<AuthInfo.Response> auth(@AuthenticationPrincipal CustomOAuthUser user) {
        AuthInfo.Response response = AuthInfo.Response.of((Set<Role>)user.getAuthorities());
        return CustomApiResponse.OK(ResSuccessCode.READ, response);
    }

}
