package reznikov.sergey.blog.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "lke")
@Data
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    Post user;

}
