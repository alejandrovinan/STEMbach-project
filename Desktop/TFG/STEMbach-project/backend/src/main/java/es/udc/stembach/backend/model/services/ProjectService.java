package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import es.udc.stembach.backend.model.exceptions.MaxStudentsInProjectException;
import es.udc.stembach.backend.model.exceptions.StudentAlreadyInGroupException;

import java.util.List;

public interface ProjectService {
    public Project createProject(Project project, List<Long> udcTeacherIdList, Long bienniumId, Long creatorId) throws InstanceNotFoundException;

    public Biennium createBiennium(String dateRange) throws DuplicateInstanceException;

    public List<Biennium> findAllBienniums();

    public List<UDCTeacher> findUdcTeachersOfProject(Long projectId);

    public Project findProjectDetails(Long projectId) throws InstanceNotFoundException;

    public Block<Project> findProjectsByCriteria(Project.Modality modality, Project.OfferZone offerZone, Boolean revised,
                                                 Boolean active, Integer maxGroups, Integer studentsPerGroup, String biennium,
                                                 Boolean assigned, int size, int page);

    public void approveProject(Long projectId) throws InstanceNotFoundException;

    public void cancelProject(Long projectId) throws InstanceNotFoundException;

    public Project editProject(Project project, List<Long> udcTeacherIdList, Long bienniumId, Long creatorId) throws InstanceNotFoundException;

    public Request requestProject(List<Student> students, Long projectId, Long school) throws InstanceNotFoundException, StudentAlreadyInGroupException, MaxStudentsInProjectException;

    public List<Request> getAllProjectRequests(Long projectId);
}
