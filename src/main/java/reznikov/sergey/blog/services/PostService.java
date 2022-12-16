package reznikov.sergey.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.SubscribeDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.entities.enums.ReportType;
import reznikov.sergey.blog.entities.enums.Role;
import reznikov.sergey.blog.mappings.MappingPost;
import reznikov.sergey.blog.mappings.MappingSubscribe;
import reznikov.sergey.blog.repositories.PostRepository;
import reznikov.sergey.blog.repositories.ReportRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MappingPost mappingPost;
    private final MappingSubscribe mappingSubscribe;
    private final ReportRepository reportRepository;


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
        return mappingPost
                .mapToPostDto(postRepository
                        .save(mappingPost
                                .mapToPostEntity(postDTO)));
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
        if (!Objects.equals(post.getUser().getId(), userDTO.getId())
                && !userDTO.getRoles().contains(Role.ADMIN.name())) {
            throw new Exception("Чужие посты может удалить только создатель или админ");
        }
        postRepository.delete(post);
    }


    public List<PostDTO> getMostReportedPosts(Pageable pageable) {
        List<PostDTO> posts = postRepository
                .findMostReportedPosts(pageable)
                .getContent()
                .stream()
                .map(mappingPost::mapToPostDto)
                .toList();
        posts.forEach(rec -> {
            rec.setReportCount(reportRepository
                    .countReportsByPost_Id(rec.getId()));
            rec.setReportType(ReportType.getInRussian(reportRepository.getReportTypeToPost(rec.getId())));
        });
        return posts;
    }

}
