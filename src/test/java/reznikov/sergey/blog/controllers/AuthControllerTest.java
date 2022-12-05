package reznikov.sergey.blog.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.repositories.UserRepository;
import reznikov.sergey.blog.services.UserService;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthControllerTest {


    @Autowired
    private UserService userService;

    @MockBean
    private MappingUser mappingUser;

    @MockBean
    private UserRepository userRepository;


    @Test
    void register() throws Exception {
        UserDTO userDto = new UserDTO();
        userDto.setPassword("");
        userDto.setRoles(new HashSet<>(List.of("USER")));

        userService.registerUser(userDto);

        User UserEntity = Mockito.verify(mappingUser, Mockito.times(1)).mapToUserEntity(userDto);
        Mockito.verify(userRepository, Mockito.times(1)).save(UserEntity);
    }
}