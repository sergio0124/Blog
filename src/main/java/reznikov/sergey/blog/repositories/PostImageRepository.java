package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.PostImage;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findPostImageByPost_Id(Long post_id);
}
