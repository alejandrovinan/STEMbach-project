package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Project;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectInstanceDto {

    private Long id;
    private String title;
    private String description;
    private String observations;
    private Project.Modality modality;
    private String url;
    private Project.OfferZone offerZone;
    private Boolean active;
    private BienniumDto biennium;
    private UDCTeacherSummaryDto createdBy;
    private List<StudentDto> students;

    public ProjectInstanceDto() {
    }

    public ProjectInstanceDto(Long id, String title, String description, String observations, Project.Modality modality, String url, Project.OfferZone offerZone, Boolean active, BienniumDto biennium, UDCTeacherSummaryDto createdBy, List<StudentDto> students) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.observations = observations;
        this.modality = modality;
        this.url = url;
        this.offerZone = offerZone;
        this.active = active;
        this.biennium = biennium;
        this.createdBy = createdBy;
        this.students = students;
    }
}
