package reznikov.sergey.blog.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.SubscribeDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Subscribe;

@Service
public class MappingSubscribe {
    private final ModelMapper modelMapper;

    public MappingSubscribe(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //из entity в dto
    public SubscribeDTO mapToSubscribeDto(Subscribe subscribe) {
        if (subscribe == null) {
            return null;
        }

        SubscribeDTO subscribeDTO = modelMapper.map(subscribe, SubscribeDTO.class);
        if (subscribe.getSubscriber() != null) {
            subscribeDTO.setSubscriber(modelMapper.map(subscribe.getSubscriber(), UserDTO.class));
        }
        if (subscribe.getInfluencer() != null) {
            subscribeDTO.setInfluencer(modelMapper.map(subscribe.getInfluencer(), UserDTO.class));
        }

        return subscribeDTO;
    }

    //из dto в entity
    public Subscribe mapToSubscribeEntity(SubscribeDTO dto) {
        return modelMapper.map(dto, Subscribe.class);
    }
}
