package reznikov.sergey.blog.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostImageDTO extends BaseEntityDTO {

    PostDTO post;

    String image;
}
