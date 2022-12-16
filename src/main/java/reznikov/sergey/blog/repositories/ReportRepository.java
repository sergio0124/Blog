package reznikov.sergey.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import reznikov.sergey.blog.entities.Report;
import reznikov.sergey.blog.entities.enums.ReportType;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findReportByUser_IdAndPost_Id(Long user_id, Long post_id);
    Long countReportsByPost_Id(Long post_id);
    @Query(nativeQuery = true,
    value = "select report_type from report r where post_id = ?1 group by r.report_type order by count(*) desc limit 1")
    ReportType getReportTypeToPost(Long postId);

    void deleteReportsByPost_Id(Long post_id);
}
