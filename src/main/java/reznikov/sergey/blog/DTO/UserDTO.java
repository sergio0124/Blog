package reznikov.sergey.blog.DTO;

import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
public class UserDTO extends BaseEntityDTO {

    private String username;

    private String oldPassword;
    private String password;

    private Date registrationDate = new Date();

    private Set<String> roles;

    private boolean nonLocked = true;
}
