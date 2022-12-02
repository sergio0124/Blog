package reznikov.sergey.blog.controllers.request_bodies;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowUser {
    Long userId;
    String username;
}
