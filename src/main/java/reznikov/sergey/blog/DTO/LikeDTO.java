package reznikov.sergey.blog.DTO;

import lombok.Data;

@Data
public class LikeDTO extends BaseEntityDTO {

    PostDTO post;

    UserDTO user;
}
