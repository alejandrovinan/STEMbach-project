package es.udc.stembach.backend.rest.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDto {

    private Long id;
    private Boolean hasProject;
    private SchoolDto school;

    public GroupDto() {
    }

    public GroupDto(Long id, Boolean hasProject, SchoolDto school) {
        this.id = id;
        this.hasProject = hasProject;
        this.school = school;
    }

    public GroupDto(Boolean hasProject, SchoolDto school) {
        this.hasProject = hasProject;
        this.school = school;
    }
}
