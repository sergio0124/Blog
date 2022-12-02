package reznikov.sergey.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.repositories.UserRepository;

@Service
public class UserService {


    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserRepository userRepo;

    public UserService(@Autowired UserRepository userRepo, @Autowired BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public void registerUser(User user) throws Exception {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new Exception("Данный пользователь уже существует");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }
}


