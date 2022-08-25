package reznikov.sergey.blog.business_logic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public ResponseEntity<Object> sayHello() {
        return ResponseEntity.ok("<html>\n" +
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
