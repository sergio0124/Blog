package reznikov.sergey.blog.entities;

import lombok.Data;
import reznikov.sergey.blog.logic.ImageToBlobConverter;

import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Entity
@Table(name = "post_image")
@Data
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    @Column(nullable = false)
    private byte[] image;

}
