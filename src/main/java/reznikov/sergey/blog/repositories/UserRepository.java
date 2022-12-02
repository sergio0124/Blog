package reznikov.sergey.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.entities.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    List<User> findUsersByUsernameContainsIgnoreCase(String loginPart);

    Optional<User> findUserById(Long id);

    Page<User> findUsersByRoles(Set<Role> roles, Pageable pageable);

    Page<User> findUsersByRolesAndUsernameContainingIgnoreCase(Set<Role> roles, String username, Pageable pageable);
}
