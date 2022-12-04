package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Like;
import reznikov.sergey.blog.entities.Post;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Long countByPost_Id(Long post_id);

    Optional<Like> findLikeByPost_IdAndUser_Id(Long post_id, Long user_id);
}
