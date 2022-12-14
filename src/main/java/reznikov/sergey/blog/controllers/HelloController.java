package reznikov.sergey.blog.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.entities.enums.Role;

import java.util.Optional;

@Controller
public class HelloController {

    @GetMapping("/")
    public String sayHello(@AuthenticationPrincipal User curUser) {

        var roles = curUser.getRoles();
        if (roles.contains(Role.MAIN_ADMIN)) {
            return "redirect:main_admin/";
        } else if (roles.contains(Role.ADMIN)) {
            return "redirect:admin/";
        } else if (roles.contains(Role.CREATOR)) {
            return "redirect:creator/";
        } else if (roles.contains(Role.USER)) {
            return "redirect:user/";
        }

        return "error";
    }

}
