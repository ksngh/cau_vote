package caugarde.vote.model.constant;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Getter
public class CustomOAuthUser implements OAuth2User {

    private final UUID id; // 사용자 ID
    private final String email; // 사용자 닉네임// OAuth2 사용자 속성
    private final Boolean isSignedIn;
    private final Map<String, Object> attributes;

    public CustomOAuthUser(UUID id, String email, Boolean isSignedIn, Map<String, Object> attributes) {
        this.id = id;
        this.email = email;
        this.isSignedIn = isSignedIn;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return email; // 이메일을 사용자 이름으로 사용
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes; // OAuth2 사용자 속성 반환
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name); // 특정 속성 반환
    }
}
