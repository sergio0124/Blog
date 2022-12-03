package reznikov.sergey.blog.DTO;

import lombok.Data;

@Data
public class PostImageDTO extends BaseEntityDTO {

    PostDTO post;

    String image;
}
