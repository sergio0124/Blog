package reznikov.sergey.blog.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class PostDTO extends BaseEntityDTO {

    private Date date = new Date();

    private UserDTO user;

    private String title;

    private String description;

    private String text;

    List<CommentDTO> comments = new ArrayList<>();

    private int likesCount;

    List<ReportDTO> reports = new ArrayList<>();

    List<PostImageDTO> postImages = new ArrayList<>();

}
