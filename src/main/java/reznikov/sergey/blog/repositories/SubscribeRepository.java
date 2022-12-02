package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Subscribe;
import reznikov.sergey.blog.entities.User;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    List<Subscribe> findSubscribesBySubscriber(User user);

}
