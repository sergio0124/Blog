package reznikov.sergey.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/")
    ModelAndView getRepostPage(@RequestParam(value = "page", required = false) Optional<Integer> pageNumber){
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> map = new HashMap<>();
        modelAndView.setViewName("admin_home_page");


        modelAndView.addAllObjects(map);
        return modelAndView;
    }
}
