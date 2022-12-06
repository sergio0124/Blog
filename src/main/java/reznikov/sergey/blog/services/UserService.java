package reznikov.sergey.blog.services;

import com.mysql.cj.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.repositories.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepo;

    private final MappingUser mappingUser;

    private final MailSender mailSender;

    public void registerUser(UserDTO userDTO) throws Exception {
        User user = mappingUser.mapToUserEntity(userDTO);

        if (user.getPassword() == null || user.getUsername() == null || user.getRoles().size() == 0) {
            throw new Exception("Данные неполные");
        }
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new Exception("Пользователь с данным логином уже существует");
        }
        user.setActivationCode(UUID.randomUUID().toString());

        if (!StringUtils.isNullOrEmpty(user.getMail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getMail(), "Activation code", message);
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
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


    public void updateUser(UserDTO userDTO) throws Exception {
        User user = userRepo.findUserById(userDTO.getId()).orElse(null);
        if (user == null) {
            throw new Exception("Пользователь с таким id не найден");
        }
        if (!bCryptPasswordEncoder.matches(userDTO.getOldPassword(), user.getPassword())) {
            throw new Exception("Введен неверный старый пароль");
        }

        if (userDTO.getPassword() != null) {
            user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        } else if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }

        userRepo.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO loadUserById(Long userId) throws Exception {
        User user = userRepo.findUserById(userId).orElse(null);
        if (user == null) {
            throw new Exception("Пользователь с таким id не найден");
        }
        return mappingUser.mapToUserDto(user);
    }
}
