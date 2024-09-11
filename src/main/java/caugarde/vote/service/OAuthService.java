package caugarde.vote.service;

import caugarde.vote.model.constant.CustomOAuthUser;
import caugarde.vote.model.entity.Student;
import caugarde.vote.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuthService extends DefaultOAuth2UserService {

    private final StudentRepository studentRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String,Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        String email = (String) kakaoAccount.get("email");


        Optional<Student> studentOptional = studentRepository.findByEmail(email);

        if (studentOptional.isPresent()) {
            return new CustomOAuthUser(studentOptional.get().getStudentPk(), email,true,oAuth2User.getAttributes()) ;
        } else {
            UUID uuid = UUID.randomUUID();
            Student student = new Student(uuid,email);
            studentRepository.save(student);
            return new CustomOAuthUser(uuid,email,false,oAuth2User.getAttributes());
        }


    }

}
