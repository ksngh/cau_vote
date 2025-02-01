package caugarde.vote.config;

import caugarde.vote.common.filter.JwtAuthenticationFilter;
import caugarde.vote.service.v2.impls.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthUserService oAuthUserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilter(jwtAuthenticationFilter)
                .oauth2Login(oauth2 -> oauth2
                        .loginProcessingUrl("/oauth/kakao/callback")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuthUserService)  // 사용자 정보 가져오기
                        )
                        .defaultSuccessUrl("/", true)  // 로그인 성공 시 리디렉트
                );
        return http.build();
    }

}
