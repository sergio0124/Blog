package reznikov.sergey.blog.DTO;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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
