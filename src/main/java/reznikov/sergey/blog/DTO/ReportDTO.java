package reznikov.sergey.blog.DTO;

import lombok.Data;

@Data
public class ReportDTO extends BaseEntityDTO {

    PostDTO post;

    UserDTO user;

    String reportType;
}
