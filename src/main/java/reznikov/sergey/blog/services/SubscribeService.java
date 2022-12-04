package reznikov.sergey.blog.services;

import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.SubscribeDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.mappings.MappingSubscribe;
import reznikov.sergey.blog.mappings.MappingUser;
import reznikov.sergey.blog.repositories.SubscribeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscribeService {

    MappingSubscribe mappingSubscribe;
    SubscribeRepository subscribeRepository;
    MappingUser mappingUser;

    public SubscribeService(MappingSubscribe mappingSubscribe, SubscribeRepository subscribeRepository, MappingUser mappingUser) {
        this.mappingSubscribe = mappingSubscribe;
        this.subscribeRepository = subscribeRepository;
        this.mappingUser = mappingUser;
    }

    public List<SubscribeDTO> getUsersSubscribes(UserDTO userDTO) throws Exception{
        return subscribeRepository
                .findSubscribesBySubscriber_Id(userDTO.getId())
                .stream().map(mappingSubscribe::mapToSubscribeDto).toList();

    }
}
