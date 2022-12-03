package reznikov.sergey.blog.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.repositories.UserRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserRepository userRepo;

    MappingUser mappingUser;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepo, MappingUser mappingUser) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepo = userRepo;
        this.mappingUser = mappingUser;
    }

    public void registerUser(UserDTO userDTO) throws Exception {
        User user = mappingUser.mapToUserEntity(userDTO);

        if (user.getPassword() == null || user.getUsername() == null || user.getRoles().size() == 0) {
            throw new Exception("Данные неполные");
        }
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new Exception("Пользователь с данным логином уже существует");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void updateUser(UserDTO userDTO) throws Exception{
        User user = userRepo.findUserById(userDTO.getId()).orElse(null);
        if(user==null){
            throw new Exception("Пользователь с таким id не найден");
        }

        if(userDTO.getPassword()!=null){
            user.setPassword(userDTO.getPassword());
        }
        else if (userDTO.getUsername()!=null){
            user.setUsername(userDTO.getUsername());
        }

        userRepo.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
