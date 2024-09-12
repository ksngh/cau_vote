package caugarde.vote.config;

import caugarde.vote.common.CustomAuthenticationSuccessHandler;
import caugarde.vote.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final OAuthService oAuthService;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // token을 사용하는 방식이기 때문에 csrf disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        http
                .httpBasic(AbstractHttpConfigurer::disable);
        //

        http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(
                                        "/","/review/{id}","/submit","/api/**","/registration",
                                        "/api/submit","/view/common/header","/project/","/project/write").permitAll()
                                .anyRequest().permitAll()
                        // authenticated()
                );

        //form 로그인 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        //oauth
        http
                .oauth2Login((oauth2) -> oauth2
                        .loginProcessingUrl("/oauth/kakao/callback")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(oAuthService))
                        .successHandler(new CustomAuthenticationSuccessHandler()));

        return http.build();


    }

}
