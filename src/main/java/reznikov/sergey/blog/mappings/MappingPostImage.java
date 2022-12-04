package reznikov.sergey.blog.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.PostImageDTO;
import reznikov.sergey.blog.entities.PostImage;

import java.nio.charset.StandardCharsets;

@Service
public class MappingPostImage {

    private final ModelMapper modelMapper;

    public MappingPostImage(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //из entity в dto
    public PostImageDTO mapToPostImageDto(PostImage postImage) {
        if (postImage == null) {
            return null;
        }

        PostImageDTO postImageDTO = modelMapper.map(postImage, PostImageDTO.class);
        if (postImage.getPost() != null) {
            postImageDTO.setPost(modelMapper.map(postImage.getPost(), PostDTO.class));
        }
        postImageDTO.setImage(new String(postImage.getImage(), StandardCharsets.UTF_8));

        return postImageDTO;
    }

    //из dto в entity
    public PostImage mapToPostImageEntity(PostImageDTO dto) {
        var postImage = modelMapper.map(dto, PostImage.class);
        postImage.setImage(dto.getImage().getBytes(StandardCharsets.UTF_8));
        return postImage;
    }

}
