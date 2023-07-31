package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.*;
import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        List<UDCTeacher> udcTeacherList = leadsProjectDao.findAllTeachersByProject(projectId);
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


}
