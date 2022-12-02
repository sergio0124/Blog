package reznikov.sergey.blog.controllers.request_bodies;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class FullPost {
    Long postId;
    Long userId;
    String title;
    String description;
    Date date = new Date();
    String text;
    List<String> postImages;
    Long likesCount;

    public FullPost(Long postId, Long userId, String title, String description, Date date, String text, List<String> postImages) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.text = text;
        this.postImages = postImages;
    }
}