package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Like;
import reznikov.sergey.blog.entities.Post;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Long countByPost(Post post);

}
