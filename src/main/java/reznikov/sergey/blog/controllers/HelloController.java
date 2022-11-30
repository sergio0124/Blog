package reznikov.sergey.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.blog.entities.Role;
import reznikov.sergey.blog.entities.User;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public ModelAndView sayHello(@AuthenticationPrincipal User curUser) {
        if (curUser == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView modelAndView = new ModelAndView();
        var roles = curUser.getRoles();
        if (roles.contains(Role.MainAdmin)) {

        } else if (roles.contains(Role.Admin)) {

        } else if (roles.contains(Role.Creator)) {
            modelAndView.setViewName("redirect:creator/");
        } else if (roles.contains(Role.User)) {

        }

        return modelAndView;
    }

}
