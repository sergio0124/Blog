package reznikov.sergey.blog.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.CommentDTO;
import reznikov.sergey.blog.DTO.LikeDTO;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Comment;
import reznikov.sergey.blog.entities.Like;

@Service
public class MappingLike {

    private final ModelMapper modelMapper;

    public MappingLike(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //из entity в dto
    public LikeDTO mapToAppointmentDto(Like like){
        if(like==null){
            return null;
        }

        LikeDTO likeDTO = modelMapper.map(like, LikeDTO.class);
        if(like.getUser() != null){
            likeDTO.setUser(modelMapper.map(like.getUser(), UserDTO.class));
        }
        if(like.getPost() != null){
            likeDTO.setPost(modelMapper.map(like.getPost(), PostDTO.class));
        }

        return likeDTO;
    }

    //из dto в entity
    public Like mapToAppointmentEntity(LikeDTO dto){

        return modelMapper.map(dto, Like.class);
    }

}
