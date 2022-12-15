package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import reznikov.sergey.blog.entities.Report;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportByUser_IdAndPost_Id(Long user_id, Long post_id);
}
