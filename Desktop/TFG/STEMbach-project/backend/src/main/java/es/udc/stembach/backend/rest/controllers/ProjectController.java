package es.udc.stembach.backend.rest.controllers;

import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import es.udc.stembach.backend.model.services.ProjectService;
import es.udc.stembach.backend.rest.common.ErrorsDto;
import es.udc.stembach.backend.rest.dtos.BienniumDto;
import es.udc.stembach.backend.rest.dtos.ProjectCreationDto;
import es.udc.stembach.backend.rest.dtos.ProjectDetailsDto;
import es.udc.stembach.backend.rest.dtos.UDCTeacherSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static es.udc.stembach.backend.rest.dtos.BienniumConversor.toBienniumDto;
import static es.udc.stembach.backend.rest.dtos.BienniumConversor.toBienniumDtos;
import static es.udc.stembach.backend.rest.dtos.ProjectConversor.toProject;
import static es.udc.stembach.backend.rest.dtos.ProjectConversor.toProjectDetailsDto;
import static es.udc.stembach.backend.rest.dtos.UserConversor.toUDCTeacherSummaryDtos;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final static String INSTANCE_NOT_FOUND_CODE= "project.exceptions.InstanceNotFoundException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ProjectService projectService;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundException(InstanceNotFoundException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(INSTANCE_NOT_FOUND_CODE, null,
                INSTANCE_NOT_FOUND_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @PostMapping("/createProject")
    public ProjectDetailsDto createProject(@RequestAttribute Long userId,
                                           @Validated({ProjectCreationDto.AllValidations.class}) @RequestBody ProjectCreationDto projectCreationDto) throws InstanceNotFoundException {

        ProjectDetailsDto projectDetailsDto = toProjectDetailsDto(projectService.createProject(toProject(projectCreationDto), projectCreationDto.getTeacherIds(), projectCreationDto.getBienniumId(), userId));
        List<UDCTeacherSummaryDto> udcTeacherSummaryDtos = toUDCTeacherSummaryDtos(projectService.findUdcTeachersOfProject(projectDetailsDto.getId()));
        projectDetailsDto.setTeacherList(udcTeacherSummaryDtos);

        return projectDetailsDto;
    }

    @PostMapping("/createBiennium")
    public BienniumDto createBiennium(@RequestParam String dateRange) throws DuplicateInstanceException {
        BienniumDto bienniumDto = toBienniumDto(projectService.createBiennium(dateRange));
        return bienniumDto;
    }

    @GetMapping("/bienniums")
    public List<BienniumDto> findAllBienniums(){
        return toBienniumDtos(projectService.findAllBienniums());
    }

    @GetMapping("/projectDetails/{id}")
    public ProjectDetailsDto findProjectDetails(@PathVariable Long id) throws InstanceNotFoundException {
        ProjectDetailsDto projectDetailsDto = toProjectDetailsDto(projectService.findProjectDetails(id));
        List<UDCTeacherSummaryDto> udcTeacherSummaryDtos = toUDCTeacherSummaryDtos(projectService.findUdcTeachersOfProject(id));

        projectDetailsDto.setTeacherList(udcTeacherSummaryDtos);

        return projectDetailsDto;
    }
}
