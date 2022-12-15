package reznikov.sergey.blog.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.SubscribeDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.mappings.MappingPost;
import reznikov.sergey.blog.mappings.MappingSubscribe;
import reznikov.sergey.blog.repositories.PostRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {
    PostRepository postRepository;
    MappingPost mappingPost;
    MappingSubscribe mappingSubscribe;

    public PostService(PostRepository postRepository, MappingPost mappingPost, MappingSubscribe mappingSubscribe) {
        this.postRepository = postRepository;
        this.mappingPost = mappingPost;
        this.mappingSubscribe = mappingSubscribe;
    }

    public List<PostDTO> findPostsByUser(UserDTO userDTO, Pageable pageable) {
        return postRepository
                .findPostsByUser_IdOrderByDateDesc(userDTO.getId(), pageable)
                .getContent()
                .stream()
                .map(mappingPost::mapToPostDto)
                .collect(Collectors.toList());
    }

    public PostDTO findPostById(Long postId) {
        Post post = postRepository.findPostById(postId).orElse(null);
        if (post == null) {
            return null;
        } else {
            return mappingPost.mapToPostDto(post);
        }
    }

    public PostDTO savePost(PostDTO postDTO) throws Exception {
        if (postDTO.getDescription() == null || postDTO.getDescription().length() == 0 ||
                postDTO.getText() == null || postDTO.getText().length() == 0 ||
                postDTO.getTitle() == null || postDTO.getTitle().length() == 0) {
            throw new Exception("Неполные данные, заполните недостающие поля");
        }
        Post post = postRepository.findPostById(postDTO.getId()).orElse(null);
        if (post == null) {
            return null;
        } else {
            post.setTitle(postDTO.getTitle());
            post.setText(postDTO.getText());
            post.setDescription(postDTO.getDescription());
            post.setDate(new Timestamp(postDTO.getDate().getTime()));
        }
        return mappingPost
                .mapToPostDto(postRepository
                        .save(post));
    }

    public List<PostDTO> getPostsBySubscribesByPages(List<SubscribeDTO> subscribeDTOList,
                                                     Pageable pageable) {
        var influencers = subscribeDTOList
                .stream()
                .map(rec -> rec.getInfluencer().getId())
                .toList();
        return postRepository.findPostByUser_IdIn(
                        influencers,
                        pageable)
                .getContent()
                .stream()
                .map(mappingPost::mapToPostDto).toList();

    }

    public List<PostDTO> searchPostsByTitle(String search, Pageable pageable) {
        return postRepository
                .findPostsByTitleContainingIgnoreCase(search, pageable)
                .stream()
                .map(mappingPost::mapToPostDto)
                .toList();
    }


    public void deletePost(PostDTO postDTO, UserDTO userDTO) throws Exception {
        if (postDTO.getId() == null) {
            throw new Exception("Не передан id поста");
        }
        Post post = postRepository.findPostById(postDTO.getId())
                .orElseThrow(null);
        if (post == null) {
            throw new Exception("Пост не найден");
        }
        if (!Objects.equals(post.getUser().getId(), userDTO.getId())) {
            throw new Exception("Чужие посты может удалить только создатель или админ");
        }
        postRepository.delete(post);
    }
}
