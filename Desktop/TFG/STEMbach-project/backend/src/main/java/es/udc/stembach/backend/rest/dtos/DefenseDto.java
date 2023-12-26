package es.udc.stembach.backend.rest.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DefenseDto {

    private Long id;
    private Long projectInstanceId;
    private String place;
    private LocalDateTime date;
    private BigDecimal mark;
    private String observations;
    private List<RecordFileDto> recordFileDtos;
    private List<UDCTeacherSummaryDto> teacherSummaryDtos;

    public DefenseDto() {
    }

    public DefenseDto(Long id, Long projectInstanceId, String place, LocalDateTime date, BigDecimal mark, String observations, List<RecordFileDto> recordFileDtos, List<UDCTeacherSummaryDto> teacherSummaryDtos) {
        this.id = id;
        this.projectInstanceId = projectInstanceId;
        this.place = place;
        this.date = date;
        this.mark = mark;
        this.observations = observations;
        this.recordFileDtos = recordFileDtos;
        this.teacherSummaryDtos = teacherSummaryDtos;
    }

    public DefenseDto(Long id, Long projectInstanceId, String place, LocalDateTime date, BigDecimal mark, String observations) {
        this.id = id;
        this.projectInstanceId = projectInstanceId;
        this.place = place;
        this.date = date;
        this.mark = mark;
        this.observations = observations;
    }

    public DefenseDto(Long id, String place, LocalDateTime date, BigDecimal mark, String observations, List<RecordFileDto> recordFileDtos, List<UDCTeacherSummaryDto> teacherSummaryDtos) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.mark = mark;
        this.observations = observations;
        this.recordFileDtos = recordFileDtos;
        this.teacherSummaryDtos = teacherSummaryDtos;
    }

    public DefenseDto(Long id, String place, LocalDateTime date, BigDecimal mark, String observations) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.mark = mark;
        this.observations = observations;
    }
}
