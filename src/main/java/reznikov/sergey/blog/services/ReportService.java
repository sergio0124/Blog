package reznikov.sergey.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.ReportDTO;
import reznikov.sergey.blog.entities.Report;
import reznikov.sergey.blog.entities.enums.ReportType;
import reznikov.sergey.blog.mappings.MappingReport;
import reznikov.sergey.blog.repositories.ReportRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MappingReport mappingReport;

    public ReportDTO saveReport(ReportDTO reportDTO) {
        Report report = reportRepository
                .findReportByUser_IdAndPost_Id(reportDTO.getUser().getId(), reportDTO.getPost().getId())
                .orElse(null);
        if (report == null) {
            report = mappingReport.mapToAppointmentEntity(reportDTO);
        }
        report.setReportType(ReportType.fromRussian(reportDTO.getReportType()));

        return mappingReport
                .mapToAppointmentDto(reportRepository
                        .save(report));
    }


    public Long getReportCount(Long postId) {
        return reportRepository.countReportsByPost_Id(postId);
    }

    public ReportType getReportType(Long postId) {
        return reportRepository.getReportTypeToPost(postId);
    }

    @Transactional
    public void deleteReportsByPostId(Long postId) {
        reportRepository.deleteReportsByPost_Id(postId);
    }
}
