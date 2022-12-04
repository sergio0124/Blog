package reznikov.sergey.blog.services;

import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Like;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingLike;
import reznikov.sergey.blog.mappings.MappingPost;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.repositories.LikeRepository;
import reznikov.sergey.blog.repositories.PostRepository;
import reznikov.sergey.blog.repositories.UserRepository;

@Service
public class LikeService {

    MappingLike mappingLike;
    LikeRepository likeRepository;

    PostRepository postRepository;
    UserRepository userRepository;

    public LikeService(MappingLike mappingLike, LikeRepository likeRepository,
                       PostRepository postRepository, UserRepository userRepository) {
        this.mappingLike = mappingLike;
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Long getPostLikesCount(PostDTO postDTO) throws Exception {
        if (postDTO.getId() == null) {
            throw new Exception("Не указан id поста");
        }
        return likeRepository.countByPost_Id(postDTO.getId());
    }

    public Boolean isPostLiked(PostDTO post, UserDTO userDTO) throws Exception {
        if (post.getId() == null || userDTO.getId() == null) {
            throw new Exception("Информации для получения дайка недостаточно");
        }
        return likeRepository
                .findLikeByPost_IdAndUser_Id(post.getId(), userDTO.getId())
                .isPresent();
    }


    public void likeOrUnlike(PostDTO postDTO, User user) throws Exception {
        if (user.getId() == null || postDTO.getId() == null) {
            throw new Exception("Неполные данные");
        }
        Like like = likeRepository
                .findLikeByPost_IdAndUser_Id(postDTO.getId(), user.getId())
                .orElse(null);
        if (like == null) {
            Post post = postRepository.findPostById(postDTO.getId()).orElse(null);
            if (post == null) {
                throw new Exception("Пост не был найден в базе");
            }
            like = new Like();
            like.setPost(post);
            like.setUser(user);
            likeRepository.save(like);
        } else {
            likeRepository.delete(like);
        }
    }
}
