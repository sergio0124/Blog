package reznikov.sergey.blog.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Post;

@Service
public class MappingPost {

    private final ModelMapper modelMapper;

    public MappingPost(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //из entity в dto
    public PostDTO mapToPostDto(Post post) {
        if (post == null) {
            return null;
        }

        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        if (post.getUser() != null) {
            postDTO.setUser(modelMapper.map(post.getUser(), UserDTO.class));
        }
        postDTO.setLikes(post.getLikes().size());

        return postDTO;
    }

    //из dto в entity
    public Post mapToPostEntity(PostDTO dto) {
        return modelMapper.map(dto, Post.class);
    }
}
