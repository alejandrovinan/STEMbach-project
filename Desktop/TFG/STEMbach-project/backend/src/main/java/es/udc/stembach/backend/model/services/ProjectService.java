package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.*;

import java.util.List;

public interface ProjectService {
    public Project createProject(Project project, List<Long> udcTeacherIdList, Long bienniumId, Long creatorId) throws InstanceNotFoundException;

    public Biennium createBiennium(String dateRange) throws DuplicateInstanceException;

    public List<Biennium> findAllBienniums();

    public List<UDCTeacher> findUdcTeachersOfProject(Long projectId);

    public Project findProjectDetails(Long projectId) throws InstanceNotFoundException;

    public Block<Project> findAllProjects(int page, int size);

    public Block<Project> findProjectsByCriteria(Project.Modality modality, Project.OfferZone offerZone, Boolean revised,
                                                 Boolean active, Integer maxGroups, Integer studentsPerGroup, Long biennium,
                                                 Boolean assigned, List<Long> teachers, String title, int size, int page);

    public void approveProject(Long projectId) throws InstanceNotFoundException;

    public void cancelProject(Long projectId) throws InstanceNotFoundException;

    public Project editProject(Project project, List<Long> udcTeacherIdList, Long bienniumId, Long creatorId) throws InstanceNotFoundException;

    public Request requestProject(List<Student> students, Long projectId, Long school) throws InstanceNotFoundException, StudentAlreadyInGroupException, MaxStudentsInProjectException;

    public Block<Request> getAllProjectRequests(Long projectId, int page, int size);

    public void asignProjects();

    public Block<ProjectInstance> findProjectsInstances(Long id, User.RoleType roleType, int page, int size);

    public ProjectInstance findProjectInstanceDetails(Long projectInstanceId) throws InstanceNotFoundException;

    public List<UDCTeacher> findUdcTeachersOfProjectInstance(Long projectId);

    public CenterSTEMCoordinator findCoordinatorOfSchool(Long schoolId) throws InstanceNotFoundException;

    public ProjectInstance editProjectInstance(ProjectInstance project, List<Long> udcTeacherIdList, List<Student> students, Long bienniumId) throws InstanceNotFoundException, EmptyStudentForProjectException;

    Object createFacultyOrSchool(int type, String name, String location) throws DuplicateInstanceException;
}
