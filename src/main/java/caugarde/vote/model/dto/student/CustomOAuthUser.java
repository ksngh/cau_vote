package caugarde.vote.model.dto.student;

import caugarde.vote.model.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuthUser implements OAuth2User {

    private final Student student;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return student.getAuthorities();
    }

    @Override
    public String getName() {
        return student.getEmail();
    }

}
