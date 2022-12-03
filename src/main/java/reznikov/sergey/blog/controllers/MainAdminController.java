package reznikov.sergey.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main_admin")
public class MainAdminController {
//    int PAGE_SIZE = 10;
//
//    UserRepository userRepository;
//    UserService userService;
//
//    public MainAdminController(UserRepository userRepository,
//                               UserService userService) {
//        this.userRepository = userRepository;
//        this.userService = userService;
//    }
//
//
//    @GetMapping("/")
//    ModelAndView getAdmins(
//            @RequestParam(name = "page", required = false) Optional<Integer> pageNumber) {
//        ModelAndView modelAndView = new ModelAndView();
//        Map<String, Object> map = new HashMap<>();
//        modelAndView.setViewName("user_home_page");
//
//        int page = pageNumber.orElse(0);
//        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
//        var admins = userRepository
//                .findUsersByRoles_NameIn(List.of(Role.ADMIN.name()), pageable)
//                .getContent();
//
//        map.put("admins", admins
//                .stream()
//                .map(rec -> new ShowUser(rec.getId(), rec.getUsername())));
//        modelAndView.addAllObjects(map);
//        return modelAndView;
//    }
//
//
//    @GetMapping("/search")
//    ModelAndView getSearchPage(@RequestParam("string") String searchRequest,
//                               @RequestParam(value = "page", required = false) Optional<Integer> pageNumber) {
//        ModelAndView modelAndView = new ModelAndView();
//        Map<String, Object> map = new HashMap<>();
//        modelAndView.setViewName("main_admin_home_page");
//
//        int page = pageNumber.orElse(0);
//        PageRequest pageRequest =
//                PageRequest.of(page, PAGE_SIZE);
//        var users = userRepository.findUsersByRoles_NameInAndUsernameContainingIgnoreCase(
//                List.of(Role.ADMIN.name()), searchRequest, pageRequest).toList();
//
//        map.put("admins", users
//                .stream()
//                .map(rec -> new ShowUser(rec.getId(), rec.getUsername()))
//                .collect(Collectors.toList()));
//        modelAndView.addAllObjects(map);
//        return modelAndView;
//    }
//
//
//    @DeleteMapping("/block_admin")
//    ResponseEntity<Object> blockAdmin(HttpServletRequest request) {
//        User user = userRepository.findUserById(Long.valueOf(request.getParameter("user_id"))).orElse(null);
//        if (user == null) {
//            return ResponseEntity.badRequest().body("Пользователь не найден");
//        }
//        user.setNonLocked(false);
//        userRepository.save(user);
//        return ResponseEntity.ok("Админ заблокирован");
//    }
//
//
//    @PutMapping("/create_admin")
//    ResponseEntity<Object> createAdmin(@RequestBody User user) {
//        if (user.getPassword() == null || user.getUsername() == null) {
//            return ResponseEntity.status(206).body("data has errors");
//        }
//        user.setRoles(new HashSet<>(List.of(Role.ADMIN)));
//        try {
//            userService.registerUser(user);
//        } catch (Exception ex) {
//            return ResponseEntity.status(400).body(ex.getMessage());
//        }
//        return ResponseEntity.ok("registration complete");
//
//    }
}
