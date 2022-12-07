package reznikov.sergey.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.services.UserService;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;


    @PostMapping(path = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
        } catch (Exception ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
        return ResponseEntity.ok("Активируйте почту в своем аккаунте");
    }



    @GetMapping("/registration")
    public String registrationPage() {
        return "account/registration";
    }
}
