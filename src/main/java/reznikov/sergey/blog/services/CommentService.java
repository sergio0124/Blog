package reznikov.sergey.blog.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.CommentDTO;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.mappings.MappingComment;
import reznikov.sergey.blog.repositories.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    MappingComment mappingComment;
    CommentRepository commentRepository;

    public CommentService(MappingComment mappingComment,
                          CommentRepository commentRepository) {
        this.mappingComment = mappingComment;
        this.commentRepository = commentRepository;
    }

    public List<CommentDTO> getCommentsByPostByPage(PostDTO postDTO,
                                                    Pageable pageable) throws Exception {
        if(postDTO.getId()==null){
            throw new Exception("Не получен id поста");
        }
        return commentRepository.findCommentsByPost_Id(postDTO.getId(), pageable)
                .getContent()
                .stream()
                .map(mappingComment::mapToCommentDto)
                .toList();
    }
}
