package reznikov.sergey.blog.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.entities.enums.Role;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.services.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MainAdminController {

    private static final int PAGE_SIZE = 10;

    private final UserService userService;
    private final MappingUser mappingUser;

    @GetMapping("main_admin/")
    String getRepostPage(@RequestParam(value = "page", required = false) Optional<Integer> pageNumber,
                         @RequestParam(required = false) Optional<String> search,
                         Map<String, Object> model,
                         @AuthenticationPrincipal User user) {

        int page = pageNumber.orElse(0);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<UserDTO> users;
        if (search.isPresent()) {
            String str = search.get();
            users = userService.findUsersByRoleAndSearch(pageable,
                    List.of(Role.ADMIN), str);
            model.put("search", str);
        } else {
            users = userService.findUsersByRole(pageable, List.of(Role.ADMIN));
        }
        model.put("admins", users);
        model.put("page", page);
        model.put("user", mappingUser.mapToUserDto(user));

        return "main_admin_home_page";
    }

    @GetMapping("main_admin/save_admin")
    String getAdminSavePage(@RequestParam(required = false) Optional<Long> userId,
                            Map<String, Object> model,
                            @AuthenticationPrincipal User user) {
        if (userId.isPresent()) {
            Long id = userId.get();
            UserDTO userDTO = userService.loadUserById(id);
            if (userDTO == null) {
                model.put("message", "Пользователь не найден");
            } else {
                model.put("admin", userDTO);
            }
        }
        model.put("user", mappingUser.mapToUserDto(user));

        return "save_admin";
    }


    @PostMapping("main_admin/save_admin")
    ResponseEntity<Object> saveAdmin(@RequestBody UserDTO userDTO) {
        UserDTO res;
        userDTO.setRoles(Set.of(Role.ADMIN.name()));
        if (userDTO.getId() != null) {
            res = userService.updateUser(userDTO);
        } else {
            res = userService.registerUser(userDTO);
        }

        if (res == null) {
            return ResponseEntity.badRequest()
                    .body("Что-то пошло не так и пользователь не был зарегестрирован");
        }
        return ResponseEntity.ok("Пользователь создан");
    }

    @GetMapping("main_admin/delete_admin")
    private String deleteAdminPage(@RequestParam Long userId,
                                   Map<String, Object> model,
                                   @AuthenticationPrincipal User user) {
        UserDTO userDTO = userService.loadUserById(userId);
        if (userDTO != null) {
            model.put("admin", userDTO);
        } else {
            model.put("message", "Пользователь с таким id не найден");
        }
        model.put("user", mappingUser.mapToUserDto(user));
        return "delete_admin";
    }


    @PostMapping("main_admin/delete_admin")
    private ResponseEntity<Object> deleteAdmin(@RequestParam Long userId) {
        UserDTO userDTO = userService.loadUserById(userId);
        if (userDTO == null) {
            return ResponseEntity.badRequest().body("Пользователь не найден");
        }
        userService.deleteUser(userDTO);
        return ResponseEntity.ok("Пользователь удалён");
    }
}
