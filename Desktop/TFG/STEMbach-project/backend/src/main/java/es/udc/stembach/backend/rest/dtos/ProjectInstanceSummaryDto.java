package es.udc.stembach.backend.rest.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectInstanceSummaryDto {

    private Long id;
    private String title;
    private List<StudentDto> students;

    public ProjectInstanceSummaryDto() {
    }

    public ProjectInstanceSummaryDto(Long id, String title, List<StudentDto> students) {
        this.id = id;
        this.title = title;
        this.students = students;
    }


}
