package reznikov.sergey.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Post;


public interface PostRepository extends JpaRepository<Post, Long> {


    Page<Post> findPostsByUser_IdOrderByDateDesc(Long user_id, Pageable pageable);
}
