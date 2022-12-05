package reznikov.sergey.blog.services;

import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.SubscribeDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Like;
import reznikov.sergey.blog.entities.Post;
import reznikov.sergey.blog.entities.Subscribe;
import reznikov.sergey.blog.entities.User;
import reznikov.sergey.blog.mappings.MappingSubscribe;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.repositories.SubscribeRepository;
import reznikov.sergey.blog.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscribeService {

    MappingSubscribe mappingSubscribe;
    SubscribeRepository subscribeRepository;
    UserRepository userRepository;
    MappingUser mappingUser;

    public SubscribeService(MappingSubscribe mappingSubscribe, SubscribeRepository subscribeRepository, UserRepository userRepository, MappingUser mappingUser) {
        this.mappingSubscribe = mappingSubscribe;
        this.subscribeRepository = subscribeRepository;
        this.userRepository = userRepository;
        this.mappingUser = mappingUser;
    }

    public List<SubscribeDTO> getUsersSubscribes(UserDTO userDTO) throws Exception{
        return subscribeRepository
                .findSubscribesBySubscriber_Id(userDTO.getId())
                .stream().map(mappingSubscribe::mapToSubscribeDto).toList();

    }

    public void subscribeOrUnsubscribe(UserDTO influencerDTO, User subscriber) throws Exception {
        if (subscriber.getId() == null || influencerDTO.getId() == null) {
            throw new Exception("Неполные данные");
        }

        Subscribe subscribe = subscribeRepository
                .findSubscribeBySubscriber_IdAndInfluencer_Id(subscriber.getId(), influencerDTO.getId())
                .orElse(null);
        if (subscribe == null) {
            User influencer = userRepository
                    .findUserById(influencerDTO.getId()).orElse(null);
            if (influencer == null) {
                throw new Exception("Блоггер не был найден в базе");
            }
            subscribe = new Subscribe();
            subscribe.setInfluencer(influencer);
            subscribe.setSubscriber(subscriber);
            subscribeRepository.save(subscribe);
        } else {
            subscribeRepository.delete(subscribe);
        }
    }


    public Boolean isUserSubscribedTo(User subscriber, UserDTO influencer) throws Exception {
        if (subscriber.getId() == null || influencer.getId() == null) {
            throw new Exception("Информации для получения подписки недостаточно");
        }
        return subscribeRepository
                .findSubscribeBySubscriber_IdAndInfluencer_Id(
                        subscriber.getId(), influencer.getId())
                .isPresent();
    }
}
