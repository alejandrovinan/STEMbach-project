package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Project;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDetailsDto {
    private Long id;
    private String title;
    private String description;
    private String observations;
    private Project.Modality modality;
    private String url;
    private Project.OfferZone offerZone;
    private Boolean revised;
    private Boolean active;
    private Integer maxGroups;
    private Integer studentsPerGroup;
    private BienniumDto biennium;
    private UDCTeacherSummaryDto createdBy;
    private Boolean assigned;
    private List<UDCTeacherSummaryDto> teacherList;

    public ProjectDetailsDto(Long id, String title, String description, String observations, Project.Modality modality,
                             String url, Project.OfferZone offerZone, Boolean revised, Boolean active, Integer maxGroups,
                             Integer studentsPerGroup, BienniumDto biennium, UDCTeacherSummaryDto createdBy, Boolean assigned) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.observations = observations;
        this.modality = modality;
        this.url = url;
        this.offerZone = offerZone;
        this.revised = revised;
        this.active = active;
        this.maxGroups = maxGroups;
        this.studentsPerGroup = studentsPerGroup;
        this.biennium = biennium;
        this.createdBy = createdBy;
        this.assigned = assigned;
        this.teacherList = new ArrayList<>();
    }
}
