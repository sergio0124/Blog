package reznikov.sergey.blog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reznikov.sergey.blog.entities.User;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<Object> sayHello(@AuthenticationPrincipal User curUser) {
        return ResponseEntity.ok((curUser == null ? "no user" : curUser.getUsername()) +
                "\n\n<html>\n" +
                "<head>\n" +
                "    <title>Index</title>\n" +
                "    <link rel=\"stylesheet\" href=\"/static/css/index.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>  Hello I am grad to see ya!  </h1>\n" +
                "\n" +
                "</body>\n" +
                "</html>");
    }

}
