package reznikov.sergey.blog.DTO;

import lombok.Data;
import reznikov.sergey.blog.entities.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO extends BaseEntityDTO {

    private String username;

    private String password;

    private Date registrationDate = new Date();

    private Set<String> roles;

    private boolean nonLocked = true;

    private List<Post> posts;

    private List<Comment> comments;

    private List<Like> likes;

    private List<Report> reports;

    private List<Subscribe> subscribes;

    private List<Subscribe> influencers;
}
