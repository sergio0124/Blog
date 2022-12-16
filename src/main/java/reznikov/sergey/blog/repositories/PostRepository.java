package reznikov.sergey.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reznikov.sergey.blog.entities.Post;

import java.util.Collection;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findPostsByUser_IdOrderByDateDesc(Long user_id, Pageable pageable);
    Optional<Post> findPostById(Long postId);
    void deleteById(Long id);
    Page<Post> findPostByUser_IdIn(Collection<Long> user_id, Pageable pageable);
    Page<Post> findPostsByTitleContainingIgnoreCase(String title, Pageable pageable);
    @Query(value = "SELECT *, count(*) as num from post p join report r on p.id = r.post_id group by p.id order by num",
            countQuery = "SELECT count(*) FROM post",
            nativeQuery = true)
    Page<Post> findMostReportedPosts(Pageable pageable);


}
