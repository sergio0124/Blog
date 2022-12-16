package reznikov.sergey.blog.controllers;

import com.mysql.cj.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.services.MailSenderService;
import reznikov.sergey.blog.services.ScheduledService;
import reznikov.sergey.blog.services.UserService;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class ActivateController {

    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final ScheduledService scheduledService;


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
                          @RequestParam(required = false) String login) {
        if (login != null) {
            UserDTO userDTO = userService.loadUserDTOByUsername(login);
            if (userDTO == null) {
                model.put("message", "Пользователь с таким логином не найден");
                return "account/recover_password_login";
            }
            if (!userDTO.isActive()) {
                model.put("message", "Активируйте аккаунт, пройдя по ссылке в письме от reznikovsergeyfirst@gmail.com");
                return "account/recover_password_login";
            }
            if (!StringUtils.isNullOrEmpty(userDTO.getMail())) {

                String code = scheduledService.putId(userDTO.getId());
                String message = String.format(
                        "Hello, %s! \n" +
                                "Welcome to Blog. Please, visit next link to recover password: " +
                                "http://localhost:8080/activate/recover_password/%s",
                        userDTO.getUsername(),
                        code
                );

                mailSenderService.send(userDTO.getMail(), "Recover code", message);
            }

            model.put("message", "Ссылка для восстановления отправлена на почту и действует в течение 10 минут");
        }

        return "account/recover_password_login";
    }

    @GetMapping("activate/recover_password/{code}")
    public String recoverPassword(@PathVariable String code,
                                  HashMap<String, Object> model) {
        if (!scheduledService.isCodeExists(code)) {
            model.put("message", "Скорее всего, время действия кода истекло, повторите процедуру восстановления");
        }
        return "account/recover_password";
    }


    @PostMapping("activate/recover_password/{code}")
    public ResponseEntity<Object> recoverPasswordPost(@PathVariable String code,
                                                      @RequestBody UserDTO userDTO,
                                                      HashMap<String, Object> model) {
        if (!scheduledService.isCodeExists(code)) {
            return ResponseEntity.badRequest().body("Скорее всего, время действия кода истекло, повторите процедуру восстановления");
        }
        UserDTO user = userService.loadUserById(scheduledService.getIdByCode(code));
        if (user == null) {
            return ResponseEntity.badRequest().body("По какой-то причине пользователь не найден, обратитесь к администратору");
        }
        if (userDTO.getPassword() == null) {
            return ResponseEntity.badRequest().body("Отправлены неполные данные");
        }
        user.setPassword(userDTO.getPassword());
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Возникла ошибка в обновлении данных");
        }
        scheduledService.deleteCode(code);

        return ResponseEntity.ok("Пароль обновлен");
    }
}
