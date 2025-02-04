package caugarde.vote.model.enums;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    PENDING_USER("ROLE_PENDING_USER");

    private final String auth;

    Role(String auth) {
        this.auth = auth;
    }

    @Override
    public String getAuthority() {
        return auth;
    }
}
