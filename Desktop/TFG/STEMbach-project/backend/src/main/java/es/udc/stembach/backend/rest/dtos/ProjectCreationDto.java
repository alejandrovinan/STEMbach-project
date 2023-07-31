package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Project;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
public class ProjectCreationDto {


    public interface AllValidations {}

    private String title;
    private String description;
    private String observations;
    private Project.Modality modality;
    private String url;
    private Project.OfferZone offerZone;
    private Integer maxGroups;
    private Integer studentsPerGroup;
    private Long bienniumId;
    private List<Long> teacherIds;

    public ProjectCreationDto() {
    }

    public ProjectCreationDto(String title, String description, String observations, Project.Modality modality, String url, Project.OfferZone offerZone, Integer maxGroups, Integer studentsPerGroup, Long bienniumId, List<Long> teacherIds) {
        this.title = title;
        this.description = description;
        this.observations = observations;
        this.modality = modality;
        this.url = url;
        this.offerZone = offerZone;
        this.maxGroups = maxGroups;
        this.studentsPerGroup = studentsPerGroup;
        this.bienniumId = bienniumId;
        this.teacherIds = teacherIds;
    }

    @NotNull(groups={AllValidations.class})
    @Size(min=1, max=100, groups={AllValidations.class})
    public String getTitle() {
        return title;
    }

    @NotNull(groups={AllValidations.class})
    @Size(min=1, max=200, groups={AllValidations.class})
    public String getDescription() {
        return description;
    }

    public String getObservations() {
        return observations;
    }

    @NotNull(groups={AllValidations.class})
    public Project.Modality getModality() {
        return modality;
    }

    public String getUrl() {
        return url;
    }

    @NotNull(groups={AllValidations.class})
    public Project.OfferZone getOfferZone() {
        return offerZone;
    }

    @NotNull(groups={AllValidations.class})
    @Min(1)
    public Integer getMaxGroups() {
        return maxGroups;
    }

    @NotNull(groups={AllValidations.class})
    @Min(1)
    public Integer getStudentsPerGroup() {
        return studentsPerGroup;
    }

    public Long getBienniumId() {
        return bienniumId;
    }

    public List<Long> getTeacherIds() {
        return teacherIds;
    }
}
