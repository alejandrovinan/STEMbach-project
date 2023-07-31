package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Project;
import lombok.Data;

@Data
public class ProjectSummaryDto {

    private String title;
    private Project.Modality modality;
    private Project.OfferZone offerZone;
    private Integer maxGroups;
    private Integer studentsPerGroup;
    private Long bienniumId;

    public ProjectSummaryDto() {
    }

    public ProjectSummaryDto(String title, Project.Modality modality, Project.OfferZone offerZone, Integer maxGroups, Integer studentsPerGroup, Long bienniumId) {
        this.title = title;
        this.modality = modality;
        this.offerZone = offerZone;
        this.maxGroups = maxGroups;
        this.studentsPerGroup = studentsPerGroup;
        this.bienniumId = bienniumId;
    }
}
