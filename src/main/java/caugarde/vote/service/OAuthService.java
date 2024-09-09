package caugarde.vote.service;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.entity.Student;
import caugarde.vote.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuthService extends DefaultOAuth2UserService {

    private final StudentService studentService;
    private final StudentRepository studentRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String name = oAuth2User.getName();
        String email = oAuth2User.getAttribute("email");

        Optional<Student> existData = studentRepository.findByEmail(oAuth2User.getAttribute("email"));

        UUID uuid = UUID.randomUUID();

        if (existData.isEmpty()) {

            Student student = new Student(uuid,name,"ROLE_USER",email);

            studentService.save(student);

        }
        return new CustomOAuthUser(uuid,name, List.of(() -> "ROLE_USER"));

    }

}
