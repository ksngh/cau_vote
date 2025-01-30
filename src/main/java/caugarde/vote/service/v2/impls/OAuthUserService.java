package caugarde.vote.service.v2.impls;

import caugarde.vote.model.dto.student.CustomOAuthUser;
import caugarde.vote.model.entity.Student;
import caugarde.vote.model.enums.Role;
import caugarde.vote.repository.v2.interfaces.StudentRepository;
import caugarde.vote.service.v2.interfaces.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuthUserService extends DefaultOAuth2UserService {

    private final StudentRepository studentRepository;
    private final StudentService studentService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) kakaoAccount.get("email");


        // ✅ 사용자 존재 여부 확인
        Optional<Student> existingStudent = studentRepository.findByEmail(email);

        Student student;
        if (existingStudent.isPresent()) {
            student = existingStudent.get();

        } else {
            student = Student.create(email);
            studentRepository.save(student);
        }

        return new CustomOAuthUser(student, attributes);
    }
}
