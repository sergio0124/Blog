package reznikov.sergey.blog.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.services.UserService;

import java.util.HashMap;

@Controller
public class AuthController {

    UserService userService;
    MappingUser mappingUser;

    public AuthController(UserService userService, MappingUser mappingUser) {
        this.userService = userService;
        this.mappingUser = mappingUser;
    }


    @PostMapping("/update_user")
    ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO,
                                          @AuthenticationPrincipal User user) {
        userDTO.setId(user.getId());
        try {
            userService.updateUser(userDTO);
        } catch (Exception e) {
            ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Аккаунт изменен");
    }


    @GetMapping("/info")
    String getUserInfo(@AuthenticationPrincipal User user,
                       HashMap<String, Object> model) {
        model.put("user", mappingUser.mapToUserDto(user));
        return "account/user_info_page";
    }

}
