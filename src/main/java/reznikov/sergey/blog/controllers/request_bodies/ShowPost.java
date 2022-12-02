package reznikov.sergey.blog.controllers.request_bodies;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ShowPost {
    String creator_name;
    String title;
    String description;
    Long id;
    Date date;

}
