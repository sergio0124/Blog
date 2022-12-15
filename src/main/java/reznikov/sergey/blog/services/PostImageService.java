package reznikov.sergey.blog.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.PostImageDTO;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.mappings.MappingPost;
import reznikov.sergey.blog.mappings.MappingPostImage;
import reznikov.sergey.blog.repositories.PostImageRepository;
import reznikov.sergey.blog.repositories.PostRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostImageService {
    private final PostImageRepository postImageRepository;
    private final MappingPostImage mappingPostImage;
    private final PostRepository postRepository;


    public List<PostImageDTO> findPostImagesByPost(PostDTO postDTO) {
        if (postDTO.getId() == null) {
            new ArrayList<>();
        }
        return postImageRepository
                .findPostImageByPost_Id(postDTO.getId())
                .stream()
                .map(mappingPostImage::mapToPostImageDto)
                .collect(Collectors.toList());
    }


    public void savePostImagesByPost(List<PostImageDTO> postImageDTOList, PostDTO postDTO) {
        //Удалим картинки, которых нет в итоговом резе
        var imagesForDeleting =
                findPostImagesByPost(postDTO)
                        .stream()
                        .filter(rec -> postImageDTOList
                                .stream()
                                .noneMatch(p -> Objects.equals(p.getId(), rec.getId()))).toList();

        Post post = postRepository.findById(postDTO.getId()).orElse(null);
        if (post == null) {
            return;
        }
        post.setPostImages(new ArrayList<>(post.getPostImages()));

        for (var img : imagesForDeleting) {
            post.getPostImages().removeIf(rec -> rec.getId() == img.getId());
            postImageRepository.deleteById(img.getId());
        }

        post.getPostImages().addAll(postImageDTOList.stream().filter(rec -> rec.getId() == null)
                .map(mappingPostImage::mapToPostImageEntity).toList());

        postRepository.save(post);
    }
}
