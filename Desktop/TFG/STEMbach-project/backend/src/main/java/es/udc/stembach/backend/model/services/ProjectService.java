package es.udc.stembach.backend.model.services;

import es.udc.stembach.backend.model.entities.Biennium;
import es.udc.stembach.backend.model.entities.Project;
import es.udc.stembach.backend.model.entities.UDCTeacher;
import es.udc.stembach.backend.model.exceptions.DuplicateInstanceException;
import es.udc.stembach.backend.model.exceptions.InstanceNotFoundException;

import java.util.List;

public interface ProjectService {
    public Project createProject(Project project, List<Long> udcTeacherIdList, Long bienniumId, Long creatorId) throws InstanceNotFoundException;

    public Biennium createBiennium(String dateRange) throws DuplicateInstanceException;

    public List<Biennium> findAllBienniums();

    public List<UDCTeacher> findUdcTeachersOfProject(Long projectId);

    public Project findProjectDetails(Long projectId) throws InstanceNotFoundException;
}
