package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Request;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RequestDto {

    private Long id;
    private long date;
    private Request.requestStatus status;
    private Long groupId;
    private Long projectId;
    private String schoolName;
    private List<StudentDto> students;

    public RequestDto() {
    }

    public RequestDto(Long id, long date, Request.requestStatus status, Long groupId, Long projectId, String schoolName, List<StudentDto> students) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.groupId = groupId;
        this.projectId = projectId;
        this.schoolName = schoolName;
        this.students = students;
    }
}

