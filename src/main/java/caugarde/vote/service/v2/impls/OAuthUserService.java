package caugarde.vote.service.v2.impls;

import caugarde.vote.common.util.CookieUtil;
import caugarde.vote.common.util.JwtUtil;
import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.entity.Student;
import caugarde.vote.repository.v2.interfaces.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

    private final StudentRepository studentRepository;
    private final CookieUtil cookieUtil;
    private final HttpServletResponse response;
    private final JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = extractEmail(attributes);

        String token = jwtUtil.generateToken(email);
        cookieUtil.createCookie(response, token);

        Optional<Student> existingStudent = studentRepository.findByEmail(email);

        Student student = extractStudent(existingStudent,email);

        return new CustomOAuthUser(student);
    }

    private String extractEmail(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    private Student extractStudent(Optional<Student> existingStudent,String email) {
        Student student;
        if (existingStudent.isEmpty()){
            student = Student.create(email);
            studentRepository.save(student);
        }else{
            student = existingStudent.get();
        }
        return student;
    }


}
