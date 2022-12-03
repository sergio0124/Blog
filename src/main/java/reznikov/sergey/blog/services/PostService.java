package reznikov.sergey.blog.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.mappings.MappingPost;
import reznikov.sergey.blog.repositories.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    PostRepository postRepository;
    MappingPost mappingPost;

    public PostService(PostRepository postRepository, MappingPost mappingPost) {
        this.postRepository = postRepository;
        this.mappingPost = mappingPost;
    }

    public List<PostDTO> findPostsByUser(UserDTO userDTO, Pageable pageable) {
        return postRepository
                .findPostsByUser_IdOrderByDateDesc(userDTO.getId(), pageable)
                .getContent()
                .stream()
                .map(mappingPost::mapToAppointmentDto)
                .collect(Collectors.toList());
    }
}
