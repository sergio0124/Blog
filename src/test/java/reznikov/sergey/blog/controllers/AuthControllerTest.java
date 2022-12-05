package reznikov.sergey.blog.controllers;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.repositories.UserRepository;
import reznikov.sergey.blog.services.UserService;

import java.util.HashMap;
import java.util.Map;

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
    void register() {
        reznikov.sergey.blog.DTO.UserDTO userDto = new userDto();
        userDto.setPassword("");

        Map<String, String> form = new HashMap();
        form.put("THERAPIST", "on");
        form.put("ENT", "on");
        form.put("User", "on");

        userService.registerUser(userDto);

        Assert.assertEquals(userDto.getPosition(), Set.of(Position.THERAPIST, Position.ENT));
        Assert.assertEquals(userDto.getRole(), Set.of(Role.User));

        User UserEntity = Mockito.verify(mappingUsers, Mockito.times(1)).mapToUserEntity(userDto);
        Mockito.verify(UserRepo, Mockito.times(1)).save(UserEntity);
    }
}