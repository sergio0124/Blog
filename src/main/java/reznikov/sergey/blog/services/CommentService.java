package reznikov.sergey.blog.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.CommentDTO;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.entities.Comment;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingComment;
import reznikov.sergey.blog.repositories.CommentRepository;
import reznikov.sergey.blog.repositories.PostRepository;
import reznikov.sergey.blog.repositories.UserRepository;

import java.util.List;

@Service
public class CommentService {

    MappingComment mappingComment;
    CommentRepository commentRepository;
    PostRepository postRepository;
    UserRepository userRepository;

    public CommentService(MappingComment mappingComment, CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.mappingComment = mappingComment;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<CommentDTO> getCommentsByPostByPage(PostDTO postDTO,
                                                    Pageable pageable) throws Exception {
        if (postDTO.getId() == null) {
            throw new Exception("Не получен id поста");
        }
        return commentRepository.findCommentsByPost_Id(postDTO.getId(), pageable)
                .getContent()
                .stream()
                .map(mappingComment::mapToCommentDto)
                .toList();
    }


    public CommentDTO saveComment(CommentDTO commentDTO) throws Exception {
        if (commentDTO.getPost() == null ||
                commentDTO.getPost().getId() == null ||
                commentDTO.getUser() == null ||
                commentDTO.getUser().getId() == null) {
            throw new Exception("Неполные данные");
        }

        Post post = postRepository.findPostById(commentDTO.getPost().getId()).orElse(null);
        User user = userRepository.findUserById(commentDTO.getUser().getId()).orElse(null);

        if (post == null || user == null) {
            throw new Exception("Не удалось найти сущности по указанным данным");
        }

        Comment comment = mappingComment.mapToCommentEntity(commentDTO);
        comment.setPost(post);
        comment.setUser(user);
        return mappingComment
                .mapToCommentDto(
                        commentRepository
                                .save(comment));
    }

    public void deleteComment(CommentDTO commentDTO) throws Exception {
        if (commentDTO.getId() == null) {
            throw new Exception("Недостаточно данных для удаления");
        }
        Comment comment = commentRepository.findById(commentDTO.getId()).orElse(null);
        if (comment == null) {
            throw new Exception("Комментарий не найден в базе данных");
        }
        commentRepository.delete(comment);
    }
}
