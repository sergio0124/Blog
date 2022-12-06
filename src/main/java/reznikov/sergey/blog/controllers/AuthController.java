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

    @PostMapping(path = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
        return ResponseEntity.ok("Активируйте почту в своем аккаунте");
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


    @GetMapping("/registration")
    public String registrationPage() {
        return "account/registration";
    }


    @GetMapping("/info")
    String getUserInfo(@AuthenticationPrincipal User user,
                       HashMap<String, Object> model) {
        model.put("user", mappingUser.mapToUserDto(user));
        return "account/user_info_page";
    }


    @GetMapping("activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found!");
        }

        return "account/login_user";
    }


    @GetMapping("activate/recover_password")
    public String recover(HashMap<String, Object> model,
                          ){
        model.put("message", "Ссылка для восстановления отправлена на почту и действует в течение 10 минут");

        return "account/recover_password_login";
    }

    @GetMapping("activate/recover_password/{code}")
    public String recoverPassword(@PathVariable String code){
        return "account/recover_password";
    }


    @PostMapping("activate/recover_password/{code}")
    public String recoverPasswordPost(@PathVariable String code,
                                      HashMap<String, Object> model){

        model.put("message", "Пароль обновлен");
        return "redirect:login";
    }
}
