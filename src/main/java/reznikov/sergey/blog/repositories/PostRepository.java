package reznikov.sergey.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.entities.User;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {


    Page<Post> findPostsByUser_IdOrderByDateDesc(Long user_id, Pageable pageable);

    Optional<Post> findPostById(Long postId);

    void deleteById(Long id);


    Page<Post> findPostByUser_IdIn(Collection<Long> user_id, Pageable pageable);


    Page<Post> findPostsByTitleContainingIgnoreCase(String title, Pageable pageable);
}
