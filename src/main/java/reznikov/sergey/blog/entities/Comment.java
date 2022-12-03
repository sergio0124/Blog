package reznikov.sergey.blog.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "comment")
@Data
public class Comment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @CreatedDate
    Timestamp creationDate = new Timestamp(new Date().getTime());

    @Column(nullable = false)
    String text;

}
