package reznikov.sergey.blog.controllers.request_bodies;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FullPost {
    String title;
    String description;
    Date date = new Date();
    String text;
    List<String> postImages;

}