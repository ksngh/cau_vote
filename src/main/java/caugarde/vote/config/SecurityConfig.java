package caugarde.vote.config;

import caugarde.vote.common.handler.AdminAuthenticationFailureHandler;
import caugarde.vote.common.handler.AdminAuthenticationSuccessHandler;
import caugarde.vote.common.handler.CustomAuthenticationSuccessHandler;
import caugarde.vote.service.user.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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

                        .requestMatchers(HttpMethod.GET, "/mypage").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/api/student/vote/*").hasRole("USER")

                        .requestMatchers(HttpMethod.POST, "/api/vote").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/vote/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/vote/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/posting").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/admin/posting/*").hasRole("ADMIN")


                        .anyRequest().permitAll()
                );

        //form 로그인(admin)
        http.formLogin(formLogin -> {
            formLogin
                    .loginPage("/admin/login")
                    .permitAll()// 사용자 정의 로그인 페이지
                    .loginProcessingUrl("/admin/loginProcess")
                    .permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new AdminAuthenticationSuccessHandler())
                    .failureHandler(new AdminAuthenticationFailureHandler());
        });


        //oauth
        http
                .oauth2Login((oauth2) -> oauth2
                        .loginProcessingUrl("/oauth/kakao/callback")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(oAuthService))
                        .successHandler(new CustomAuthenticationSuccessHandler()));

        http.logout((logout) -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll());

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
