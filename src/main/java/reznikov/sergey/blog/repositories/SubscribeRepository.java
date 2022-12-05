package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Subscribe;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {

    List<Subscribe> findSubscribesBySubscriber_Id(Long subscriberId);

    Optional<Subscribe> findSubscribeBySubscriber_IdAndInfluencer_Id(Long subscriber_id, Long influencer_id);
}
