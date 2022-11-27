package reznikov.sergey.blog.entities;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    User,
    Creator,
    Admin,
    MainAdmin;

    @Override
    public String getAuthority() {
        return name();
    }
}
