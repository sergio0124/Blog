package reznikov.sergey.blog.entities;

import lombok.Data;
import reznikov.sergey.blog.entities.enums.ReportType;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Data
public class Report extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="post_id")
    Post post;

    @ManyToOne
    @JoinColumn(name="user_id")
    User user;

    @Enumerated(EnumType.STRING)
    ReportType reportType;
}
