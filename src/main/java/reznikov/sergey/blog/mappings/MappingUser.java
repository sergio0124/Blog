package reznikov.sergey.blog.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.entities.enums.Role;
import java.util.stream.Collectors;

@Service
public class MappingUser {

    private final ModelMapper modelMapper;

    public MappingUser(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //из entity в dto
    public UserDTO mapToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setRoles(user.getRoles().stream().map(Role::name).collect(Collectors.toSet()));

        return userDTO;
    }

    //из dto в entity
    public User mapToUserEntity(UserDTO dto) {
        var user = modelMapper.map(dto, User.class);
        user.setRoles(dto.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()));
        return user;
    }
}
