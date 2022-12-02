package reznikov.sergey.blog.entities;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long Id;

    @ManyToOne
    @JoinColumn(name="post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    Post user;

    @CreatedDate
    Timestamp creationDate = new Timestamp(new Date().getTime());

    @Column(nullable = false)
    String text;

}
