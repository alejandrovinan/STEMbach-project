package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    @Autowired
    ProjectDao projectDao;

    @Autowired
    UDCTeacherDao udcTeacherDao;

    @Autowired
    LeadsProjectDao leadsProjectDao;

    @Autowired
    BienniumDao bienniumDao;

    @Autowired
    SchoolDao schoolDao;

    @Autowired
    StudentGroupDao studentGroupDao;

    @Autowired
    StudentDao studentDao;

    @Autowired
    RequestDao requestDao;

    @Autowired
    ProjectInstanceDao projectInstanceDao;

    @Autowired
    LeadsProjectInstanceDao leadsProjectInstanceDao;

    @Autowired
    CenterHistoryDao centerHistoryDao;

    @Override
    public Project createProject(Project project, List<Long> udcTeacherIdList, Long bienniumId, Long creatorId) throws InstanceNotFoundException {

        Optional<UDCTeacher> creator = udcTeacherDao.findById(creatorId);
        if(creator.isEmpty()){
            throw new InstanceNotFoundException("UDCTeacher", UDCTeacher.class);
        }

        Optional<Biennium> biennium = bienniumDao.findById(bienniumId);
        if(biennium.isEmpty()){
            throw new InstanceNotFoundException("Biennium", Biennium.class);
        }

        project.setActive(true);
        project.setRevised(false);
        project.setAssigned(false);
        project.setBiennium(biennium.get());
        project.setCreatedBy(creator.get());
        projectDao.save(project);

        for(Long id: udcTeacherIdList){
            Optional<UDCTeacher> udcTeacher = udcTeacherDao.findById(id);
            leadsProjectDao.save(new LeadsProject(project,udcTeacher.get()));
        }

        return project;
    }

    @Override
    public Biennium createBiennium(String dateRange) throws DuplicateInstanceException {
        Optional<Biennium> biennium = bienniumDao.findByDateRange(dateRange);

        if(biennium.isPresent()){
            throw new DuplicateInstanceException("project.entities.Biennium", biennium.get().getDateRange());
        }

        Biennium newBiennium = new Biennium(dateRange);
        bienniumDao.save(newBiennium);

        return newBiennium;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Biennium> findAllBienniums() {
        Iterable<Biennium> bienniumIterable = bienniumDao.findAll(Sort.by(Sort.Direction.ASC, "dateRange"));
        List<Biennium> bienniums = new ArrayList<>();

        bienniumIterable.forEach(b -> bienniums.add(b));

        return bienniums;
    }

    @Override
    @Transactional(readOnly=true)
    public List<UDCTeacher> findUdcTeachersOfProject(Long projectId) {
        return leadsProjectDao.findAllTeachersByProject(projectId);
    }

    @Override
    @Transactional(readOnly=true)
    public Project findProjectDetails(Long projectId) throws InstanceNotFoundException {

        Optional<Project> project = projectDao.findById(projectId);

        if (project.isEmpty()){
            throw new InstanceNotFoundException("project.entities.project", projectId);
        }

        return project.get();
    }

    @Override
    @Transactional(readOnly = true)
    public Block<Project> findAllProjects(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Project> projects = projectDao.findAll(pageable);

        return new Block<>(projects.getContent(), projects.hasNext());
    }

    @Override
    @Transactional(readOnly = true)
    public Block<Project> findProjectsByCriteria(Project.Modality modality, Project.OfferZone offerZone, Boolean revised,
                                                 Boolean active, Integer maxGroups, Integer studentsPerGroup,
                                                 String biennium, Boolean assigned, List<Long> teachers, String title, int size, int page) {

        Slice<Project> projects = projectDao.findProjectListWithFilters(modality, offerZone, revised, active, maxGroups,
                                                                       studentsPerGroup, biennium, assigned, teachers, title, size, page);

        return new Block<>(projects.getContent(), projects.hasNext());
    }

    @Override
    public void approveProject(Long projectId) throws InstanceNotFoundException {
       Optional<Project> project = projectDao.findById(projectId);

       if(project.isEmpty()){
           throw new InstanceNotFoundException("project.entities.project", projectId);
       }

       project.get().setRevised(true);
    }

    @Override
    public void cancelProject(Long projectId) throws InstanceNotFoundException {
        Optional<Project> project = projectDao.findById(projectId);

        if(project.isEmpty()){
            throw new InstanceNotFoundException("project.entities.project", projectId);
        }

        project.get().setActive(false);
    }

    @Override
    public Project editProject(Project project, List<Long> udcTeacherIdList, Long bienniumId, Long creatorId) throws InstanceNotFoundException{

        Optional<UDCTeacher> udcTeacher = udcTeacherDao.findById(creatorId);

        if(udcTeacher.isEmpty()){
            throw new InstanceNotFoundException("project.entities.udcteacher", creatorId);
        }

        Optional<Project> projectOptional = projectDao.findById(project.getId());

        if(projectOptional.isEmpty()){
            throw new InstanceNotFoundException("project.entities.project", project.getId());
        }

        Optional<Biennium> biennium = bienniumDao.findById(bienniumId);

        if(biennium.isEmpty()){
            throw new InstanceNotFoundException("project.entities.biennium", bienniumId);
        }

        projectOptional.get().setTitle(project.getTitle());
        projectOptional.get().setDescription(project.getDescription());
        projectOptional.get().setObservations(project.getObservations());
        projectOptional.get().setModality(project.getModality());
        projectOptional.get().setUrl(project.getUrl());
        projectOptional.get().setOfferZone(project.getOfferZone());
        projectOptional.get().setMaxGroups(project.getMaxGroups());
        projectOptional.get().setStudentsPerGroup(project.getStudentsPerGroup());
        projectOptional.get().setBiennium(biennium.get());

        List<LeadsProject> leadsProjects = leadsProjectDao.findAllByProjectId(projectOptional.get().getId());
        List<Long> oldUDCTeacherListId = new ArrayList<>();

        for(LeadsProject l: leadsProjects){
            if(!udcTeacherIdList.contains(l.getUdcTeacher().getId())){
                leadsProjectDao.delete(l);
            } else {
                oldUDCTeacherListId.add(l.getUdcTeacher().getId());
            }
        }

        for(Long id: udcTeacherIdList){
            if(!oldUDCTeacherListId.contains(id)){
                Optional<UDCTeacher> udcTeacherTemp = udcTeacherDao.findById(id);
                if(udcTeacherTemp.isEmpty()){
                    throw new InstanceNotFoundException("project.entities.udcteacher", id);
                }

                leadsProjectDao.save(new LeadsProject(projectOptional.get(), udcTeacherTemp.get()));
                oldUDCTeacherListId.add(id);
            }
        }

        return projectOptional.get();
    }

    public Boolean checkGroupInProject(Student student, Long projectId){

        List<Request> requests = requestDao.findAllByStudentGroupId(student.getStudentGroup().getId());

        for(Request r: requests){
            if(r.getProject().getId() == projectId){
                return false;
            }
        }
        return true;
    }

    @Override
    public Request requestProject(List<Student> students, Long projectId, Long schoolId) throws InstanceNotFoundException, StudentAlreadyInGroupException, MaxStudentsInProjectException {
        Optional<School> schoolOptional = schoolDao.findById(schoolId);

        if(schoolOptional.isEmpty()){
            throw new InstanceNotFoundException("project.entities.shool", schoolId);
        }

        Optional<Project> projectOptional = projectDao.findById(projectId);

        if(projectOptional.isEmpty()){
            throw new InstanceNotFoundException("project.entities.project", projectId);
        }

        if(projectOptional.get().getStudentsPerGroup() < students.size()){
            throw new MaxStudentsInProjectException(projectId, students.size());
        }

        List<Student> studentList = new ArrayList<>();
        Long groupIdAux = null;

        for(Student s : students){

            Optional<Student> studentOptional = studentDao.findByDni(s.getDni());

            if(studentOptional.isPresent()){
                studentList.add(studentOptional.get());

                if(studentOptional.get().getStudentGroup() != null){
                    groupIdAux = studentOptional.get().getStudentGroup().getId();

                    if(!checkGroupInProject(studentOptional.get(), projectId)){
                        throw new StudentAlreadyInGroupException(studentOptional.get().getId());
                    }
                }
            } else {
                s.setSchool(schoolOptional.get());
                studentList.add(studentDao.save(s));
            }
        }

        StudentGroup studentGroup = null;
        if(groupIdAux != null){
            studentGroup = studentGroupDao.findById(groupIdAux).get();
        } else {
            studentGroup = studentGroupDao.save(new StudentGroup(false, schoolOptional.get()));
        }

        for(Student st: studentList){
            st.setStudentGroup(studentGroup);
        }

        return requestDao.save(new Request(LocalDateTime.now(), Request.requestStatus.PENDING, studentGroup, projectOptional.get()));
    }

    @Override
    public Block<Request> getAllProjectRequests(Long projectId, int page, int size) {

        boolean existMore = false;
        Slice<Request> requests = requestDao.findAllByProjectId(projectId, page, size);

        return new Block<>(requests.getContent(), requests.hasNext());
    }

    @Override
    public void asignProjects() {
        Random rand = new Random();
        Iterable<Project> projects = projectDao.findAllByRevisedIsTrueAndActiveIsTrueAndAssignedIsFalse();
        List<Request> projectRequests = new ArrayList<>();
        List<LeadsProject> leadsProjects = new ArrayList<>();
        Request request = null;
        int randomNumber = 0;
        ProjectInstance projectInstance = null;

        for(Project p: projects){
            projectRequests = requestDao.findAllByProjectId(p.getId());
            for(int i = 0; (i < p.getMaxGroups() && i < projectRequests.size()); i++){
                randomNumber = rand.nextInt(projectRequests.size());
                request = projectRequests.get(randomNumber);

                while(request.getStudentGroup().getHasProject() && projectRequests.size() != 0){
                    projectRequests.remove(randomNumber);
                    request = projectRequests.get(rand.nextInt(projectRequests.size()));
                }

            }

            if(projectRequests.size() != 0){
                request.getStudentGroup().setHasProject(true);
                request.setStatus(Request.requestStatus.ASSIGNED);
                projectInstance = projectInstanceDao.save(
                        new ProjectInstance(p.getTitle(), p.getDescription(), p.getObservations(), p.getModality(),
                                p.getUrl(), p.getOfferZone(), p.getActive(), p.getBiennium(), p.getCreatedBy(), request.getStudentGroup())
                );

                leadsProjects = leadsProjectDao.findAllByProjectId(p.getId());
                for(LeadsProject l: leadsProjects){
                    leadsProjectInstanceDao.save(new LeadsProjectInstance(projectInstance, l.getUdcTeacher()));
                }

                p.setAssigned(true);
            }
        }
    }

    @Override
    public Block<ProjectInstance> findProjectsInstances(Long id, User.RoleType roleType, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProjectInstance> projectInstances = null;

        switch (roleType){
            case CENTERSTEMCOORDINATOR -> projectInstances = projectInstanceDao.findAllProjectInstacesByCenterStemCoordinator(id, pageable);
            case UDCTEACHER -> projectInstances = projectInstanceDao.findAllProjectInstancesByUdcTeacherLead(id, pageable);
            case STEMCOORDINATOR -> projectInstances = projectInstanceDao.findAll(pageable);
        }
        return new Block<>(projectInstances.getContent(), projectInstances.hasNext());
    }

    @Override
    public ProjectInstance findProjectInstanceDetails(Long projectInstanceId) throws InstanceNotFoundException {

        Optional<ProjectInstance> projectInstance = projectInstanceDao.findById(projectInstanceId);

        if(projectInstance.isEmpty()){
            throw new InstanceNotFoundException("project.entities.projectInstance", projectInstanceId);
        }

        return projectInstance.get();
    }

    @Override
    @Transactional(readOnly=true)
    public List<UDCTeacher> findUdcTeachersOfProjectInstance(Long projectId) {
        return leadsProjectInstanceDao.findAllTeachersByProject(projectId);
    }

    @Override
    public CenterSTEMCoordinator findCoordinatorOfSchool(Long schoolId) throws InstanceNotFoundException {
        Optional<CenterHistory> centerHistory = centerHistoryDao.findBySchoolIdAndEndDateIsNull(schoolId);

        if(centerHistory.isEmpty()){
            throw new InstanceNotFoundException("project.entities.centerHistory", schoolId);
        }

        return centerHistory.get().getCenterSTEMCoordinator();
    }

    @Override
    public ProjectInstance editProjectInstance(ProjectInstance project, List<Long> udcTeacherIdList, List<Student> students, Long bienniumId) throws InstanceNotFoundException, EmptyStudentForProjectException {

        Optional<Biennium> biennium = bienniumDao.findById(bienniumId);

        if(biennium.isEmpty()){
            throw new InstanceNotFoundException("project.entities.biennium", bienniumId);
        }

        Optional<ProjectInstance> projectInstance = projectInstanceDao.findById(project.getId());

        if(projectInstance.isEmpty()){
            throw new InstanceNotFoundException("project.entities.projectInstance", project.getId());
        }

        projectInstance.get().setTitle(project.getTitle());
        projectInstance.get().setDescription(project.getDescription());
        projectInstance.get().setObservations(project.getObservations());
        projectInstance.get().setModality(project.getModality());
        projectInstance.get().setUrl(project.getUrl());
        projectInstance.get().setOfferZone(project.getOfferZone());
        projectInstance.get().setBiennium(biennium.get());

        List<LeadsProjectInstance> leadsProjectInstances = leadsProjectInstanceDao.findAllByProjectInstanceId(projectInstance.get().getId());
        List<Long> oldUDCTeacherListId = new ArrayList<>();

        for(LeadsProjectInstance l: leadsProjectInstances){
            if(!udcTeacherIdList.contains(l.getUdcTeacher().getId())){
                leadsProjectInstanceDao.delete(l);
            } else {
                oldUDCTeacherListId.add(l.getUdcTeacher().getId());
            }
        }

        for(Long id: udcTeacherIdList){
            if(!oldUDCTeacherListId.contains(id)){
                Optional<UDCTeacher> udcTeacherTemp = udcTeacherDao.findById(id);
                if(udcTeacherTemp.isEmpty()){
                    throw new InstanceNotFoundException("project.entities.udcteacher", id);
                }

                leadsProjectInstanceDao.save(new LeadsProjectInstance(projectInstance.get(), udcTeacherTemp.get()));
                oldUDCTeacherListId.add(id);
            }
        }

        if(students.size() <= 0){
            throw new EmptyStudentForProjectException(projectInstance.get().getId());
        }

        Iterable<Student> studentsIterable = studentDao.findByStudentGroupId(project.getStudentGroup().getId());
        Optional<StudentGroup> studentGroup = studentGroupDao.findById(project.getStudentGroup().getId());

        for(Student s: studentsIterable){
            boolean found = false;
            for(Student student : students){
                if(s.getDni().equals(student.getDni())){
                    found = true;
                    break;
                }
            }
            if(!found){
                studentDao.delete(s);
            }
        }

        for(Student s: students){
            Optional<Student> student = studentDao.findByDni(s.getDni());
            if(student.isEmpty()){
                studentDao.save(new Student(s.getName(), s.getSurname(), s.getSecondSurname(), s.getRole(), project.getStudentGroup(),
                        studentGroup.get().getSchool(), s.getDni()));
            }
        }

        return projectInstance.get();
    }

}
