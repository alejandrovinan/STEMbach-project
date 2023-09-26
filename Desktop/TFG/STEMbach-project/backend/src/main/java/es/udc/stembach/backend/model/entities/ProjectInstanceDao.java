package es.udc.stembach.backend.model.entities;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProjectInstanceDao extends PagingAndSortingRepository<ProjectInstance, Long> {

    @Query("SELECT NEW es.udc.stembach.backend.model.entities.ProjectInstance(p.id, p.title, p.description, p.observations, p.modality, p.url, p.offerZone, p.active, p.biennium, p.createdBy, p.studentGroup) " +
            "FROM ProjectInstance p JOIN LeadsProjectInstance l on p.id = l.projectInstance.id " +
            "WHERE l.udcTeacher.id = :udcTeacherId " +
            "ORDER BY p.title ")
    public Page<ProjectInstance> findAllProjectInstancesByUdcTeacherLead(Long udcTeacherId, Pageable pageable);

    @Query("SELECT NEW es.udc.stembach.backend.model.entities.ProjectInstance(p.id, p.title, p.description, p.observations, p.modality, p.url, p.offerZone, p.active, p.biennium, p.createdBy, p.studentGroup) " +
            "FROM ProjectInstance p JOIN StudentGroup s ON p.studentGroup.id = s.id " +
            "JOIN CenterHistory c ON s.school.id = c.school.id " +
            "WHERE c.centerSTEMCoordinator.id = :centerStemCoordinatorId AND c.endDate IS null " +
            "ORDER BY p.title")
    public Page<ProjectInstance> findAllProjectInstacesByCenterStemCoordinator(Long centerStemCoordinatorId, Pageable pageable);

    public Page<ProjectInstance> findAll(Pageable pageable);
}
