package es.udc.stembach.backend.rest.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestCreationDto {

    private List<StudentDto> students;
    private Long projectId;
    private Long schoolId;

    public RequestCreationDto() {
    }

    public RequestCreationDto(List<StudentDto> students, Long projectId, Long schoolId) {
        this.students = students;
        this.projectId = projectId;
        this.schoolId = schoolId;
    }
}
