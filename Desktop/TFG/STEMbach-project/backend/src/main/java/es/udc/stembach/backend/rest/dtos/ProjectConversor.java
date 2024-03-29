package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Biennium;
import es.udc.stembach.backend.model.entities.Project;
import es.udc.stembach.backend.model.entities.ProjectInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static es.udc.stembach.backend.rest.dtos.UserConversor.toUDCTeacherSummaryDto;

public class ProjectConversor {

    public ProjectConversor() {
    }

    public final static Project toProject(ProjectCreationDto projectCreationDto){
        return new Project(projectCreationDto.getTitle(), projectCreationDto.getDescription(), projectCreationDto.getObservations(),
                projectCreationDto.getModality(), projectCreationDto.getUrl(), projectCreationDto.getOfferZone(),
                Boolean.FALSE, Boolean.FALSE, projectCreationDto.getMaxGroups(), projectCreationDto.getStudentsPerGroup(),
                null, null, Boolean.FALSE);
    }

    public final static Project toProjectWithId(ProjectCreationDto projectCreationDto){
        return new Project(projectCreationDto.getId(), projectCreationDto.getTitle(), projectCreationDto.getDescription(), projectCreationDto.getObservations(),
                projectCreationDto.getModality(), projectCreationDto.getUrl(), projectCreationDto.getOfferZone(),
                Boolean.FALSE, Boolean.FALSE, projectCreationDto.getMaxGroups(), projectCreationDto.getStudentsPerGroup(),
                null, null, Boolean.FALSE);
    }

    public final static ProjectSummaryDto toProjectSummaryDto(Project project){
        return new ProjectSummaryDto(project.getId(), project.getTitle(), project.getModality(), project.getOfferZone(), project.getMaxGroups(), project.getStudentsPerGroup(), project.getBiennium().getId());
    }

    public final static List<ProjectSummaryDto> toProjectSummaryDtos(List<Project> projects){

        List<ProjectSummaryDto> projectSummaryDtos = new ArrayList<>();
        projects.stream().map(p -> projectSummaryDtos.add(toProjectSummaryDto(p))).collect(Collectors.toList());

        return projectSummaryDtos;
    }

    private final static BienniumDto toBienniumDto(Biennium biennium){
        return new BienniumDto(biennium.getId(), biennium.getDateRange());
    }

    public final static ProjectDetailsDto toProjectDetailsDto(Project project){
        return new ProjectDetailsDto(project.getId(), project.getTitle(), project.getDescription(), project.getObservations(),
                project.getModality(), project.getUrl(), project.getOfferZone(), project.getRevised(), project.getActive(),
                project.getMaxGroups(), project.getStudentsPerGroup(), toBienniumDto(project.getBiennium()), toUDCTeacherSummaryDto(project.getCreatedBy()), project.getAssigned());
    }

    public final static ProjectInstanceDto toProjectInstanceDto(ProjectInstance p){
        return new ProjectInstanceDto(p.getId(), p.getTitle(), p.getDescription(), p.getObservations(), p.getModality(), p.getUrl(), p.getOfferZone(), p.getActive(), null, null, null);
    }

    public final static ProjectInstanceSummaryDto toProjectInstanceSummaryDto(ProjectInstance p){
        return new ProjectInstanceSummaryDto(p.getId(), p.getTitle(), null);
    }

    public final static ProjectInstanceDetailsDto toProjectInstanceDetailsDto(ProjectInstance projectInstance){
        return new ProjectInstanceDetailsDto(projectInstance.getId(), projectInstance.getTitle(), projectInstance.getDescription(), projectInstance.getObservations(), projectInstance.getModality(),
                projectInstance.getUrl(), projectInstance.getOfferZone(), projectInstance.getActive(), toBienniumDto(projectInstance.getBiennium()), toUDCTeacherSummaryDto(projectInstance.getCreatedBy()), null, null);
    }
}
