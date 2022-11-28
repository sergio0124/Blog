package reznikov.sergey.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.services.UserService;

@Controller
public class RegistrationController {

    UserService userRepository;

    public RegistrationController(@Autowired UserService userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(path = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@RequestBody User user) {
        if (user.getPassword() == null || user.getUsername() == null || user.getRoles().size() == 0) {
            return ResponseEntity.status(206).body("data has errors");
        }
        try {
            userRepository.registerUser(user);
        } catch (Exception ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
        return ResponseEntity.ok("registration complete");
    }

    @GetMapping("/registration")
    public ModelAndView registrationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }

}
