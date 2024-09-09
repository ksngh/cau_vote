package caugarde.vote.model.constant;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomOAuthUser implements OAuth2User {

    private final UUID userId; // 사용자 ID
    private final String name; // 사용자 이름
    private final List<GrantedAuthority> authorities; // 사용자 권한

    public CustomOAuthUser(UUID userId, String name, List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.name = name;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }
}


