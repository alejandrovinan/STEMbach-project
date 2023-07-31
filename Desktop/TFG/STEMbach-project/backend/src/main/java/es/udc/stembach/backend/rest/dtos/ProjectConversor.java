package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Biennium;
import es.udc.stembach.backend.model.entities.Project;

import static es.udc.stembach.backend.rest.dtos.UserConversor.toUDCTeacherSummaryDto;

public class ProjectConversor {

    public ProjectConversor() {
    }

    public final static Project toProject(ProjectCreationDto projectCreationDto){
        return new Project(projectCreationDto.getTitle(), projectCreationDto.getDescription(), projectCreationDto.getObservations(),
                projectCreationDto.getModality(), projectCreationDto.getUrl(), projectCreationDto.getOfferZone(),
                Boolean.FALSE, Boolean.FALSE, projectCreationDto.getMaxGroups(), projectCreationDto.getStudentsPerGroup(),
                null, null);
    }

    public final static ProjectSummaryDto toProjectSummaryDto(Project project){
        return new ProjectSummaryDto(project.getTitle(), project.getModality(), project.getOfferZone(), project.getMaxGroups(), project.getStudentsPerGroup(), project.getBiennium().getId());
    }

    private final static BienniumDto toBienniumDto(Biennium biennium){
        return new BienniumDto(biennium.getId(), biennium.getDateRange());
    }

    public final static ProjectDetailsDto toProjectDetailsDto(Project project){
        return new ProjectDetailsDto(project.getId(), project.getTitle(), project.getDescription(), project.getObservations(),
                project.getModality(), project.getUrl(), project.getOfferZone(), project.getRevised(), project.getActive(),
                project.getMaxGroups(), project.getStudentsPerGroup(), toBienniumDto(project.getBiennium()), toUDCTeacherSummaryDto(project.getCreatedBy()));
    }
}
