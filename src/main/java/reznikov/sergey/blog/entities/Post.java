package reznikov.sergey.blog.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
@Data
public class Post extends BaseEntity {

    @CreationTimestamp
    private Timestamp date = new Timestamp(new Date().getTime());

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String text;

    @OneToMany(mappedBy = "post",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    List<Report> reports = new ArrayList<>();

    @OneToMany(mappedBy = "post",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    List<PostImage> postImages = new ArrayList<>();
}
