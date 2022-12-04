package reznikov.sergey.blog.services;

import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.PostImageDTO;
import reznikov.sergey.blog.mappings.MappingPostImage;
import reznikov.sergey.blog.repositories.PostImageRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostImageService {
    PostImageRepository postImageRepository;
    MappingPostImage mappingPostImage;

    public PostImageService(PostImageRepository postImageRepository, MappingPostImage mappingPostImage) {
        this.postImageRepository = postImageRepository;
        this.mappingPostImage = mappingPostImage;
    }

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


    @Transactional
    public void savePostImagesByPost(List<PostImageDTO> postImageDTOList, PostDTO postDTO) throws Exception {
        //Удалим картинки, которых нет в итоговом резе
        var imagesForDeleting =
                findPostImagesByPost(postDTO)
                .stream()
                .filter(rec -> postImageDTOList
                        .stream()
                        .noneMatch(p -> Objects.equals(p.getId(), rec.getId()))).toList();
        imagesForDeleting
                .forEach(rec->postImageRepository
                        .delete(mappingPostImage
                                .mapToPostImageEntity(rec)));

        postImageRepository.saveAll(postImageDTOList
                .stream()
                .map(mappingPostImage::mapToPostImageEntity)
                .collect(Collectors.toList()));
    }
}
