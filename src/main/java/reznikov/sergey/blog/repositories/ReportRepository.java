package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
