package caugarde.vote.model.constant;

import caugarde.vote.model.entity.Authority;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomOAuthUserTest {


    @BeforeEach
    public void setup() {

        Student student = Student.builder()
                .studentPk(UUID.randomUUID())
                .studentId("20220456")
                .authority(new Authority(UUID.randomUUID(), Role.USER))
                .name("황준영")
                .majority("에너지시스템공학과")
                .email("junyoung@naver.com")
                .memberType("기존 부원")
                .build();


        // 사용자 정보 생성 (예: UserDetails)
        CustomOAuthUser customOAuthUser = new CustomOAuthUser(Collections.singleton(new SimpleGrantedAuthority(Role.USER.getAuth())), student.getStudentPk(), student.getEmail(), true, null);

        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                customOAuthUser,
                null,
                customOAuthUser.getAuthorities()
        );

        // SecurityContext에 Authentication 설정
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("유저 정보 추출하기")
    void getName() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //테스트 검증
        System.out.println(email);
        assertThat(email).isEqualTo("junyoung@naver.com");
    }

    @Test
    @DisplayName("유저 권한 추출")
    void getAuthorities() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(Role.USER.getAuth())));
    }

    @Test
    @DisplayName("유저 id 추출")
    void getId() {
        CustomOAuthUser user = (CustomOAuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getId());
    }
}