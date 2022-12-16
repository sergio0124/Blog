package reznikov.sergey.blog.services;

import com.mysql.cj.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.entities.enums.Role;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.repositories.UserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepo;

    private final MappingUser mappingUser;

    private final MailSenderService mailSender;

    public UserDTO registerUser(UserDTO userDTO) {
        User user = mappingUser.mapToUserEntity(userDTO);

        if (user.getPassword() == null || user.getUsername() == null || user.getRoles().size() == 0) {
            return null;
        }
        if (userRepo.existsByUsername(user.getUsername())) {
            return null;
        }

        if (userDTO.getMail() != null) {
            user.setActivationCode(UUID.randomUUID().toString());
            if (!StringUtils.isNullOrEmpty(user.getMail())) {
                String message = String.format(
                        "Hello, %s! \n" +
                                "Welcome to Blog. Please, visit next link: http://localhost:8080/activate/%s",
                        user.getUsername(),
                        user.getActivationCode()
                );

                mailSender.send(user.getMail(), "Activation code", message);
            }
        } else {
            user.setActive(true);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return mappingUser.mapToUserDto(userRepo.save(user));
    }


    public boolean activateUser(String code) {
        User user = userRepo.findUserByActivationCode(code).orElse(null);

        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);
        return true;
    }


    public UserDTO updateUser(UserDTO userDTO) {
        User user = userRepo.findUserById(userDTO.getId()).orElse(null);
        if (user == null) {
            return null;
        }

        if (userDTO.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }

        return mappingUser.mapToUserDto(userRepo.save(user));
    }


    public void deleteUser(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            return;
        }
        User user = userRepo.findUserById(userDTO.getId()).orElse(null);
        if (user == null) {
            return;
        }
        userRepo.delete(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO loadUserDTOByUsername(String username) throws UsernameNotFoundException {
        return mappingUser.mapToUserDto(userRepo.findByUsername(username).orElse(null));
    }

    public UserDTO loadUserById(Long userId) {
        User user = userRepo.findUserById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        return mappingUser.mapToUserDto(user);
    }

    public List<UserDTO> findUsersByRole(Pageable pageable, Collection<Role> roles) {
        return userRepo.findUsersByRolesIsIn(new HashSet<>(roles), pageable)
                .getContent()
                .stream()
                .map(mappingUser::mapToUserDto)
                .toList();
    }

    public List<UserDTO> findUsersByRoleAndSearch(Pageable pageable,
                                                  Collection<Role> roles,
                                                  String search) {
        return userRepo.findUsersByRolesIsInAndUsernameContainsIgnoreCase(
                        new HashSet<>(roles), search, pageable)
                .getContent()
                .stream()
                .map(mappingUser::mapToUserDto)
                .toList();

    }

    public void blockUser(UserDTO user) {
        User curUser = userRepo.findUserById(user.getId()).orElse(null);
        if (curUser == null) {
            return;
        }
        curUser.setNonLocked(false);
        userRepo.save(curUser);
    }
}
