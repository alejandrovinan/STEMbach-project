package es.udc.stembach.backend.model.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LeadsProjectDao extends PagingAndSortingRepository<LeadsProject, Long>{

    @Query("SELECT NEW es.udc.stembach.backend.model.entities.UDCTeacher(u.id, u.name, u.surname, u.secondSurname, u.role, u.email, u.dni, u.password, u.faculty) " +
            "FROM UDCTeacher u JOIN LeadsProject l ON u.id = l.udcTeacher.id " +
            "WHERE l.project.id = :projectId " +
            "ORDER BY u.name, u.surname")
    public List<UDCTeacher> findAllTeachersByProject(Long projectId);

}
