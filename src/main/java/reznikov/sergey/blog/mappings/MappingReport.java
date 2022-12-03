package reznikov.sergey.blog.mappings;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reznikov.sergey.blog.DTO.ReportDTO;
import reznikov.sergey.blog.DTO.PostDTO;
import reznikov.sergey.blog.DTO.UserDTO;
import reznikov.sergey.blog.entities.Report;
import reznikov.sergey.blog.entities.enums.ReportType;

@Service
public class MappingReport {
    private final ModelMapper modelMapper;

    public MappingReport(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //из entity в dto
    public ReportDTO mapToAppointmentDto(Report report) {
        if (report == null) {
            return null;
        }

        ReportDTO reportDTO = modelMapper.map(report, ReportDTO.class);
        if (report.getUser() != null) {
            reportDTO.setUser(modelMapper.map(report.getUser(), UserDTO.class));
        }
        if (report.getPost() != null) {
            reportDTO.setPost(modelMapper.map(report.getPost(), PostDTO.class));
        }
        reportDTO.setReportType(ReportType.getInRussian(report.getReportType()));

        return reportDTO;
    }

    //из dto в entity
    public Report mapToAppointmentEntity(ReportDTO dto) {
        var report = modelMapper.map(dto, Report.class);
        report.setReportType(ReportType.fromRussian(dto.getReportType()));
        return report;
    }
}
