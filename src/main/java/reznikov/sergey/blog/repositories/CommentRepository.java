package reznikov.sergey.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Comment;
import reznikov.sergey.blog.entities.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByPost_Id(Long post_id, Pageable pageable);

}
