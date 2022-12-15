package reznikov.sergey.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.services.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@PreAuthorize("MAIN_ADMIN")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

}
