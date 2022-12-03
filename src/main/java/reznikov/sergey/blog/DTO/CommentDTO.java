package reznikov.sergey.blog.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDTO extends BaseEntityDTO {

    PostDTO post;

    UserDTO user;

    Date creationDate = new Date();

    String text;
}
