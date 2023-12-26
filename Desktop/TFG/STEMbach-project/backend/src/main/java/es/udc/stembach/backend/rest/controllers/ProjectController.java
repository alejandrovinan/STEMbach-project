package es.udc.stembach.backend.rest.controllers;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.*;
import es.udc.stembach.backend.model.services.Block;
import es.udc.stembach.backend.model.services.ProjectService;
import es.udc.stembach.backend.model.services.UserService;
import es.udc.stembach.backend.rest.common.ErrorsDto;
import es.udc.stembach.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static es.udc.stembach.backend.rest.dtos.BienniumConversor.toBienniumDto;
import static es.udc.stembach.backend.rest.dtos.BienniumConversor.toBienniumDtos;
import static es.udc.stembach.backend.rest.dtos.ProjectConversor.*;
import static es.udc.stembach.backend.rest.dtos.RequestConversor.toRequestDto;
import static es.udc.stembach.backend.rest.dtos.UserConversor.*;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final static String INSTANCE_NOT_FOUND_CODE= "project.exceptions.InstanceNotFoundException";
    private final static String ALREADY_IN_GROUP_EXCEPTION="project.exceptions.AlreadyInGroupException";
    private final static String MAX_STUDENTS_IN_PROJECT="project.exception.MaxStudentsInProjectException";
    private final static String DUPLICATE_INSTANCE_EXCEPTION="project.exception.DuplicateInstanceException";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @ExceptionHandler(InstanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInstanceNotFoundException(InstanceNotFoundException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(INSTANCE_NOT_FOUND_CODE, new Object[] {exception.getName(), exception.getKey()},
                INSTANCE_NOT_FOUND_CODE, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(StudentAlreadyInGroupException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleStudentAlreadyInGroupException(StudentAlreadyInGroupException e, Locale locale){
        String errorMessage = messageSource.getMessage(ALREADY_IN_GROUP_EXCEPTION, new Object[] {e.getStudentId()},
                ALREADY_IN_GROUP_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(MaxStudentsInProjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorsDto handleMaxStudentsInProjectException(MaxStudentsInProjectException e, Locale locale){
        String errorMessage = messageSource.getMessage(MAX_STUDENTS_IN_PROJECT, new Object[] {e.getProjectId(),e.getStudents()},
                MAX_STUDENTS_IN_PROJECT, locale);

        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(DuplicateInstanceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleDuplicateInstanceException(DuplicateInstanceException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(DUPLICATE_INSTANCE_EXCEPTION, new Object[] {exception.getName(), exception.getKey()},
                DUPLICATE_INSTANCE_EXCEPTION, locale);

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

    @GetMapping("/findAllProjects")
    public BlockDto<ProjectSummaryDto> findAllProjects(@RequestParam(defaultValue = "0") int page){
        Block<Project> projectBlock = projectService.findAllProjects(page, 2);
        return new BlockDto<>(toProjectSummaryDtos(projectBlock.getItems()), projectBlock.getExistMoreItems());
    }

    @GetMapping("/findByCriteria")
    public BlockDto<ProjectSummaryDto> findProjectsByCriteria(
            @RequestParam(required = false) Project.Modality modality,
            @RequestParam(required = false) Project.OfferZone offerZone,
            @RequestParam(required = false) Boolean revised,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Integer maxGroups,
            @RequestParam(required = false) Integer studentsPerGroup,
            @RequestParam(required = false) Long biennium,
            @RequestParam(required = false) Boolean assigned,
            @RequestParam(required = false) List<Long> teachers,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page){

        Block<Project> projectBlock = projectService.findProjectsByCriteria(modality, offerZone, revised, active,
                maxGroups, studentsPerGroup, biennium, assigned, teachers, title, 2, page);
        return new BlockDto<>(toProjectSummaryDtos(projectBlock.getItems()), projectBlock.getExistMoreItems());
    }

    @PostMapping("/approveProject")
    public void approveProject(@RequestBody ProjectRevision projectRevision) throws InstanceNotFoundException {
        projectService.approveProject(projectRevision.getId());
    }

    @PostMapping("/cancelProject")
    public void cancelProject(@RequestBody ProjectRevision projectRevision) throws InstanceNotFoundException {
        projectService.cancelProject(projectRevision.getId());
    }

    @PostMapping("/editProject")
    public ProjectDetailsDto editProject(@RequestAttribute Long userId,
                                         @Validated({ProjectCreationDto.AllValidations.class}) @RequestBody ProjectCreationDto projectCreationDto) throws InstanceNotFoundException {
        ProjectDetailsDto projectDetailsDto = toProjectDetailsDto(projectService.editProject(toProjectWithId(projectCreationDto), projectCreationDto.getTeacherIds(), projectCreationDto.getBienniumId(), userId));
        List<UDCTeacherSummaryDto> udcTeacherSummaryDtos = toUDCTeacherSummaryDtos(projectService.findUdcTeachersOfProject(projectDetailsDto.getId()));
        projectDetailsDto.setTeacherList(udcTeacherSummaryDtos);
        return projectDetailsDto;
    }

    @PostMapping("/requestProject")
    public RequestDto requestProject(@RequestBody RequestCreationDto requestCreationDto) throws InstanceNotFoundException, StudentAlreadyInGroupException, MaxStudentsInProjectException {
        Request request = projectService.requestProject(toStudentList(requestCreationDto.getStudents()), requestCreationDto.getProjectId(), requestCreationDto.getSchoolId());
        RequestDto requestDto = toRequestDto(request);
        requestDto.setStudents(toStudentDtos(userService.findAllStudentsOfGroup(request.getStudentGroup().getId())));
        return requestDto;
    }

    @GetMapping("/getAllProjectRequests/{id}")
    public BlockDto<RequestDto> getAllProjectRequests(@PathVariable Long id,
                                                      @RequestParam int page,
                                                      @RequestParam int size){

        Block<Request> requests = projectService.getAllProjectRequests(id, page, size);
        List<RequestDto> requestDtos = new ArrayList<>();
        RequestDto requestDto = null;

       for(Request r : requests.getItems()){
           requestDto = toRequestDto(r);
           requestDto.setStudents(toStudentDtos(userService.findAllStudentsOfGroup(r.getStudentGroup().getId())));
           requestDtos.add(requestDto);
       }

        return new BlockDto<>(requestDtos, requests.getExistMoreItems());
    }

    @PostMapping("/asignProjects")
    public ResponseEntity.BodyBuilder asignProjects(){
        projectService.asignProjects();
        return ResponseEntity.ok();
    }

    @GetMapping("/findProjectsInstancesSummary")
    public BlockDto<ProjectInstanceSummaryDto> findProjectsByTeacher(@RequestAttribute Long userId,
                                                                     @RequestParam String role,
                                                                     @RequestParam int page,
                                                                     @RequestParam int size){

        Block<ProjectInstance> projectInstanceBlock = projectService.findProjectsInstances(userId, User.RoleType.valueOf(role), page, size);
        List<ProjectInstanceSummaryDto> projectInstanceSummaryDtos = new ArrayList<>();
        ProjectInstanceSummaryDto projectInstanceSummaryDto = null;
        for(ProjectInstance p : projectInstanceBlock.getItems()){
            projectInstanceSummaryDto = toProjectInstanceSummaryDto(p);
            projectInstanceSummaryDto.setStudents(toStudentDtos(userService.findAllStudentsOfGroup(p.getStudentGroup().getId())));
            projectInstanceSummaryDtos.add(projectInstanceSummaryDto);
        }

        return new BlockDto<>(projectInstanceSummaryDtos, projectInstanceBlock.getExistMoreItems());
    }

    @GetMapping("/projectInstanceDetails/{id}")
    public ProjectInstanceDetailsDto findProjectInstanceDetails(@PathVariable Long id) throws InstanceNotFoundException {

        ProjectInstance projectInstance = projectService.findProjectInstanceDetails(id);
        ProjectInstanceDetailsDto projectInstanceDetailsDto = toProjectInstanceDetailsDto(projectInstance);
        projectInstanceDetailsDto.setStudents(toStudentDtos(userService.findAllStudentsOfGroup(projectInstance.getStudentGroup().getId())));
        List<UDCTeacherSummaryDto> udcTeacherSummaryDtos = toUDCTeacherSummaryDtos(projectService.findUdcTeachersOfProjectInstance(id));
        projectInstanceDetailsDto.setTeacherList(udcTeacherSummaryDtos);
        projectInstanceDetailsDto.setCenterSTEMCoordinator(toUserDto(projectService.findCoordinatorOfSchool(projectInstance.getStudentGroup().getSchool().getId())));
        projectInstanceDetailsDto.getCenterSTEMCoordinator().setPassword(null);
        return projectInstanceDetailsDto;
    }

    @PostMapping("/editProjectInstance")
    public ProjectInstanceDetailsDto editProjectInstance(@RequestAttribute Long userId,
                                                         @RequestBody ProjectInstanceModDto p) throws InstanceNotFoundException, EmptyStudentForProjectException {


        ProjectInstance projectInstance = projectService.editProjectInstance(
                new ProjectInstance(p.getId(), p.getTitle(), p.getDescription(), p.getObservations(), p.getModality(), p.getUrl(), p.getOfferZone(), null, null, null, new StudentGroup(p.getStudentGroup().getId(), p.getStudentGroup().getHasProject(), null)),
                p.getTeacherIds(),
                toStudentList(p.getStudents()),
                p.getBienniumId()
        );
        ProjectInstanceDetailsDto projectInstanceDetailsDto = toProjectInstanceDetailsDto(projectInstance);

        projectInstanceDetailsDto.setStudents(toStudentDtos(userService.findAllStudentsOfGroup(projectInstance.getStudentGroup().getId())));
        List<UDCTeacherSummaryDto> udcTeacherSummaryDtos = toUDCTeacherSummaryDtos(projectService.findUdcTeachersOfProjectInstance(p.getId()));
        projectInstanceDetailsDto.setTeacherList(udcTeacherSummaryDtos);
        projectInstanceDetailsDto.setCenterSTEMCoordinator(toUserDto(projectService.findCoordinatorOfSchool(projectInstance.getStudentGroup().getSchool().getId())));
        projectInstanceDetailsDto.getCenterSTEMCoordinator().setPassword(null);
        return projectInstanceDetailsDto;
    }

    @PostMapping("/createSchool_Faculty")
    public Object createFacultyOrSchool(@RequestParam(required = false) int type,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) String location) throws DuplicateInstanceException {
        Object o = projectService.createFacultyOrSchool(type, name, location);
        return o;
    }
}
