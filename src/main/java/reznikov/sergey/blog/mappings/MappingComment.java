package reznikov.sergey.blog.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.CommentDTO;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Comment;


@Service
public class MappingComment {

    private final ModelMapper modelMapper;

    public MappingComment(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //из entity в dto
    public CommentDTO mapToCommentDto(Comment comment){
        if(comment==null){
            return null;
        }

        CommentDTO commentDTO = modelMapper.map(comment, CommentDTO.class);
        if(comment.getUser() != null){
            commentDTO.setUser(modelMapper.map(comment.getUser(), UserDTO.class));
        }
        if(comment.getPost() != null){
            commentDTO.setPost(modelMapper.map(comment.getPost(), PostDTO.class));
        }

        return commentDTO;
    }

    //из dto в entity
    public Comment mapToCommentEntity(CommentDTO dto){

        return modelMapper.map(dto, Comment.class);
    }

}
