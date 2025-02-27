package caugarde.vote.config;

import caugarde.vote.common.filter.JwtAuthenticationFilter;
import caugarde.vote.model.enums.Role;
import caugarde.vote.service.v2.impls.OAuthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuthUserService oAuthUserService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 👈 세션 끄기
                )
                .authorizeHttpRequests(request -> request
                        //Student
                        .requestMatchers(HttpMethod.GET,"/oauth2/authorization/kakao").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v2/api/logout").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v2/api/student/late-fee").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH,"/v2/api/student").hasRole(Role.PENDING_USER.name())
                        .requestMatchers(HttpMethod.GET,"/v2/api/student/board").hasAnyRole(Role.ADMIN.name(),Role.USER.name())
                        .requestMatchers(HttpMethod.GET,"/v2/api/student/gear").hasAnyRole(Role.ADMIN.name(),Role.USER.name())

                        //static
                        .requestMatchers(HttpMethod.GET,"/css/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/js/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/images/**").permitAll()

                        //vote
                        .requestMatchers(HttpMethod.GET,"/v2/api/board/*/vote").permitAll()

                        //rental gear
                        .requestMatchers(HttpMethod.POST,"/v2/api/gear/*/rental").hasAnyRole(Role.ADMIN.name(),Role.USER.name())
                        .requestMatchers(HttpMethod.GET,"/v2/api/gear/*/rental").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/v2/api/gear/*/return").hasAnyRole(Role.ADMIN.name(),Role.USER.name())
                        .requestMatchers(HttpMethod.GET,"/v2/api/rental-gear").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/v2/api/rental-gear/history").hasRole(Role.ADMIN.name())

                        //gear
                        .requestMatchers(HttpMethod.GET,"/v2/api/gear").permitAll()
                        .requestMatchers(HttpMethod.POST,"/v2/api/gear").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/v2/api/gear/*").hasAnyRole(Role.ADMIN.name(),Role.USER.name())

                        //board
                        .requestMatchers(HttpMethod.POST,"/v2/api/board").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/v2/api/board").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/v2/api/board/*").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/v2/api/board/*").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/v2/api/board/*/count").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v2/api/board/*").permitAll()

                        //auth
                        .requestMatchers(HttpMethod.GET,"/v2/api/auth").permitAll()

                        //attendance
                        .requestMatchers(HttpMethod.GET,"/v2/api/attendance/most").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v2/api/attendance/ranking").permitAll()
                        .requestMatchers(HttpMethod.GET,"/v2/api/attendance/before-all").permitAll()

                        //admin
                        .requestMatchers(HttpMethod.GET,"/v2/api/admin/student").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH,"/v2/api/admin/student/*").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PATCH,"/v2/api/admin/student/*/late-fee").hasAnyRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET,"/v2/api/admin/student/*").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,"/v2/api/admin/student/*").hasRole(Role.ADMIN.name())

                        //view
                        .requestMatchers(HttpMethod.GET,"/").permitAll()
                        .requestMatchers(HttpMethod.GET,"/gear/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/ranking").permitAll()
                        .requestMatchers(HttpMethod.GET,"/mypage/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/admin/**").hasRole(Role.ADMIN.name())
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().permitAll()
                )

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .loginProcessingUrl("/oauth/kakao/callback")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuthUserService)
                        )
                        .defaultSuccessUrl("/", true)
                );

        return http.build();
    }

}
