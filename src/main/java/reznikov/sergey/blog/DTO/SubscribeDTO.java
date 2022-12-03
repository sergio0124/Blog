package reznikov.sergey.blog.DTO;

import lombok.Data;

@Data
public class SubscribeDTO extends BaseEntityDTO {

    UserDTO subscriber;

    UserDTO influencer;
}
