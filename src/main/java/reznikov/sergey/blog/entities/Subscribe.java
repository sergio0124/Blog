package reznikov.sergey.blog.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "subscribe")
@Data
public class Subscribe extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "subscriber")
    User subscriber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "influencer")
    User influencer;

}
