package reznikov.sergey.blog.entities.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    CREATOR,
    ADMIN,
    MAIN_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
