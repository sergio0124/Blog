package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.PostImage;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
