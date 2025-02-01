package caugarde.vote.service.v2.impls;

import caugarde.vote.common.util.CookieUtil;
import caugarde.vote.common.util.JwtUtil;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.entity.Student;
import caugarde.vote.repository.v2.interfaces.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

// jwt에 일단 정보 올리기. 
// security context holder 에 email 이랑 attributes 올려놓기.
// signup page로 리다이렉트
// 로그인 안되어 있으면 투표 x -> 투표할때 findby 하면 거기서 npe 터트리기
// request에 validation 걸어놓기

@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

    private final StudentRepository studentRepository;
    private final CookieUtil cookieUtil;
    private JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = extractEmail(attributes);

        Optional<Student> existingStudent = studentRepository.findByEmail(email);
        Student student = validateStudent(existingStudent,email);


        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(oAuth2User, null, oAuth2User.getAuthorities()));


        return new CustomOAuthUser(student, attributes);
    }

    private String extractEmail(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    private Student validateStudent(Optional<Student> existingStudent, String email) {
        return existingStudent.orElseGet(() -> Student.create(email));
    }

}
