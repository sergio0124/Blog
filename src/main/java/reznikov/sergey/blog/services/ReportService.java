package reznikov.sergey.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.ReportDTO;
import reznikov.sergey.blog.mappings.MappingReport;
import reznikov.sergey.blog.repositories.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final MappingReport mappingReport;
    public ReportDTO saveReport(ReportDTO reportDTO){
        return mappingReport
                .mapToAppointmentDto(reportRepository
                .save(mappingReport.mapToAppointmentEntity(reportDTO)));
    }
}
