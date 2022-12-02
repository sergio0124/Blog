package reznikov.sergey.blog.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "subscribe")
@Data
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    @ManyToOne
    @JoinColumn(name = "subscriber")
    User subscriber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "influencer")
    User influencer;

}
