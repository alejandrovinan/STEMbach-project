package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import es.udc.stembach.backend.model.exceptions.MaxStudentsInProjectException;
import es.udc.stembach.backend.model.exceptions.StudentAlreadyInGroupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Block<Project> findProjectsByCriteria(Project.Modality modality, Project.OfferZone offerZone, Boolean revised,
                                                 Boolean active, Integer maxGroups, Integer studentsPerGroup,
                                                 String biennium, Boolean assigned, int size, int page) {

        Slice<Project> projects= projectDao.findProjectListWithFilters(modality, offerZone, revised, active, maxGroups,
                                                                       studentsPerGroup, biennium, assigned, size, page);

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
    public List<Request> getAllProjectRequests(Long projectId) {
        return null;
    }


}
