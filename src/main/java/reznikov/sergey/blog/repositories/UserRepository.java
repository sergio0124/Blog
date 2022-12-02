package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reznikov.sergey.blog.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    List<User> findUsersByUsernameContainsIgnoreCase(String loginPart);

    Optional<User> findUserById(Long id);

//    User saveUser(User entity);
}
