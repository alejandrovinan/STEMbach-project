package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Project;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectInstanceModDto {

    private Long id;
    private String title;
    private String description;
    private String observations;
    private Project.Modality modality;
    private String url;
    private Project.OfferZone offerZone;
    private Long bienniumId;
    private List<Long> teacherIds;
    private List<StudentDto> students;
    private GroupDto studentGroup;

    public ProjectInstanceModDto() {
    }

    public ProjectInstanceModDto(Long id, String title, String description, String observations, Project.Modality modality, String url, Project.OfferZone offerZone, Long bienniumId, List<Long> teacherIds, List<StudentDto> students, GroupDto studentGroup) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.observations = observations;
        this.modality = modality;
        this.url = url;
        this.offerZone = offerZone;
        this.bienniumId = bienniumId;
        this.teacherIds = teacherIds;
        this.students = students;
        this.studentGroup = studentGroup;
    }
}
