package reznikov.sergey.blog.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "post_image")
@Data
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "post")
    Post post;

    @Lob
    byte[] image;
}
